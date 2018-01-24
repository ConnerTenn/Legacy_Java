
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CellPhone
{
	static Scanner scanner = new Scanner(System.in);
	//static Scanner scanner = new Scanner(new FileReader("In.txt"));
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	//keayboard data
	//format: { delay, key number}
	static int[][] Data = 
		{
			//A         B         C
			{ 1, 2 }, { 2, 2 }, { 3, 2 },
			//D         E         F
			{ 1, 3 }, { 2, 3 }, { 3, 3 },
			//G         H         I
			{ 1, 4 }, { 2, 4 }, { 3, 4 },
			//J         K         L
			{ 1, 5 }, { 2, 5 }, { 3, 5 },
			//M         N         O
			{ 1, 6 }, { 2, 6 }, { 3, 6 },
			//P         Q         R         S
			{ 1, 7 }, { 2, 7 }, { 3, 7 }, { 4, 7 },
			//T         U         V
			{ 1, 8 }, { 2, 8 }, { 3, 8 },
			//W         X         Y         Z
			{ 1, 9 }, { 2, 9 }, { 3, 9 }, { 4, 9 },
		};
	
	public static void main(String[] args)
	{
		//input string
		String in = "";
		
		//loop until user enters "halt"
		do
		{
			in = scanner.next();
			//execute only if user enters halt
			if (!in.equals("halt"))
			{
				//init vars
				int time = 0;
				int previousButton = 0;
				
				//loop through each character
				for (char character: in.toCharArray()) 
				{
					//chack if user has to wait the time out delay (if they need to press the same button for a new character)
					if (previousButton == Data[character-'a'][1])
					{
						//add the time to reach the letter + 2 seconds for the dalay
						time += Data[character-'a'][0] + 2;
					}
					else
					{
						//add the time to reach the letter
						time += Data[character-'a'][0];
					}
					//set the previous button to the button just pressed
					previousButton = Data[character-'a'][1];
				}
				
				//display the results
				printer.println(time); printer.flush();
			}
		}
		while (!in.equals("halt"));
		
		//cleanup
		scanner.close();
		printer.flush();
		printer.close();
	}
}