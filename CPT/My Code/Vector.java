package FirstTestPKG;
//import robocode.*;
import java.lang.Math;

public class Vector //Move to spearate file
{
	public static final double TAU = 6.28318530717958647692;
	
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
	
	/*public void calculatePerpendicular(Vector vector)
	{
		Point avg = new Point((vector.begin.x + vector.end.x) / 2.0, (vector.begin.y + vector.end.y) / 2.0);
		Point rotX = Point.rotate(new Point(vector.begin.x - avg.x, vector.begin.y - avg.y), TAU/4.0);
		this.begin = avg;
		this.end = rotX;
	}*/
	
	public static Vector calculatePerpendicular(Vector vector)
	{
		Point avg = new Point((vector.begin.x + vector.end.x) / 2.0, (vector.begin.y + vector.end.y) / 2.0);
		Point rotX = Point.rotate(new Point(vector.begin.x - avg.x, vector.begin.y - avg.y), TAU/4.0);
		return new Vector(avg, rotX);
	}
}

