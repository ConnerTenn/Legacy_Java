
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class FloorPlan
{
	//static Scanner scanner = new Scanner(System.in);
	static Scanner scanner;
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	static double PI = 3.14159265358979323;
	static double TAU = 2.0 * PI;
	static int[][] Floor;
	static int Flooring;
	static int Height;
	static int Width;
	
	public static void Print()
	{
		for (int y = 0; y < Height; ++y)
		{
			for (int x = 0; x < Width; ++x)
			{
				int value = Floor[y][x];
				if (value == -2)
				{
					for (int i = 1-(0); i >0; --i) { printer.print(" "); }
					printer.print("#");
				}
				else if (value == -1)
				{
					for (int i = 1-(0); i >0; --i) { printer.print(" "); }
					printer.print(" ");
				}
				else
				{
					if (value !=0) { for (int i = 1 - (int)Math.log10(value); i > 0; --i) { printer.print(" "); } }
					else { printer.print(" "); }
					printer.print(value);
				}
				printer.print(" ");
			}
			printer.println();
		}
		printer.flush(); 
	}
	
	public static int Fill(int x, int y, int value)
	{
		int offx; int offy;
		
		for (int i = 0; i < 4; i++)
		{
			int n = (int)Math.cos((i / 4.0) * TAU);
			int m = (int)Math.sin((i / 4.0) * TAU);
			offx = x + n; offy = y + m;
			if (offx >= 0 && offx < Width && offy >= 0 && offy <Height && Floor[offy][offx] == -1)
			{
				value++;
				Floor[offy][offx] = value;
				value = Fill(offx, offy, value);
			}
		}
		
		return value;
	}
	
	public static int FloodFill(int x, int y)
	{
		Floor[y][x] = 0;
		return Fill(x, y, 0);
	}
	
	public static void main(String[] args) throws IOException
	{
		scanner = new Scanner(new FileReader("In.txt"));
		
		//printer.print("Flooring:"); printer.flush(); 
		Flooring = scanner.nextInt();
		//printer.print("Height:"); printer.flush(); 
		Height = scanner.nextInt();
		//printer.print("Width:"); printer.flush(); 
		Width = scanner.nextInt();
		
		Floor = new int[Height][Width];
		
		//read floor data
		for (int y = 0; y < Height; ++y)
		{
			String in = scanner.next();
			for (int x = 0; x < Width; ++x)
			{
				char data = in.charAt(x);
				if (data == 'I')
				{
					Floor[y][x] = -2;
				}
				else
				{
					Floor[y][x] = -1;
				}
			}
		}
		Print();
		printer.println(); printer.flush(); 
		
		List<Integer> roomList = new ArrayList<Integer>();;
		int x = 0, y = 0;
		while (y < Height)
		{
			if (Floor[y][x] == -1)
			{
				roomList.add(FloodFill(x,y) + 1);
			}
			++x;
			if (x >= Width) { ++y; x = 0; }
		}
		Collections.sort(roomList);
		Collections.reverse(roomList);
		
		int leftover = Flooring;
		int roomCount = 0;
		boolean done = false;
		for (int i = 0; i < roomList.size() && !done; i++)
		{
			printer.println(roomList.get(i)); printer.flush();
			if (leftover >= roomList.get(i)) { leftover -= roomList.get(i); roomCount++; }
			else { done = true; } 
		}
		
		Print();
		printer.println();
		printer.println("Rooms:" + roomCount + " Leftover:" + leftover);
		printer.flush(); 
		

		scanner.close();
		printer.flush();
		printer.close();
	}
}
