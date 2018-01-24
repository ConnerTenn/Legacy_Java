
package stfx;
//import robocode.*;
import java.lang.Math;

/**
 * Class which encapsulates a vector structure
 *
 */
public class Vector //Move to separate file
{
	public static final double TAU = 6.28318530717958647692;
	public static final double PI = 3.14159265358979323846;
	
	public Point begin;
	public Point end;
	
	//constructors
	public Vector()
	{
		begin = new Point();
		end = new Point();
	}
	
	public Vector(Point begin, Point end)
	{
		this.begin = new Point(begin);
		this.end = new Point(end);
	}
	
	public Vector(double x1, double y1, double x2, double y2)
	{
		this.begin = new Point(x1, y1);
		this.end = new Point(x2, y2);
	}
	
	public Vector(double x, double y, double angle)
	{
		this.begin = new Point(x, y);
		this.end = new Point(Math.sin(angle), -Math.cos(angle));
	}
	
	/**
	 * normalize this vector
	 */
	public void normalize()
	{
		double x = this.end.x - this.begin.x;
		double y = this.end.y - this.begin.y;
		this.end.x = x / Math.sqrt(x*x + y*y) + this.begin.x;
		this.end.y = y / Math.sqrt(x*x + y*y) + this.begin.y;
	}
	
	/**
	 * retturn a normalized vector
	 * @param vector
	 * @return normalized vector
	 */
	public Vector getNormalized(Vector vector)
	{
		double x = vector.end.x - vector.begin.x;
		double y = vector.end.y - vector.begin.y;
		return new Vector(vector.begin.x, vector.begin.y, x / Math.sqrt(x*x + y*y) + vector.begin.x, y / Math.sqrt(x*x + y*y) + vector.begin.y);
	}
	
	/**
	 * get the angle of this vector
	 * @return angle
	 */
	public double getAngle()
	{
		Vector normal = new Vector(this.begin, this.end);
		normal.normalize();
		
		//VectorBot.print("normal: " + normal + "\nAngle:" + Math.atan2(normal.end.x - normal.begin.x, normal.end.y - normal.begin.y) + "\n");
		return Math.atan2(normal.end.x - normal.begin.x, normal.end.y - normal.begin.y);
	}
	
	/*public void calculatePerpendicular(Vector vector)
	{
		Point avg = new Point((vector.begin.x + vector.end.x) / 2.0, (vector.begin.y + vector.end.y) / 2.0);
		Point rotX = Point.rotate(new Point(vector.begin.x - avg.x, vector.begin.y - avg.y), TAU/4.0);
		this.begin = avg;
		this.end = rotX;
	}*/
	
	//TODO Add parameter to specify output side
	/**
	 * get a vector perpendicular to the input vector
	 * @param vector input vector
	 * @param side side that the returned vector is to be on. True: right side, False: Left side
	 * @return returns a vector perpendicular to the input
	 */
	public static Vector calculatePerpendicular(Vector vector, boolean side)
	{
		double offset = 1.0;
		if (!side)
		{
			offset = 3.0;
		}
		Point avg = new Point((vector.begin.x + vector.end.x) / 2.0, (vector.begin.y + vector.end.y) / 2.0);
		Point rot = Point.rotate(new Point(vector.begin.x - avg.x, vector.begin.y - avg.y), offset*TAU/4.0);
		//rot.x += avg.x;
		//rot.y += avg.y;
		return new Vector(avg, rot);
	}
	
	public String toString()
	{
		return "Begin:" + this.begin + " End:" + this.end;
	}
}

