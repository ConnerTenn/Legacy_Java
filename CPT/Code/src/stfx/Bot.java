
package stfx;
import robocode.*;
//import java.lang.Math;

public class Bot
{
	Point position;
	double distance;
	double bearing;
	double heading;
	double speed;
	String name;
	long timeStamp;
	
	//constructors
	public Bot()
	{
		this.position = new Point();
		this.distance = 0;
		this.bearing = 0;
		this.heading = 0;
		this.speed = 0;
		this.name = "";
		this.timeStamp = 0;
	}
	
	public Bot(double distance, double bearing, double heading, double speed, String name, long timeStamp)
	{
		this.position = new Point();
		this.distance = distance;
		this.bearing = bearing;
		this.heading = heading;
		this.speed = speed;
		this.name = name;
		this.timeStamp = timeStamp;
	}
	
	public Bot(ScannedRobotEvent event, long timeStamp)
	{
		this.position = new Point();
		this.distance = event.getDistance();
		this.bearing = event.getBearingRadians();
		this.heading = event.getHeadingRadians();
		this.speed = event.getVelocity();
		this.name = event.getName();
		this.timeStamp = timeStamp;
	}
	
	/**
	 * calculates the position of this robot based off of the bearing and position of vectorBot
	 * @param angle bearing of vectorBot
	 * @param x X position of vectorBot
	 * @param y Y position of vectorBot
	 */
	public void calculatePosition(double angle, double x, double y)
	{
		position = new Point(x+Math.sin(bearing + angle)*this.distance, y+Math.cos(bearing + angle)*this.distance);
	}
	
	/**
	 * calculate the distance from a point
	 * @param x x pos of point to reference from
	 * @param y x pos of point to reference from
	 */
	public void calculateDistance(double x, double y)
	{
		this.distance = Math.sqrt(Math.pow(this.position.x - x,2) + Math.pow(this.position.y - y,2));
	}
	
	//switch priority to a point system? not limited to *0->*1
	//test wheter points >= other points
	
	/**
	 * compare the priority of this robot vs another
	 * @param other the other robot to compare against
	 * @return return if this robot has a greater priority
	 */
	public boolean priority(Bot other)
	{
		double priority = 0.5;
		
		priority = 1 - (this.distance / (this.distance + other.distance));//priority is >= 0.5 if this is closer than other
		
		boolean result = false;
		if (priority >= 0.5)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
	
	public Point extrapolatePos(long currentTime)
	{
		return new Point(this.position.x + Math.sin(this.heading) * this.speed * (currentTime - this.timeStamp), this.position.y + Math.cos(this.heading) * this.speed * (currentTime - this.timeStamp));
	}
}