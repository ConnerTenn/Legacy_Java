import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

public class PopulationDBTest
{
	static Scanner scanner;
	static PrintWriter printer;
	
	public static void main(String[] args) throws IOException
	{
		//scanner = new Scanner(new FileReader("AlgorithmsIntro/populations.txt"));
		scanner = new Scanner(System.in);
		//printer = new PrintWriter(new PrintWriter("AlgorithmsIntro/Out.txt"));
		printer = new PrintWriter(System.out);
		
		
		PopulationDataBase dataBase = new PopulationDataBase();
		dataBase.readDataBaseFromFile("populations.txt");
		//dataBase.bubbleSort();
		//dataBase.insertSort();
		dataBase.bubbleSort("country");
		Element key = new Element(); key.country = "China";
		printer.println("Found: " + dataBase.populationData.get(dataBase.binarySearch(0, dataBase.size(), key, "country")));
		dataBase.saveDataBaseToFile("Out.txt");
		
		scanner.close();
		printer.flush();
		printer.close();
	}
}