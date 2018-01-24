import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Time;
import java.util.Scanner;

import javax.swing.*;

public class ProblemSolverCRV2 extends JComponent {

	int animateTime = 100;
	int[][] Array;
	boolean[][] HorizontalList;
	boolean[][] VerticalList;

	int Size = 0;
	boolean Play = true;

	// Reid version
	public void Display()
	{
		for (int y = 0; y < Size; y++)
		{
			for (int x = 0; x < Size; x++)
			{
				if (Array[x][y] < 10) {
					System.out.print(Array[x][y]+"  ");
				} else {
					System.out.print(Array[x][y]+" ");
				}
			}
			System.out.println();
		}
	}

	public void Init()
	{
		Array = new int[Size][Size];

		for (int y = 0; y < Size; y++)
		{
			for (int x = 0; x < Size; x++)
			{
				Array[y][x] = 0;
			}
		}

		HorizontalList = new boolean[Size][Size];

		for (int i = 0; i < Size; i++)
		{
			for (int n = 0; n < Size; n++)
			{
				HorizontalList[i][n] = true;
			}
		}

		VerticalList = new boolean[Size][Size];

		for (int i = 0; i < Size; i++)
		{
			for (int n = 0; n < Size; n++)
			{
				VerticalList[i][n] = true;
			}
		}
	}


	boolean CheckValidDiagonalPlace(int x, int n)
	{
		for (int i = x - 1; i >= 0; --i)
		{
			if (Array[Size - i - 1][i] == n + 1) 
			{ return false; }
		}
		return true;
	}

	boolean OnDiagonal(int x, int y)
	{
		if (x == y) { return true; }
		if (y == -x + Size - 1) { return true; }
		return false;
	}

	boolean Calculate() throws InterruptedException
	{
		int x = 0;
		int y = 0;
		int n = 0;
		boolean proceed = false;

		//down right
		for (int i = 0; i < Size; i++)
		{
			Array[i][i] = i + 1; VerticalList[i][i] = false; HorizontalList[i][i] = false;
			repaint();
			Thread.sleep(animateTime);
		}

		//up right
		int i = 0;
		while (i < Size)
		{
			if (!(i == Size - i - 1))
			{
				if (VerticalList[Size - i - 1][n] && HorizontalList[i][n] && CheckValidDiagonalPlace(i, n))//valid place
				{
					//set number and move on
					Array[Size - i - 1][i] = n + 1; VerticalList[Size - i - 1][n] = false; HorizontalList[i][n] = false;
					++i; n = 0;
					repaint();
					Thread.sleep(animateTime);
				}
				else
				{
					//check next number
					++n;
				}
				if (n >= Size)
				{
					//backtrack
					do
					{
						--i;
					}
					while (i == Size - i - 1);
					if (i < 0) { return false; }
					n = Array[Size - i - 1][i];
				}
			}
			else
			{
				++i;
			}
		}

		n = 0;

		//until end case
		while (y < Size)
		{
			proceed = false;

			while (!proceed && n < Size)
			{
				if (OnDiagonal(x, y))
				{
					proceed = true; n = 0; ++x;
					if (x >= Size) { ++y; x = 0; }
				}
				else
				{
					if (VerticalList[y][n] && HorizontalList[x][n])
					{
						Array[y][x] = n + 1; VerticalList[y][n] = false; HorizontalList[x][n] = false;
						repaint();
						Thread.sleep(animateTime);
						proceed = true; n = 0; ++x;
						if (x >= Size) { ++y; x = 0; }
					}
					else
					{
						++n;
					}
				}
			}
			if (!proceed) 
			{
				do
				{
					--x;
				}
				while (OnDiagonal(x, y));
				if (x < 0) { x = Size - 1; --y; }
				n = Array[y][x] - 1; Array[y][x] = 0;
				VerticalList[y][n] = true; HorizontalList[x][n] = true;
				++n;
				repaint();
				Thread.sleep(animateTime);
			}
		}

		return true;
	}

	public void paint(Graphics g)
	   {
	     // Clear screen
		   g.setColor(Color.white);
		   g.fillRect(0, 0, 500, 500);
		   
		   g.setColor(Color.black);
		   g.setFont(new Font("Courier", Font.PLAIN, 18));
			for (int y = 0; y < Size; y++)
			{
				for (int x = 0; x < Size; x++)
				{
					g.drawString(""+Array[x][y], 20+x*30, 20+y*20);
				}
			}		   
	   }


	

	
	// Constructor
	public ProblemSolverCRV2() throws InterruptedException 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Grid Size:"); 
		Size = sc.nextInt();
	
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    frame.setSize(500,500);  
	    frame.add(this); 
	    frame.setVisible(true); 
		
	    Init();
		
	    long startTime = System.currentTimeMillis();
		System.out.println("Started: "+ new Time(startTime));
		boolean result = Calculate();
		if (result)
		{
			System.out.println("\nSolved:\n");
			Display();
		}
		else	
		{	
			System.out.println("No Soloution\n");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Finsihed: "+ new Time(endTime));
		System.out.println("Total time: "+(endTime-startTime));
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new ProblemSolverCRV2();
	}
}
