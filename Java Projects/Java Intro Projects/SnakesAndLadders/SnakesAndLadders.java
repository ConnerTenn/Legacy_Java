
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SnakesAndLadders
{
	static Scanner scanner = new Scanner(System.in);
	//static Scanner scanner = new Scanner(new FileReader("In.txt"));
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	//data of where to jump to
	//format: { Source, Destination }
	static int[][] Jumps = 
		{ 
			{ 9, 34 }, { 40, 64 }, { 67, 86 },
			{ 54, 19 }, { 90, 48 }, { 99, 77 }
		};
	//current player Pos
	static int[] Pos = {0, 0};
	static int CurrentPlayer = 0;
	
	public static int DoJumps(int pos)
	{
		boolean jump = false;
		//go through each snake and ladder
		for (int i = 0; i < Jumps.length && !jump; i++)
		{
			//if on square with snake or ladder
			if (pos == Jumps[i][0])
			{
				//move to new square
				pos = Jumps[i][1];
				//stop searching for snakes and ladders
				jump = true;
			}
		}
		return pos;
	}
	
	public static void main(String[] args)
	{
		int dice = 0;
		boolean win = false;
		
		//keep running until win
		while(!win)
		{
			//display Pos
			printer.print("Player " + (CurrentPlayer + 1) + ": You are on Square: " + Pos[CurrentPlayer] + "\n Enter Dice Number:");
			printer.flush();
			//get next roll
			dice = scanner.nextInt();
			
			//update Pos
			Pos[CurrentPlayer] += dice;
			
			Pos[CurrentPlayer] = DoJumps(Pos[CurrentPlayer]);
			
			//if player has reached the end
			if (Pos[CurrentPlayer] >= 100)
			{
				win = true;
			}
			
			if (!win)
			{
				CurrentPlayer++;
				if (CurrentPlayer >= Pos.length) { CurrentPlayer = 0; }
			}
		}
		//display winning message
		printer.println( "Player " + (CurrentPlayer + 1) + " Wins!");
		
		//cleanup
		scanner.close();
		printer.flush();
		printer.close();
	}
}
