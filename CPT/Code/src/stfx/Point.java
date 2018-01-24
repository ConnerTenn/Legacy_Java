
package stfx;
//import robocode.*;
//import java.lang.Math;

public class Point
{
	public static final double TAU = 6.28318530717958647692;
	public static final double PI = 3.14159265358979323846;
	
	public double x;
	public double y;
	
	//constructors
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
	
	/*public void rotate(Point point, double angle)
	{
		this.x = point.x * Math.cos(angle) + point.y * Math.sin(angle);
		this.y = -point.x * Math.sin(angle) + point.y * Math.cos(angle);
	}*/
	
	/**
	 * rotate a point by an angle
	 * @param point point to be rotated
	 * @param angle angle to rotate by
	 * @return rotated point
	 */
	public static Point rotate(Point point, double angle)
	{
		return new Point(point.x * Math.cos(angle) + point.y * Math.sin(angle), -point.x * Math.sin(angle) + point.y * Math.cos(angle));
	}
	
	public String toString()
	{
		return "X:" + this.x + " Y" + this.y;
	}
}

