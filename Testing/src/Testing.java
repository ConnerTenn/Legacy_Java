
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Testing
{
	static Scanner scanner;
	static PrintWriter printer;
	
	public static void main(String[] args)
	{
		scanner = new Scanner(System.in);
		//scanner = new Scanner(new FileReader("TruckingTroubles/In.txt"));
		printer = new PrintWriter(System.out);
		//printer = new PrintWriter(new PrintWriter("TruckingTroubles/Out.txt"));
		
		printer.println("Hello World");
		
		scanner.close();
		printer.flush();
		printer.close();
	}
}
