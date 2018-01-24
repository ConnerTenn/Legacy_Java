package SlotMachines;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class SlotMachines
{
	static Scanner scanner;
	static PrintWriter printer;
	
	public static void main(String[] args)
	{
		//scanner = new Scanner(new FileReader("In.txt"));
		scanner = new Scanner(System.in);
		printer = new PrintWriter(System.out);
		
		printer.print("How many quarters? "); printer.flush();
		Player player = new Player(scanner.nextInt());
		
		Machine[] machines = new Machine[3];
		printer.print("How many times has the first machine been played since paying out? "); printer.flush();
		machines[0] = new Machine(30, 35, scanner.nextInt());
		
		printer.print("How many times has the second machine been played since paying out? "); printer.flush();
		machines[1] = new Machine(60, 100, scanner.nextInt());
		
		printer.print("How many times has the third machine been played since paying out? "); printer.flush();
		machines[2] = new Machine(9, 10, scanner.nextInt());
		
		int count = 0;
		do { count++; } while (player.play(machines));
		printer.println(count + " times before broke."); printer.flush();
		
		scanner.close();
		printer.flush();
		printer.close();
	}
}