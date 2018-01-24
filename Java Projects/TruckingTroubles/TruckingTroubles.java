package TruckingTroubles;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class TruckingTroubles
{
	static Scanner scanner;
	static PrintWriter printer;
	
	static int finalWeight = -1;
	
	//calculate the maximum allowed weight (minimum of current and new)
	public static void updateWeight(int weight)
	{
		if (finalWeight == -1)
		{
			finalWeight = weight;
		}
		else
		{
			if ( weight < finalWeight ) { finalWeight = weight; }
		}
	}

	public static void main(String[] args) throws IOException
	{
		long beginTime = System.currentTimeMillis();

		scanner = new Scanner(new FileReader("TruckingTroubles/In.txt"));
		//scanner = new Scanner(System.in);
		printer = new PrintWriter(new PrintWriter("TruckingTroubles/Out.txt"));
		//printer = new PrintWriter(System.out);

		City[] cities;
		Bridge[] bridges;

		int numCities = scanner.nextInt();
		int numBridges = scanner.nextInt();
		int numDestinations = scanner.nextInt();

		cities = new City[numCities];
		for (int i = 0; i < numCities; ++i) { cities[i] = new City(); cities[i].id = i; }
		//init cities

		bridges = new Bridge[numBridges];

		for (int i = 0; i < numBridges; ++i)
		{
			int startCity = scanner.nextInt() - 1;
			int endCity = scanner.nextInt() - 1;
			int weight = scanner.nextInt();
			
			bridges[i] = new Bridge(new City[]{cities[startCity], cities[endCity]}, weight);
			cities[startCity].bridges.addElement(bridges[i]);
			cities[endCity].bridges.addElement(bridges[i]);
			//bridges[i] = new Bridge({cities[startCity], cities[endCity]}, weight);
		}
		
		//set destinations
		for (int i = 0; i < numDestinations; ++i)
		{
			int city = scanner.nextInt() - 1;
			cities[city].destination = true;
		}
		
		//run
		/*int weight = -2;
		for (int i = 0; i < numDestinations; ++i)
		{
			TruckingTroubles.printer.println("CONTROL::Runnig for Destination:" + i); TruckingTroubles.printer.flush();
			int newWeight = cities[0].route(-2, 1);
			if (weight < 0)
			{
				weight = newWeight;
			}
			else if (newWeight < weight)
			{
				weight = newWeight;
			}
		}*/
		
		
		/*do
		{
			printer.println("CONTROL::Runnig for Destination. Current Weight:" + finalWeight); printer.flush();
		}
		while (cities[0].route(-1, 1));*/
		printer.println("\nCONTROL::Begin"); printer.flush();
		cities[0].route(-1, 1); printer.flush();
		printer.println("CONTROL::Finish"); printer.flush();
		
		for (int i = 0; i < cities.length; ++i)
		{
			if (cities[i].destination) { updateWeight(cities[i].bestWeight); }
		}
		
		printer.println("Weight:" + finalWeight + " Time:" + (System.currentTimeMillis()-beginTime)/1000.0); printer.flush();

		scanner.close();
		printer.flush();
		printer.close();
	}
}
