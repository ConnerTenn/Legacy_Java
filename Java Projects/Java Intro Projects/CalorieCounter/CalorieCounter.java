
import java.io.PrintWriter;
import java.util.Scanner;

public class CalorieCounter
{
	static Scanner scanner = new Scanner(System.in);
	//static Scanner scanner = new Scanner(new FileReader("In.txt"));
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	//stores the calorie data for each selection
	static int[][] Data = 
		{
			{461, 431, 420},
			{100, 57, 70},
			{130, 160, 118},
			{167, 266, 75}
		};
	//choice names
	static String[] Label =
		{
			"Burger",
			"Side",
			"Drink",
			"Dessert"
		};
	
	public static void main(String[] args)
	{
		//init vars
		int Calories = 0;
		
		//loop through each menu section
		for (int i = 0; i < Data.length; i++)
		{
			//input choice
			printer.print(Label[i] + ":"); printer.flush();
			int choice = scanner.nextInt();
			
			//check if choice is in the appropriate range
			if (choice > 0 && choice - 1 < Data[i].length)
			{
				//add to the calorie total from the data array
				Calories += Data[i][choice - 1];
			}
		}
		printer.print("Total Calories:" + Calories);
		
		//cleanup
		scanner.close();
		printer.flush();
		printer.close();
	}
}
