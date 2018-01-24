package FirstTestPKG;
import robocode.*;
import java.lang.Math;
//import java.awt.Point;
//import java.awt.geom.Point2D;
//import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.geom.Ellipse2D.Double;

public class VectorBot extends AdvancedRobot
{
	public static final double TAU = 6.28318530717958647692;
	public static final boolean PAINT = true;
	
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
	//public DriveState driveState = DriveState.IDLE;
	
	Bot target;
	boolean scan = true;
	
	/*
	public static class Point
	{
		public double x;
		public double y;
		
		public Point()
		{
			this.x = 0;
			this.y = 0;
		}
		public Point(Point point)
		{
			this.x = point.x;
			this.y = point.y;
		}
		public Point(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public static class Vector //Move to spearate file
	{
		public Point begin;
		public Point end;
		
		public Vector()
		{
			
		}
		public Vector(Point point1, Point point2)
		{
			this.begin = new Point(point1);
			this.end = new Point(point2);
		}
		public Vector(double x1, double y1, double x2, double y2)
		{
			this.begin = new Point(x1, y1);
			this.end = new Point(x2, y2);
		}
		public Vector(double x, double y, double angle)
		{
			this.begin = new Point(x, y);
			this.begin = new Point(Math.sin(x), -Math.cos(y));
		}
		public void normalize()
		{//incomplete
			double x = this.end.x - this.begin.x;
			double y = this.end.y - this.begin.y;
			this.end.x = x / Math.sqrt(x*x + y*y) + this.begin.x;
			this.end.y = y / Math.sqrt(x*x + y*y) + this.begin.y;
		}
		public Vector getNormalized(Vector vector)
		{
			double x = vector.end.x - vector.begin.x;
			double y = vector.end.y - vector.begin.y;
			return new Vector(vector.begin.x, vector.begin.y, x / Math.sqrt(x*x + y*y) + vector.begin.x, y / Math.sqrt(x*x + y*y) + vector.begin.y);
		}
	}
	
	//move to respective class
	public Vector calculatePerpendicular(Vector vector)
	{
		Point avg = new Point((vector.begin.x + vector.end.x) / 2.0, (vector.begin.y + vector.end.y) / 2.0);
		Point rotX = Rotate(new Point(vector.begin.x - avg.x, vector.begin.y - avg.y), TAU/4.0);
		return new Vector(avg, rotX);
	}
	
	//move to respective class
	public Point Rotate(Point point, double angle)
	{
		return new Point(point.x * Math.cos(angle) + point.y * Math.sin(angle), -point.x * Math.sin(angle) + point.y * Math.cos(angle));
	}
	
	//convert to/from game angle to angle
	public double convertAngle()
	{
		return 0;
	}*/
	
	@Override
	public void run()
	{

		// Get the radar spinning so we can get ScannedRobotEvents
		//setTurnRadarLeftRadians(Double.POSITIVE_INFINITY);
		int tempCount = 100;
		while (true)
		{
			//setTurnRightRadians(-getHeadingRadians());
			//System.out.println("Rad:" + getHeadingRadians());
			//setTurnRightRadians(0.01*TAU); //execute();
			//setAhead(10); //execute();
			
			//turret
			if (this.target == null)
			{
				this.scan = true;
			}
			else
			{
				this.scan = false;
			}
			if (this.scan)
			{
				setTurnRadarRightRadians(TAU);
			}
			else
			{
				setTurnRadarRightRadians(getRadarHeadingRadians() - this.target.bearing);
				System.out.println("Face:" + getRadarHeadingRadians() + " Target" + this.target.bearing);
			}
			tempCount--;
			if (tempCount <= 0)
			{
				this.target = null; tempCount = 100;
				System.out.println("ReScan");
			}
			//drive states
			/*if (this.driveState == DriveState.IDLE)
			{
				setAhead(10);
			}
			else if (this.driveState == DriveState.CIRCLE)
			{
				//check if angle to target is greater than threshold
					//rotate
				//else
					//setAhead(10);
			}
			else if (this.driveState == DriveState.APPROACH)
			{
				//set turn state to turn to angle or implement turn here?
				
			}*/
			
			//execute pending commands
			execute();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event)
	{
		this.target = new Bot();
		//System.out.println("See Robot");
		//this.scan = false;
		this.target.bearing = event.getBearingRadians();
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event)
	{
		
	}

	@Override
	public void onHitWall(HitWallEvent event)
	{
		//System.out.println("See Robot");
	}
	

	@Override
	public void onWin(WinEvent e)
	{
	}

	@Override
	public void onDeath(DeathEvent e)
	{
	}


	@Override
	public void onBattleEnded(BattleEndedEvent e)
	{
	}
	
	@Override
	public void onSkippedTurn(SkippedTurnEvent event)
	{
		System.out.println("WARNING::" + " Turn Skipped; Taking Too Long");
	}
	
	@Override
	public void onPaint(Graphics2D g)
	{
		if (PAINT)
		{
			//g.fill(Ellipse2D.Double(100, 100, 100, 100));
			//g.setColor(Color.WHITE);
			
			g.fillOval((int)(getX() + Math.sin(getHeadingRadians()) * 40.0), (int)(getY() + Math.cos(getHeadingRadians()) * 40.0), 5, 5);
		}
	}
}