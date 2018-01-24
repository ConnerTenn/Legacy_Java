import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
//import java.util

class PopulationDataBase
{	
	List<Element> populationData;
	
	public PopulationDataBase()
	{
		populationData = new ArrayList<Element>();
		
	}
	
	public void readDataBaseFromFile(String filename) throws IOException
	{
		Scanner scanner = new Scanner(new FileReader(filename));
		scanner.nextLine();
		
		while (scanner.hasNextLine())
		{
			this.populationData.add(new Element(scanner.nextLine()));
		}
		
		scanner.close();
		scanner = null;
	}	
	
	public void saveDataBaseToFile(String filename) throws IOException
	{
		PrintWriter printer = new PrintWriter(new PrintWriter(filename));
		
		for (int i = 0; i < this.populationData.size(); i++)
		{
			printer.println(this.populationData.get(i));
		}
		
		printer.flush();
		printer.close();
		printer = null;
	}
	
	public void insert(String newCity, String newCountry, long newPop, String newDate)
	{
		
	}
	
	public void bubbleSort()
	{
		bubbleSort("population");
	}
	
	public void bubbleSort(String option)
	{
		Element register = null;
		
		boolean swap = false;
		//int j=0;
		do
		{
			//if (j++%1000 == 0){PopulationDBTest.printer.println(""+(j-1)); PopulationDBTest.printer.flush();}
			swap = false;
			for (int i = 0; i < this.populationData.size() - 1; i++)
			{
				//if ((this.populationData.get(i).population < this.populationData.get(i+1).population))
				if ((this.populationData.get(i).compare(this.populationData.get(i+1), option, "least")))
				{
					//PopulationDBTest.printer.println("" + this.populationData.get(i).population + " " + this.populationData.get(i+1).population); PopulationDBTest.printer.flush();
					register = this.populationData.get(i);
					this.populationData.set(i, this.populationData.get(i+1));
					this.populationData.set(i+1, register);
					register = null;
					swap = true;
				}
			}
		}
		while (swap);
	}
	
	public void insertSort()
	{
		Element register = null;
		int registerPos = 0;
		for (int max = this.populationData.size(); max>=1; max--)
		{
			register = null;
			registerPos = 0;
			for (int i = 0; i < max; i++)
			{
				if (register == null) { register = this.populationData.get(i); registerPos = 0; }
				if (this.populationData.get(i).compare(register,"population","least"))
				{
					register = this.populationData.get(i);
					registerPos = i;
				}
			}
			this.populationData.set(registerPos, this.populationData.get(max-1));
			this.populationData.set(max-1,register);
		}
	}
	
	//traverses the data and returns bounds of data of similar type
	private int[] findTypeBound(int begin, Element key, String Option)
	{
		int[] bounds = { 0, 0 };
		
		
		
		return bounds;
	}
	
	public String display()
	{
		return "";
	}
	
	public String display(int numLines)
	{
		return "";
	}
	
	public int getPopulation(String city)
	{
		return 0;
	}
	
	public int getPopulationByCountry(String country)
	{
		//return 0;
		Element key = new Element(); key.country = "China";
		binarySearch(0, this.populationData.size(), key, "country");
		return 0;
	}
	
	public int getNumCityByYear(int year)
	{
		//// TODO: 08-Dec-2016 Implement Binary Search 
		int count = 0;
		for (int i = 0; i < this.populationData.size(); i++)
		{
			if (this.populationData.get(i).year == year)
			{
				count++;
			}
		}
		return count;
	}
	
	public int binarySearch(int lower, int upper, Element key, String option)
	{
		//int half = this.populationData.size() / 2;
		int half = (upper + lower) / 2;
		//Element found = null;
		int found = -1;
		
		if (upper != lower)
		{
			//if (this.populationData.get(half).country.equals(country))
			if (this.populationData.get(half).compare(key, option, "equal"))
			{
				//found element
				//return half;//this.populationData.get(half);
				found = half;
			}
			//else if (this.populationData.get(half).country.compareTo(country) < 0)//if before country
			else if (this.populationData.get(half).compare(key, option, "least"))//if half is after key in the alphabet
			{
				found = binarySearch(lower, half, key, option);//test lower half
			}
			else //after country
			{
				found = binarySearch(half, upper, key, option);//test upper half
			}
		}
		return found;
	}
	
	public int size()
	{
		return this.populationData.size();
	}
}
