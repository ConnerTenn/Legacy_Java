import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.sql.Time;
import java.util.Scanner;

import javax.swing.*;

public class ProblemSolverCR extends JComponent {

	int animateTime = 1;
	int[][] Array;
	int Size = 0;
	
	public boolean CheckValidPlace(int x, int y, int num)
	{
		if (x >= Size || y >= Size) { return false; }
	
		if (Array[x][y] != 0) { return false; }
	
		//check vertical
		for (int i = y; i >= 0; --i)
		{
			if (Array[x][i] == num) { return false; }
		}
	
		//check diagonal
		//check if on up left diagonal
		if (y == x)
		{
			for (int i = y; i >= 0; --i)
			{
				if (Array[i][i] == num) { return false; }
			}
		}
		//check if on down left diagonal
		if (y == -x + Size - 1)
		{
			for (int i = y; i >= 0; --i)
			{
				if (Array[i][Size - i - 1] == num) { return false; }
			}
		}		
		return true;
	}
	
	
	public boolean CheckValidDiagonal(int num)
	{
		boolean containNum = false;
		//check if on up left diagonal
		for (int i = 0; i < Size && !containNum; ++i)
		{
			if (Array[i][i] == num) { containNum = true; }
		}
	
		if (!containNum) { return false; }
		containNum = false;
	
		//check if on down left diagonal
		for (int i = 0; i < Size && !containNum; ++i)
		{
			if (Array[i][Size - i - 1] == num) 
			{ containNum = true; }
		}
	
		return containNum;
	}
	
	public int FindNumInRow(int y, int num)
	{
		for (int x = 0; x < Size; x++)
		{
			if (Array[x][y] == num) { return x; }
		}
		return -1;
	}
	
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

	
	public boolean Run() throws InterruptedException
	{
		int num = 1;
		int x = 0, y = 0;
		int lowestBack = Size;
		
		//until end case
		while (num <= Size)
		{
			while (y < Size)
			{
				if (CheckValidPlace(x, y, num))
				{
					Array[x][y] = num;
					x = 0;
					++y;
					this.repaint();
					Thread.sleep(animateTime);
					
				}
				else
				{
					++x;
					
					if (x >= Size)
					{
						//backtrack
						--y;
						if (y < 0) { 
							num--; 
							if (num < lowestBack)
							{
								lowestBack = num;
								System.out.println("Backtrack to "+num);
							}
							y = Size - 1;
							//x = 0; //Size - 1; // Reid - why not reset - to what value?
						}
						if (num <= 0) { 
							return false; 
						}						
						x = FindNumInRow(y, num);
						Array[x][y] = 0;
						this.repaint();
						Thread.sleep(animateTime);
						++x;
					}
				}
			}
			if (CheckValidDiagonal(num))
			{
				++num;
				//System.out.println(num);
				x = 0;
				y = 0;
			}
			else
			{
				--y;
				x = FindNumInRow(y, num);
				Array[x][y] = 0;
				++x;
			}
		}
		return true;
	}
	

	
	// Constructor
	public ProblemSolverCR() throws InterruptedException 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Grid Size:"); 
		Size = sc.nextInt();
	
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    frame.setSize(500,500);  
	    frame.add(this); 
	    frame.setVisible(true); 
		
		Array = new int[Size][Size];
		for (int j = 0; j < Size; j++)
		{
			for (int i = 0; i < Size; i++) 
			{ 
				Array[i][j] = 0; 
			}
		}

		System.out.println("Started: "+ new Time(System.currentTimeMillis()));
		//sc.next();
		if (Run())
		{
			System.out.println("\nSolved:\n");
			Display();
		}
		else	
		{	
			System.out.println("No Soloution\n");
		}
		System.out.println("Finsihed: "+ new Time(System.currentTimeMillis()));		
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new ProblemSolverCR();
	}
}
