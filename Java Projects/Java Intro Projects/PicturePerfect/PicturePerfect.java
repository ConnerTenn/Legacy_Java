
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PicturePerfect
{
	static Scanner scanner = new Scanner(System.in);
	//static Scanner scanner = new Scanner(new FileReader("In.txt"));
	static PrintWriter printer = new PrintWriter(System.out);
	//static PrintWriter printer = new PrintWriter(new FileWriter("Out.txt"));
	
	//return an array of the dimensions
	public static int[] FindDimensions(int num)
	{
		int[] out = { -1, -1 };
		int sqrt = (int)Math.sqrt(num);
		//int min = sqrt, max = sqrt;
		
		boolean found = false;
		
		//test for which of the dimension are valid without overlap
		for (int min = sqrt, max = sqrt; min > 0 && !found; min--, max++)
		{
			//test if the lower dimension is valid
			//if ((double)num / (double)min == (double)(int)(num / min))
			if (num % min == 0 )
			{
				out = new int[]{ min, num / min }; found = true;
			}
			//test if the upper dimension is valid
			//if ((double)num / (double)max == (double)(int)(num / max))
			if (num % max == 0 )
			{
				out = new int[]{ num / max, max }; found = true;
			}
		}
		
		//this should never return unless an error has occured (negative numbers?)
		return out;
	}
	
	public static void main(String[] args)
	{
		int numPictures = 0;
		
		//loop until user enters 0
		do
		{
			//get input
			numPictures = scanner.nextInt();
			
			//test if exit command has been given
			if (numPictures > 0)
			{
				//get dimensions 
				int[] dimensions = FindDimensions(numPictures);
				//print result
				printer.println("X:" + dimensions[0] + " Y:" + dimensions[1]);
				printer.flush();
			}
		}
		while (numPictures > 0);
		
		//cleanup
		scanner.close();
		printer.flush();
		printer.close();
	}
}
