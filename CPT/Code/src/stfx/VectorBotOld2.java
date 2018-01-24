
package stfx;

import robocode.*;

import java.awt.*;
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
TODO comment everything
 */

public class VectorBotOld2 extends AdvancedRobot
{
	//build
	public static final boolean PAINT = true;
	public static final boolean DEBUG = true;
	
	//constants
	public static final double TAU = 6.28318530717958647692;
	public static final double PI = 3.14159265358979323846;
	public static final int MAX_RESCAN_TIMER = 30;
	public static final int MIN_ROBOT_DISTANCE = 150;
	public static final int MAX_ROBOT_DISTANCE = 400;
	
	/*
	0: idle
	1: circle target
	2: approach target
	3: avoid target
	4: avoid enemy fire
	5: avoid wall
	*/
	
	/*public enum DriveState
	{
		IDLE,
		CIRCLE,
		APPROACH,
		AVOIDENEMY,
		EVADEFIRE,
		AVOIDWALL;
		
	}*/
	public DriveState driveState = DriveState.IDLE;
	
	//variables
	Bot target;
	boolean scan = true;
	boolean scanInitial = false;
	//boolean scannedTarget = false;
	boolean track = false;
	boolean aimAtTarget = false;
	int rescanTimer = MAX_RESCAN_TIMER;
	Bot closestRobot;
	double hitBulletAngle = 0;
	
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
	 * Convert an angle from domain -PI<=x<=PI to domain 0<=x<=TAU
	 * Error: Non Useful
	 *
	 * @param angle
	 * @return
	 */
	public static final double angleCenteredToFull(double angle)
	{
		return angle + PI;
	}
	
	/**
	 * Convert an angle from domain 0<=x<=TAU to domain -PI<=x<=PI
	 * Error: Non Useful
	 *
	 * @param angle
	 * @return
	 */
	public static final double angleFullToCentered(double angle)
	{
		return angle - PI;
	}
	
	/**
	 * Convert an angle from domain -PI<=angle<=PI to domain 0<=x<=TAU such that an angle from ZERO to PI will remain unchanged and an angle from PI to TAU will map into -PI to ZERO
	 *
	 * Syntax: a -> x -> b is x in range form a to b. "*" preceding a variable represents inclusive.
	 * *ZERO -> angle -> PI :: *ZERO -> angle -> PI
	 * *PI -> angle -> *TAU :: *-PI -> angle -> ZERO
	 *
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
	
	public final Point clampToWalls(Point point)
	{
		Point out = new Point();
		if (point.x < 0)
		{
			out.x = 0;
		}
		else if (point.x > getBattleFieldWidth())
		{
			out.x = getBattleFieldWidth();
		}
		else
		{
			out.x = point.x;
		}
		if (point.y < 0)
		{
			out.y = 0;
		}
		else if (point.y > getBattleFieldHeight())
		{
			out.y = getBattleFieldHeight();
		}
		else
		{
			out.y = point.y;
		}
		return out;
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
					this.scan = true;
					this.track = false;
					print("No Target\n");
				}
				if (this.target != null && !this.scan)
				{
					this.scan = false;
					this.track = true;
				}
				
				if (this.scan)
				{
					this.closestRobot = null;
					
					if (!this.scanInitial)
					{
						this.scanInitial = true;
						setTurnRadarRightRadians(TAU);
						print("Begin Scan\n");
					}
					if (getRadarTurnRemainingRadians() == 0)
					{
						this.scan = false;
						print("Done Scan\n");
					}
				}
				else
				{
					scanInitial = false;
				}
				
				if (this.track)
				{
					Point position = this.target.extrapolatePos(getTime());
					//double angle = closestAngle(Math.atan2(this.target.position.x - getX(), this.target.position.y - getY()) - getRadarHeadingRadians());
					double angle = closestAngle(Math.atan2(position.x - getX(), position.y - getY()) - getRadarHeadingRadians());
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
				
				rescanTimer--;
				if (rescanTimer <= 0 && this.track)// && getOthers() > 1)
				{
					this.target = null;
					this.track = false;
					this.scan = true;
					rescanTimer = MAX_RESCAN_TIMER;
					print("ReScan\n");
				}
			}
			
			//turret
			{
				if (this.track)
				{
					Point position = clampToWalls(extrapolatePos(this.target));
					//position = this.target.position;
					double angle = closestAngle(Math.atan2(position.x - getX(), position.y - getY()) - getGunHeadingRadians());
					this.target.calculateDistance(getX(), getY());
					//tan((off)/distance)=ang
					//off = distance * atan(ang)
					if (this.target.distance * Math.atan(angle) < getWidth() / 2.0)
					{
						aimAtTarget = true;
					} else
					{
						aimAtTarget = false;
					}
					setTurnGunRightRadians(angle);
					
					if (getGunHeat() == 0 && aimAtTarget)
					{
						setFireBullet(150.0/this.target.distance * getEnergy());
						print("Fire:" + (150.0/this.target.distance * getEnergy()) + "\n");
					}
				}
				else
				{
					if (this.target != null)
					{
						Point position = clampToWalls(this.target.extrapolatePos(getTime()));
						double angle = closestAngle(Math.atan2(position.x - getX(), position.y - getY()) - getGunHeadingRadians());
						this.target.calculateDistance(getX(), getY());
						
						//test if aiming at target
						//is true if aiming close enough to the robot
						if (this.target.distance * Math.atan(angle) < getWidth() / 2.0)
						{
							aimAtTarget = true;
						} else
						{
							aimAtTarget = false;
						}
						setTurnGunRightRadians(angle); //aim at target
						
						//only fire if aimed correctly and gun is ready to fire
						if (getGunHeat() == 0 && aimAtTarget)
						{
							//the closer the opponent; the more powerful the shot
							//therefore risky, long shots don't use as much energy
							setFireBullet(150.0 / this.target.distance * getEnergy());
							//print("Fire:" + (150.0/this.target.distance * getEnergy()) + "\n");
						}
					}
				}
			}
			
			//drive states
			{
				//print("TESTA:" + getX() + " TESTB:" + getY() + "\nTESTC:" + getBattleFieldWidth() + " TESTD:" + getBattleFieldHeight() + "\n");
				
				
				if (
						(getX() < 50 && getHeadingRadians() > PI && getHeadingRadians() < TAU) ||
						(getX() > getBattleFieldWidth() - 50 && getHeadingRadians() > 0.0 && getHeadingRadians() < PI) ||
						(getY() < 50 && getHeadingRadians() > TAU / 4.0 && getHeadingRadians() < 3.0*TAU/4.0) ||
						(getY() > getBattleFieldHeight() - 50 && (getHeadingRadians() > 3.0 * TAU / 4.0 || getHeadingRadians() < TAU/4.0)))
				{
					this.driveState = DriveState.AVOIDWALL;
				}
				else if (this.closestRobot != null && this.closestRobot.distance < MIN_ROBOT_DISTANCE)
				{
					this.driveState = DriveState.AVOIDENEMY;
				}
				else if (this.closestRobot != null && this.closestRobot.distance > MAX_ROBOT_DISTANCE)
				{
					this.driveState = DriveState.APPROACH;
				}
				else
				{
					this.driveState = DriveState.IDLE;
				}
				
				
				if (this.driveState == DriveState.IDLE)
				{
					setAhead(10);
				}
				if (this.driveState == DriveState.AVOIDWALL)
				{
					Vector wallNormal = new Vector();
					//calculate the normal of the wall
					if (getX() < 50)
					{
						wallNormal.end.x += 1;
					}
					if (getX() > getBattleFieldWidth() - 50)
					{
						wallNormal.end.x += -1;
					}
					if (getY() < 50)
					{
						wallNormal.end.y += 1;
					}
					if (getY() > getBattleFieldHeight() - 50)
					{
						wallNormal.end.y += -1;
					}
					wallNormal.normalize();
					
					//turn to face away from the wall
					setTurnRightRadians(closestAngle(wallNormal.getAngle() - getHeadingRadians()));
					if (getTurnRemainingRadians() == 0)
					{
						this.driveState = DriveState.IDLE;
					}
				}
				if (this.driveState == DriveState.AVOIDENEMY)
				{
					Vector vector = new Vector(this.closestRobot.position, new Point(getX(), getY()));
					print( "Distance:" + this.closestRobot.distance + "\n" + "Angle:" + vector.getAngle() + "\n" + "Heading:" + getHeadingRadians() + "\n");
					setTurnRightRadians(closestAngle(vector.getAngle() - getHeadingRadians()));
					setAhead( (MIN_ROBOT_DISTANCE / (this.closestRobot.distance - 2.0*getWidth())) * 10);
					if (getTurnRemainingRadians() == 0)
					{
						this.driveState = DriveState.IDLE;
					}
				}
				if (this.driveState == DriveState.APPROACH)
				{
					Vector vector = new Vector(new Point(getX(), getY()), this.closestRobot.position);
					//print( "Distance:" + this.closestRobot.distance + "\n" + "Angle:" + vector.getAngle() + "\n" + "Heading:" + getHeadingRadians() + "\n");
					setTurnRightRadians(closestAngle(vector.getAngle() - getHeadingRadians())+0.1*TAU);
					setAhead( 5 );
				}
				if (this.driveState == DriveState.EVADEFIRE)
				{
					Vector bulletTrajectory = new Vector(0,0,this.hitBulletAngle);
					double evadeAngle = 0;
					//find which direction it is closest to turn to
					if (closestAngle(this.hitBulletAngle) >= 0)
					{
						evadeAngle = Vector.calculatePerpendicular(bulletTrajectory, true).getAngle();
					}
					else
					{
						evadeAngle = Vector.calculatePerpendicular(bulletTrajectory, false).getAngle();
					}
					
					//setTurnRightRadians(evadeAngle);
				}
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
		double distBySpeed = (Math.sqrt((x0-x2)*(x0-x2) + (y0-y2)*(y0-y2))/bulletSpeed) * bot.speed;// * (1.0 / bulletSpeed);
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
			print("Scanned Robot\n");
			
			//TODO add priority checking
			
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
				print("Found Target\n");
				//this.scannedTarget = true;
				this.target = newBot;
				this.target.calculatePosition(getHeadingRadians(), getX(), getY());
			}
		}
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event)
	{
		if (this.driveState != DriveState.AVOIDWALL)
		{
			this.driveState = DriveState.EVADEFIRE;
			this.hitBulletAngle = event.getHeading();
		}
	}

	@Override
	public void onHitWall(HitWallEvent event)
	{
		//print("See Robot\n");
	}
	

	@Override
	public void onWin(WinEvent e)
	{
	}

	@Override
	public void onDeath(DeathEvent e)
	{
		print("NOOOOOOO!!!!!\n");
	}


	@Override
	public void onBattleEnded(BattleEndedEvent e)
	{
	}
	
	@Override
	public void onSkippedTurn(SkippedTurnEvent event)
	{
		print("WARNING::" + " Turn Skipped; Taking Too Long\n");
	}
	
	@Override
	public void onPaint(Graphics2D g)
	{
		if (PAINT)
		{
			//g.fill(Ellipse2D.Double(100, 100, 100, 100));
			//g.setColor(Color.WHITE);
			
			if (target != null)
			{
				Point extrapolate = this.target.extrapolatePos(getTime());
				g.setColor(Color.GREEN);
				g.fillOval((int)(target.position.x) - 10, (int)(target.position.y) - 10, 20, 20);
				g.drawLine((int)(extrapolate.x), (int)(extrapolate.y), (int)extrapolatePos(target).x, (int)extrapolatePos(target).y);
				
				g.setColor(Color.BLUE);
				g.fillOval((int)(extrapolate.x) - 10, (int)(extrapolate.y) - 10, 20, 20);
				
			}
			else
			{
				g.setColor(Color.RED);
				g.fillOval((int)(getX() + Math.sin(getHeadingRadians()) * 40.0) - 10, (int)(getY() + Math.cos(getHeadingRadians()) * 40.0) - 10, 20, 20);
			}
			
			g.setColor(Color.GREEN);
			int yCoord = (int)getBattleFieldHeight() - 10;
			g.drawString("STATUS:", 300, yCoord); yCoord -= 10;
			g.drawString("X: " + getX(), 300, yCoord); yCoord -= 10;
			g.drawString("Y: " + getY(), 300, yCoord); yCoord -= 10;
			g.drawString("Heading: " + (getHeadingRadians() / TAU) + "*TAU", 300, yCoord); yCoord -= 10;
			g.drawString("GunHeading: " + (getGunHeadingRadians() / TAU) + "*TAU", 300, yCoord); yCoord -= 10;
			g.drawString("RadarHeading: " + (getRadarHeadingRadians() / TAU) + "*TAU", 300, yCoord); yCoord -= 10;
			g.drawString("Energy: " + getEnergy(), 300, yCoord); yCoord -= 10;
			
			yCoord -= 10;
			
			g.drawString("STATE INFO:", 300, yCoord); yCoord -= 10;
			if (this.scan) { g.drawString("Scan", 300, yCoord); yCoord -= 10; }
			if (this.track) { g.drawString("Track", 300, yCoord); yCoord -= 10; }
			if (this.target != null) { g.drawString("Target", 300, yCoord); yCoord -= 10; }
			if (this.aimAtTarget) { g.drawString("AimAtTarget", 300, yCoord); yCoord -= 10; }
			
			yCoord -= 10;
			
			g.drawString("DRIVESTATE INFO:", 300, yCoord); yCoord -= 10;
			if (this.driveState == DriveState.IDLE) { g.drawString("Idle", 300, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.AVOIDWALL) { g.drawString("AvoidWall", 300, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.EVADEFIRE) { g.drawString("EvadeFire", 300, yCoord); yCoord -= 10; }
			if (this.driveState == DriveState.AVOIDENEMY) { g.drawString("AvoidEnemy", 300, yCoord); yCoord -= 10; }
			
			
		}
	}
}

