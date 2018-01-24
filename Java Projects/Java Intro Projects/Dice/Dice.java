
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Dice
{
	static Scanner scanner = new Scanner(System.in);
	//static Scanner scanner = new Scanner(new FileReader("In.txt"));
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	public static void main(String[] args)
	{
		//init vars
		int dice1 = scanner.nextInt();
		int dice2 = scanner.nextInt();
		int count = 0;
		
		//loop for numbers 1 to 9 inclusive
		for (int i = 1; i < 10; i++)
		{
			//check if each dice goes up to this number and 10 - the number
			//if true, this combo will add up to 10
			if (dice1 >= i && dice2 >= 10 - i)
			{
				//increment the count
				count++;
			}
		}
		
		//display output
		printer.println("Count:" + count);
		
		//cleanup
		scanner.close();
		printer.flush();
		printer.close();
	}
}