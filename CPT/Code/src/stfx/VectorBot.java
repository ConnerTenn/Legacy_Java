
package stfx;
import robocode.*;

import java.awt.*;
import java.lang.Math;
//import java.awt.Point;
//import java.awt.geom.Point2D;
//import java.awt.Color;

//import java.awt.geom.Ellipse2D.Double;

/*
TODO add detection of target's death
TODO gun power algorithm
TODO implement "newTarget" so that the current target can still be fired at while a scan is taking place
TODO driving algorithm
TODO combine scan and track into 1 state var
TODO approach enemy calculation
 */

public class VectorBot extends AdvancedRobot
{
	//build
	public static final boolean PAINT = false;//true;
	public static final boolean DEBUG = false;//true;
	
	//constants
	public static final double TAU = 6.28318530717958647692;
	public static final double PI = 3.14159265358979323846;
	public static final int MAX_RESCAN_TIMER = 25;//30
	public static final int MIN_ROBOT_DISTANCE = 150;
	public static final int MAX_ROBOT_DISTANCE = 400;
	public static final int MIN_DISTANCE_TO_WALL = 150;//150
	
	
	
	
	//variables
	public DriveState driveState = DriveState.IDLE;
	
	Bot target;
	boolean scan = true;
	boolean scanInitial = false;
	//boolean scannedTarget = false;
	
	boolean track = false;
	boolean aimAtTarget = false;
	int rescanTimer = MAX_RESCAN_TIMER;
	
	Bot closestRobot;
	boolean hitByBullet = false;
	double hitBulletAngle = 0;
	double driveAngle = 0;
	double turnSpeed = Rules.MAX_TURN_RATE;
	double driveSpeed = 10;
	
	/**
	 * wrapper function for printing. Allows the use of PRINT variable to enable/disable printing;
	 * @param out
	 */
	public static final void print(String out)
	{
		if (DEBUG)
		{
			System.out.print(out);
		}
	}
	
	/**
	 * Transform angle to an angle in an absolute reference frame to a reference frame relative to newFrame.
	 *
	 * @param angle
	 * @param newFrame
	 * @return
	 */
	public static final double convertReferenceFrame(double angle, double newFrame)
	{
		return mod((angle - newFrame), TAU);
	}
	
	/**
	 * Convert an angle from domain  to domain
	 * Convert an angle from domain -PI<=x<=PI to domain 0<=x<=TAU such that an angle from ZERO to PI will remain unchanged and an angle from -PI to ZERO will map into PI to TAU
	 *
	 * Syntax: a -> x -> b is x in range form a to b. "*" preceding a variable represents inclusive.
	 * *ZERO -> angle -> PI :: *ZERO -> angle -> PI
	 * *-PI -> angle -> *ZERO :: *PI -> angle -> TAU
	 *
	 * @param angle
	 * @return
	 */
	public static final double angleCenteredToFull(double angle)
	{
		//return angle + PI;
		double out = angle;
		if (angle < 0)
		{
			out = TAU + angle;
		}
		return out;
	}
	
	
	/**
	 * Convert an angle from domain 0<=x<=TAU to domain -PI<=angle<=PI such that an angle from ZERO to PI will remain unchanged and an angle from PI to TAU will map into -PI to ZERO
	 *
	 * Syntax: a -> x -> b is x in range form a to b. "*" preceding a variable represents inclusive.
	 * *ZERO -> angle -> PI :: *ZERO -> angle -> PI
	 * *PI -> angle -> *TAU :: *-PI -> angle -> ZERO
	 *
	 * @param angle
	 * @return
	 */
	public static final double angleFullToCentered(double angle)
	{
		return mod((angle - PI), TAU) - PI;
	}
	
	/**
	 * Convert an angle from domain 0<=x<=TAU to domain -PI<=angle<=PI such that an angle from ZERO to PI will remain unchanged and an angle from PI to TAU will map into -PI to ZERO
	 *
	 * Syntax: a -> x -> b is x in range form a to b. "*" preceding a variable represents inclusive.
	 * *ZERO -> angle -> PI :: *ZERO -> angle -> PI
	 * *PI -> angle -> *TAU :: *-PI -> angle -> ZERO
	 *
	 * @deprecated use angleFullToCentered instead
	 * @param angle
	 * @return
	 */
	public static final double closestAngle(double angle)
	{
		return mod((angle - PI), TAU) - PI;
	}
	
	/**
	 * calculate the modulus(a,b) using java's remainder operator.
	 *
	 * http://stackoverflow.com/questions/90238/whats-the-syntax-for-mod-in-java
	 * @param a
	 * @param b
	 * @return
	 */
	public static final double mod(double a, double b)
	{
		return ((a%b)+b) % b;
	}
	
	/**
	 * clamp a coordinate to within the battleground
	 * @param point
	 * @return
	 */
	public final Point clampToWalls(Point point)
	{
		Point out = new Point();
		//off left side
		if (point.x < 0)
		{
			out.x = 0;
		}
		//off right side
		else if (point.x > getBattleFieldWidth())
		{
			out.x = getBattleFieldWidth();
		}
		//within bounds
		else
		{
			out.x = point.x;
		}
		//off bottom side
		if (point.y < 0)
		{
			out.y = 0;
		}
		//off to side
		else if (point.y > getBattleFieldHeight())
		{
			out.y = getBattleFieldHeight();
		}
		//within bounds
		else
		{
			out.y = point.y;
		}
		return out;
	}
	
	/**
	 * calculates the distance between 2 points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static final double calculateDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}
	
	/**
	 * Main method.
	 */
	@Override
	public void run()
	{
		//Initilization
		setAdjustGunForRobotTurn(true);//set turning to be independent
		setAdjustRadarForGunTurn(true);//set turning to be independent
		setAdjustRadarForRobotTurn(true);//set turning to be independent
		
		// Get the radar spinning so we can get ScannedRobotEvents
		//setTurnRadarLeftRadians(Double.POSITIVE_INFINITY);
		
		while (true)
		{
			//setTurnRightRadians(0.01*TAU); //execute();
			//setAhead(10); //execute();
			//print("X:" + getX() + " Y:" + getY() + "\n");
			//print("Angle: " + getHeadingRadians() "\n");
			
			//scanner
			{
				if (this.target == null)
				{
					//set robot to scan
					this.scan = true;
					this.track = false;
					print("No Target\n");
				}
				if (this.target != null && !this.scan)
				{
					//has a target: begin tracking target
					this.scan = false;
					this.track = true;
				}
				
				//do scan
				if (this.scan)
				{
					this.closestRobot = null;
					
					//only execute once every scan
					if (!this.scanInitial)
					{
						this.scanInitial = true;
						setTurnRadarRightRadians(TAU);
						print("Begin Scan\n");
					}
					//wait till scan is complete
					if (getRadarTurnRemainingRadians() == 0)
					{
						this.scan = false;
						print("Done Scan\n");
					}
				}
				else
				{
					//reset var to handle executing initial code once
					scanInitial = false;
				}
				
				if (this.track)
				{
					Point position = this.target.extrapolatePos(getTime());
					//double angle = closestAngle(Math.atan2(this.target.position.x - getX(), this.target.position.y - getY()) - getRadarHeadingRadians());
					double angle = angleFullToCentered(Math.atan2(position.x - getX(), position.y - getY()) - getRadarHeadingRadians());
					angle += 0.04 * Math.signum(angle);
					//print("TEST:" + Math.atan2(this.target.position.x - getX(), this.target.position.y - getY()) + "\n");
					//print("Bearing:" + this.target.bearing + " Radar:" + getRadarHeadingRadians() + " Heading:" + getHeadingRadians() + "\n" + "TurnAngle: " + angle + "\n");
					setTurnRadarRightRadians(angle);
					//print("Face:" + getRadarHeadingRadians() + " Target" + this.target.bearing + "\n");
					
					if (getRadarTurnRemainingRadians() == 0 && angle != 0)
					{
						this.track = false;
						this.target = null;
					}
				}
				
				//timer to preform a rescan of all robots
				if (this.track)
				{
					rescanTimer--;
					if (rescanTimer <= 0)// && getOthers() > 1)
					{
						this.target = null;
						this.track = false;
						this.scan = true;
						rescanTimer = MAX_RESCAN_TIMER;
						print("ReScan\n");
					}
				}
			}
			
			//turret
			{
				//do while tracking
				//if (this.track)
				if (this.target != null)
				{
					//calculate position variables
					Point position = clampToWalls(extrapolatePos(this.target));
					//position = this.target.position;
					double angle = angleFullToCentered(Math.atan2(position.x - getX(), position.y - getY()) - getGunHeadingRadians());
					//this.target.calculateDistance(getX(), getY());
					//tan((off)/distance)=ang
					//off = distance * atan(ang)
					
					//test if aiming at target
					//is true if aiming close enough to the robot
					if (this.target.distance * Math.atan(angle) < getWidth() / 2.0)
					{
						aimAtTarget = true;
					}
					else
					{
						aimAtTarget = false;
					}
					setTurnGunRightRadians(angle); //aim at target
					
					//only fire if aimed correctly and gun is ready to fire
					if (getGunHeat() == 0 && aimAtTarget)
					{
						//the closer the opponent; the more powerful the shot
						//therefore risky, long shots don't use as much energy
						setFireBullet(150.0/this.target.distance * getEnergy());
						//print("Fire:" + (150.0/this.target.distance * getEnergy()) + "\n");
					}
				}
			}
			
			//drive states
			{
				//test if robot is near wall
				if (    (getX() < 50 && getHeadingRadians() > PI && getHeadingRadians() < TAU) ||
						(getX() > getBattleFieldWidth() - 50 && getHeadingRadians() > 0.0 && getHeadingRadians() < PI) ||
						(getY() < 50 && getHeadingRadians() > TAU / 4.0 && getHeadingRadians() < 3.0*TAU/4.0) ||
						(getY() > getBattleFieldHeight() - 50 && (getHeadingRadians() > 3.0 * TAU / 4.0 || getHeadingRadians() < TAU/4.0)))
				{
					this.driveState = DriveState.AVOIDWALL;
				}
				//test if robot is too close to another robot
				else if (this.closestRobot != null && this.closestRobot.distance < MIN_ROBOT_DISTANCE)
				{
					this.driveState = DriveState.AVOIDENEMY;
				}
				//test if robot is far away from another robot
				else if (/*this.driveState != DriveState.EVADEFIRE && */this.target != null && this.target.distance > MAX_ROBOT_DISTANCE)
				{
					this.driveState = DriveState.APPROACH;
				}
				//default state is idle
				else
				{
					this.driveState = DriveState.IDLE;
				}
				
				
				
				//do idle movements
				if (this.driveState == DriveState.IDLE)
				{
					this.driveSpeed = 10;
					
					//turn slowly towards the center
					/*Vector posToCenter = new Vector(new Point(getX(), getY()), new Point(getBattleFieldWidth()/2.0, getBattleFieldHeight()/2.0));
					double angleToCenter = angleFullToCentered(angleCenteredToFull(posToCenter.getAngle()) - getHeadingRadians());
					//this.driveAngle = angleToCenter + getHeadingRadians();
					//print(""+ angleToCenter + "\n");
					if (angleToCenter < 0)
					{
						this.driveAngle += -Math.min(0.01 * TAU, Math.abs(angleToCenter));
					}
					else if (angleToCenter > 0)
					{
						this.driveAngle += Math.min(0.01 * TAU, Math.abs(angleToCenter));
					}*/
					//this.driveAngle+=0.002*TAU;
					
					
					this.turnSpeed = Rules.MAX_TURN_RATE / 2.0;
					
					//print(""+Math.atan2(Math.sin(TAU/4), Math.cos(TAU/4)) + "\n");
				}
				//move away from enemy
				if (this.driveState == DriveState.AVOIDENEMY)
				{
					//create a vector form the closest robot to this position and get the angle
					Vector vector = new Vector(this.closestRobot.position, new Point(getX(), getY()));
					this.driveAngle = angleFullToCentered(vector.getAngle());
					
					double distance = calculateDistance(this.closestRobot.position.x, this.closestRobot.position.y, getX(), getY());
					//print( "Distance:" + this.closestRobot.distance + "\n" + "Angle:" + vector.getAngle() + "\n" + "Heading:" + getHeadingRadians() + "\n");
					
					/*this.turnSpeed = Rules.MAX_TURN_RATE * (MIN_ROBOT_DISTANCE / distance ) / 2.0;
					if (distance <= MIN_ROBOT_DISTANCE / 2.0)
					{
						this.driveSpeed = 5;//0.1
					}*/
					
					this.turnSpeed = Math.min(Rules.MAX_TURN_RATE, getTurnRemainingRadians());
					this.driveSpeed = (MIN_ROBOT_DISTANCE / (this.closestRobot.distance - 2.0 * getWidth())) * 10;
				}
				//move towards enemy
				if (this.driveState == DriveState.APPROACH)
				{
					//create a vector form this position to the closest robot and get the angle
					Vector vector = new Vector(new Point(getX(), getY()), this.target.position);
					this.driveAngle = angleFullToCentered(vector.getAngle());
					
					double distance = calculateDistance(this.target.position.x, this.target.position.y, getX(), getY());
					//print( "Distance:" + this.closestRobot.distance + "\n" + "Angle:" + vector.getAngle() + "\n" + "Heading:" + getHeadingRadians() + "\n");
					
					//calculate movements
					this.turnSpeed = Rules.MAX_TURN_RATE * (MIN_ROBOT_DISTANCE / distance ) / 4.0;
				}
				//calculate angle to drive away from the line of fire
				if (this.hitByBullet)// && this.driveState != DriveState.AVOIDWALL// && this.driveState != DriveState.AVOIDENEMY)
				{
					this.hitByBullet = false;
					//Vector bulletTrajectory = new Vector(0,0,this.hitBulletAngle);
					//double evadeAngle = 0;
					//find which direction it is closest to turn to
					if (angleFullToCentered(this.hitBulletAngle - getHeadingRadians()) >= 0)
					{
						this.driveAngle = this.hitBulletAngle - TAU/4.0;;//evadeAngle = this.hitBulletAngle - TAU/4.0;
						//print("EvadeRight\n");
					}
					else
					{
						this.driveAngle = this.hitBulletAngle + TAU/4.0;//evadeAngle = this.hitBulletAngle + TAU/4.0;
						//print("EvadeLeft\n");
					}
					//evadeAngle = this.hitBulletAngle + TAU / 4.0;
					//evadeAngle = Vector.calculatePerpendicular(bulletTrajectory, true).getAngle();
					
					
					//this.driveAngle = evadeAngle;
					this.turnSpeed = Rules.MAX_TURN_RATE;
					//print("EvadeAngle:" + evadeAngle + "\n");
					
					/*if (getRadarTurnRemainingRadians() == 0)
					{
						this.driveState = DriveState.IDLE;
					}*/
				}
				//avoid walls
				if (this.driveState == DriveState.AVOIDWALL)
				{
					Vector wallNormal = new Vector();
					//calculate the normal of the wall
					double distanceToWall = MIN_DISTANCE_TO_WALL;
					if (getX() < MIN_DISTANCE_TO_WALL)
					{
						//wall face right
						wallNormal.end.x += 1;
						distanceToWall = getX();
					}
					if (getX() > getBattleFieldWidth() - MIN_DISTANCE_TO_WALL)
					{
						//wall face left
						wallNormal.end.x += -1;
						distanceToWall = Math.min(distanceToWall,getBattleFieldWidth()-getX());
					}
					if (getY() < MIN_DISTANCE_TO_WALL)
					{
						//wall face up
						wallNormal.end.y += 1;
						distanceToWall = Math.min(distanceToWall,getY());
					}
					if (getY() > getBattleFieldHeight() - MIN_DISTANCE_TO_WALL)
					{
						//wall face down
						wallNormal.end.y += -1;
						distanceToWall = Math.min(distanceToWall,getBattleFieldHeight()-getY());
					}
					wallNormal.normalize();
					
					//calculate movements
					this.driveAngle = wallNormal.getAngle();
					this.turnSpeed = Rules.MAX_TURN_RATE * (MIN_DISTANCE_TO_WALL / distanceToWall ) / 2.0;
					this.driveSpeed = 10;
				}
				
				
				//preform drive operations
				setAhead(this.driveSpeed);
				setMaxTurnRate(this.turnSpeed);
				setTurnRightRadians(angleFullToCentered(this.driveAngle - this.getHeadingRadians()));
				//print("TargetDriveAngle:" + this.driveAngle + "\n");
			}
			
			//execute pending commands
			execute();
		}
	}
	
	/**
	 * extrapolate the motion of bot so that the gun can time shots
	 * @param bot robot to extrapolate
	 * @return the extrapolated point
	 */
	public Point extrapolatePos(Bot bot)
	{
		//don't need to solve the equation;
		////begin face robot, calculate the predicted point
		//recalculate with the current position of the gun and move to predicted point
		
		//initialize vars
		double heading = getGunHeadingRadians();
		double x0 = getX();
		double y0 = getY();
		
		Point position = this.target.extrapolatePos(getTime());
		double vector = bot.heading;
		double x1 = position.x;
		double y1 = position.y;
		
		double bulletSpeed = 11.0;
		
		//calculate equation of line for this robot with position and heading
		double a0 = -Math.cos(heading);
		double b0 = Math.sin(heading);
		double c0 = a0 * x0 + b0 * y0;
		
		//calculate equation of line for other robot with position and heading
		double a1 = -Math.cos(vector);
		double b1 = Math.sin(vector);
		double c1 = a1 * x1 + b1 * y1;
		
		//calculate intersection between the two lines (the point at which the bullet would cross the other bot's path)
		double x2 = -(b0 * c1 - b1 * c0) / (a0 * b1 - a1 * b0);
		double y2 = (a0 * c1 - a1 * c0) / (a0 * b1 - a1 * b0);
		
		//calculate the position the robot will be at when the bullet intersects the robot's path
		//double distBySpeed = (Math.sqrt((x0-x2)*(x0-x2) + (y0-y2)*(y0-y2))/bulletSpeed) * bot.speed;// * (1.0 / bulletSpeed);
		double distBySpeed = (calculateDistance(x0,y0,x2,y2)/bulletSpeed) * bot.speed;// * (1.0 / bulletSpeed);
		//print("A:" + ((Math.sqrt((x0-x2)*(x0-x2) + (y0-y2)*(y0-y2))/bulletSpeed) * bot.speed) +
		//		"\nB:" + ((calculateDistance(x0,y0,x2,y2)/bulletSpeed) * bot.speed) + "\n");
		double x3 = x1 + Math.sin(vector) * distBySpeed;
		double y3 = y1 + Math.cos(vector) * distBySpeed;
		
		/*print
		(
			"x0:" + x0 + " y0:" + y0 + "\n" +
			"x1:" + x1 + " y1:" + y1 + "\n" +
			"heading:" + heading + " vector:" + vector + "\n" +
			"a0:" + a0 + " b0:" + b0 + " c0:" + c0 + "\n" +
			"a1:" + a1 + " b1:" + b1 + " c1:" + c1 + "\n" +
			"x2:" + x2 + " y2:" + y2 + "\n" +
			"x3:" + x3 + " y3:" + y3 + "\n" +
			"speed:" + bot.speed + "distBySpeed:" + distBySpeed + "\n" +
			"\n"
		);*/
		
		return new Point(x3, y3);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event)
	{
		//this.scannedTarget = false;
		Bot newBot = new Bot(event, getTime());
		
		if (this.closestRobot == null)
		{
			this.closestRobot = newBot;
		}
		else
		{
			if (newBot.distance < this.closestRobot.distance)
			{
				this.closestRobot = newBot;
			}
		}
		this.closestRobot.calculatePosition(getHeadingRadians(), getX(), getY());
		
		if (this.scan)
		{
			//print("Scanned Robot\n");
			
			if (target == null || !target.priority(newBot))
			{
				this.target = newBot;
				this.target.calculatePosition(getHeadingRadians(), getX(), getY());
				//this.target.calculatePosition(convertReferenceFrame(0, PI), 100, 100);
				//this.target.position = new Point(100, 90);
				
			}
		}
		
		if (this.track)
		{
			if (event.getName().equals(this.target.name))
			{
				//print("Found Target\n");
				//this.scannedTarget = true;
				this.target = newBot;
				this.target.calculatePosition(getHeadingRadians(), getX(), getY());
			}
		}
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event)
	{
		//only respond if not near boarder
		if (this.driveState != DriveState.AVOIDWALL)
		{
			//set state value and record bullet data
			//this.driveState = DriveState.EVADEFIRE;
			this.hitByBullet = true;
			this.hitBulletAngle = event.getHeadingRadians();
		}
	}

	@Override
	public void onDeath(DeathEvent event)
	{
		print("NOOOOOOO!!!!!\n");//I died.
	}
	
	@Override
	public void onSkippedTurn(SkippedTurnEvent event)
	{
		print("WARNING::" + " Turn Skipped; Taking Too Long\n");
	}
	
	@Override
	public void onPaint(Graphics2D graphics)
	{
		if (PAINT)
		{
			//graphics.fill(Ellipse2D.Double(100, 100, 100, 100));
			//graphics.setColor(Color.WHITE);
			
			if (target != null)
			{
				//draw extrapolations
				Point extrapolate = this.target.extrapolatePos(getTime());
				graphics.setColor(Color.GREEN);
				graphics.fillOval((int)(target.position.x) - 10, (int)(target.position.y) - 10, 20, 20);//last known position
				graphics.drawLine((int)(extrapolate.x), (int)(extrapolate.y), (int)extrapolatePos(target).x, (int)extrapolatePos(target).y);//extrapolated bullet prediction
				
				graphics.setColor(Color.BLUE);
				graphics.fillOval((int)(extrapolate.x) - 10, (int)(extrapolate.y) - 10, 20, 20);//extrapolated movement
				
			}
			else
			{
				//no target; draw a red circle
				graphics.setColor(Color.RED);
				graphics.fillOval((int)(getX() + Math.sin(getHeadingRadians()) * 40.0) - 10, (int)(getY() + Math.cos(getHeadingRadians()) * 40.0) - 10, 20, 20);
			}
			
			
			graphics.setColor(Color.GREEN);
			//variable to keep track of vertical positioning of each line of text so that they don't overlap
			int yCoord = (int)getBattleFieldHeight() - 10;
			
			//draw robot status info
			graphics.drawString("STATUS:", 0, yCoord); yCoord -= 10;
			graphics.drawString("X: " + getX(), 0, yCoord); yCoord -= 10;
			graphics.drawString("Y: " + getY(), 0, yCoord); yCoord -= 10;
			graphics.drawString("Heading: " + (getHeadingRadians() / TAU) + "*TAU", 0, yCoord); yCoord -= 10;
			graphics.drawString("GunHeading: " + (getGunHeadingRadians() / TAU) + "*TAU", 0, yCoord); yCoord -= 10;
			graphics.drawString("RadarHeading: " + (getRadarHeadingRadians() / TAU) + "*TAU", 0, yCoord); yCoord -= 10;
			graphics.drawString("Energy: " + getEnergy(), 0, yCoord); yCoord -= 10;
			
			yCoord -= 10;
			
			//draw robot state info
			graphics.drawString("STATE INFO:", 0, yCoord); yCoord -= 10;
			if (this.scan) { graphics.drawString("Scan", 0, yCoord); yCoord -= 10; }
			if (this.track) { graphics.drawString("Track", 0, yCoord); yCoord -= 10; }
			if (this.target != null) { graphics.drawString("Target", 0, yCoord); yCoord -= 10; }
			if (this.aimAtTarget) { graphics.drawString("AimAtTarget", 0, yCoord); yCoord -= 10; }
			
			yCoord -= 10;
			
			//draw robot drivestate info
			graphics.drawString("DRIVESTATE INFO:", 0, yCoord); yCoord -= 10;
			if (this.driveState == DriveState.IDLE) { graphics.drawString("Idle", 0, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.AVOIDWALL) { graphics.drawString("AvoidWall", 0, yCoord); yCoord -= 10; }
			//if (this.driveState == DriveState.EVADEFIRE) { graphics.drawString("EvadeFire", 0, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.AVOIDENEMY) { graphics.drawString("AvoidEnemy", 0, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.APPROACH) { graphics.drawString("Approach", 0, yCoord); yCoord -= 10; }
			if (this.hitByBullet) { graphics.drawString("HitByBullet", 0, yCoord); yCoord -= 10; }
			
		}
	}
}

