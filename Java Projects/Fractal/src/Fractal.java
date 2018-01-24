import javax.swing.*;
import java.lang.Math;

import java.awt.*;

/**
* The Fractal class is used to demonstrate recursive algorithm design
* @author Mr. Reid
* @date Revised 2015 (AWT), Original May 2007
* @course ICS4U
*/
public class Fractal extends JComponent
{
	//static Console c;           // The output console
	Graphics g = null;
	
	public Fractal() throws java.lang.InterruptedException
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // exit program when you close it
		frame.setSize(1400,800);  // set the size of the window to whatever width and height you like
		frame.add(this); // put an object we can draw on in the centre of the window
		frame.setVisible(true); //show the window
		
		while (true)
		{
			repaint();
			Thread.sleep(500);
		}
	}
	
	/**
	* Draw a box recursively within itself
	*/
	public void drawBoxRecursively (int x, int y, int width, int height, Color c)
	{
		this.g.setColor (c);
		//this.g.fillRect (x, y, width, height);
		this.g.drawRect (x, y, width, height);
		
		// Base case
		if (width < 1)
		{
			this.g.setColor (Color.RED);
		}
		else
		{
			// Recursively call
			drawBoxRecursively (x + (width / 4), y + (height / 4), width / 2, height / 2, c.darker());
		}
	}
	
	/**
	* Draw a line and cross each end recursively
	*/
	public void drawCrosses(int x1, int y1, int x2, int y2)
	{
		double div = 3;
		
		// Draw the original line
		this.g.drawLine(x1, y1, x2, y2);
		
		// Find next lines (either end)
		
		if (y1 == y2) // Horizontal line
		{
			double length = Math.abs(x2 - x1);
			if (length > 1)
			{
				drawCrosses(x1, (int)(y1 - length/div), x1, (int)(y2 + length/div));
				drawCrosses(x2, (int)(y1 - length/div), x2, (int)(y2 + length/div));
			}
		}
		else
		{
			float length = Math.abs(y2 - y1);
			if (length > 10)
			{
				drawCrosses((int)(x1 - length/div), y1, (int)(x2 + length/div), y1);
				drawCrosses((int)(x1 - length/div), y2, (int)(x2 + length/div), y2);
			}
		}
		
	}
	
	public double rotX(double x, double y, double a) { return x*Math.cos(a)+y*Math.sin(a); }
	public double rotY(double x, double y, double a) { return -x*Math.sin(a)+y*Math.cos(a); }
	
	public void drawDragonFractal(int count)
	{
		drawDragonRecursion(700-200,400,700+200,400,count);
	}
	
	public void drawDragonRecursion(double x1, double y1, double x2, double y2, int count)
	{
		if (count > 0)
		{
			//
			//x*cos(a)+y*sin(-a)
			//-x*sin(a)+y*cos(a)
			//
			/*drawDragonRecursion(x2+(int)(x1*Math.cos(Math.PI/2)+y1*Math.sin(-Math.PI/2)), y2+(int)(-x1*Math.sin(Math.PI/2)+y1*Math.cos(Math.PI/2)),
					x2+(int)(x2*Math.cos(Math.PI/2)+y2*Math.sin(-Math.PI/2)), y2+(int)(-x2*Math.sin(Math.PI/2)+y2*Math.cos(Math.PI/2)),
					count -1);*/
			//perpendicular line
			
			double avgX = (x1+x2)/2.0, avgY = (y1+y2)/2.0;
			//double newX = this.rotX(x1-avgX,y1-avgY, Math.PI/2.0) + avgX, newY = this.rotY(x1-avgX,y1-avgY, Math.PI/2.0) + avgY;
			double newX = this.rotX(avgX-x1,avgY-y1, Math.PI/2.0) + avgX, newY = this.rotY(avgX-x1,avgY-y1, Math.PI/2.0) + avgY;
			
			drawDragonRecursion(x1, y1, newX, newY, count - 1);
			drawDragonRecursion(x2, y2, newX, newY, count - 1);
			//drawDragonRecursion(newX, newY, x1, y1, count - 1);
			//drawDragonRecursion(newX, newY, x2, y2, count - 1);
		}
		else
		{
			this.g.drawLine((int)(x1+00), (int)(y1+00), (int)(x2+00), (int)(y2+00));
		}
	}
	
	static int itteration  = 0;
	public void paint(Graphics g)
	{
		this.g = g;
		//this.drawBoxRecursively (0, 0, 800, 800, Color.BLUE);
		
		//this.drawCrosses(300, 400, 900, 400);
		if (itteration < 20) { itteration++; }
		this.drawDragonFractal(itteration);
		
		//System.out.println("Done!");
	}
	
	public static void main (String[] args) throws java.lang.InterruptedException
	{
		new Fractal();
	} // main method
} // Fractal class


