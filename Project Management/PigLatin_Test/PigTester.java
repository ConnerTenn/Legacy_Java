
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
	
	//find the devisors that are closest to the squareroot
	public static int[]FindDevisors(int number)
	{
		//get the floor square root
		int sqrt = (int)Math.sqrt(number);
		
		//initialize min and max
		int min = sqrt, max = sqrt;
		
		//loop until min reaches 0
		for (; min > 0; min--)
		{
			//printer.print( min + "  ");
			if ((double)number / (double)min == (double)(number / min))
			{
				//found pair of devisors
				return new int[] {min, number/min};
			}
			if ((double)number / (double)max == (double)(number / max))
			{
				//found pair of devisors
				return new int[] {number/max,min};
			}
			//increment max
			max++;
		} 
		
		//should never occur 
		return new int[] {-1, -1};
	}
	
	public static void main(String[] args) throws IOException
	{
		//init var
		int numPhotos = 0;
		
		//process input until user enters 0
		do
		{
			printer.print(">"); //output Prompt
			printer.flush(); //make sure it is displayed 
			numPhotos = scanner.nextInt(); //get value
			
			//find good dimensions
			if (numPhotos > 0) { printer.println("Dimensions: X:" + FindDevisors(numPhotos)[0] + " Y:"+ FindDevisors(numPhotos)[1]); }
			//printer.println(numPhotos);
			//printer.println((int)Math.sqrt(numPhotos));
			//printer.println((int)Math.ceil(Math.sqrt(numPhotos)));
		}
		while (numPhotos > 0);
		
		//printer.println("\n\n");
		//printer.println("Devisors: " 