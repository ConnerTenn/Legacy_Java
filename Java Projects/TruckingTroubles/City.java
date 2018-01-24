package TruckingTroubles;
import java.util.Vector;

public class City
{
	//public Bridge[] bridges;
	public Vector<Bridge> bridges = new Vector<Bridge>();
	public boolean visited = false;
	public boolean destination = false;
	//public boolean visitedDestination = false;
	public int bestWeight = -1;
	public int id = -1;
	
	public City()
	{
		
	}
	
	//find the maximum weight
	public void updateWeight(int weight)
	{
		if (this.bestWeight == -1)
		{
			this.bestWeight = weight;
		}
		else
		{
			if ( weight > this.bestWeight ) { this.bestWeight = weight; }
		}
	}
	
	public void print(String string, int tab)
	{
		for (int i = 0; i < tab; ++i)
		{
			TruckingTroubles.printer.print("\t");
		}
		TruckingTroubles.printer.println(string);
	}
	
	//return
	//true: found destination
	//false: not found destination
	
	
	public void route(int weight, int layer)
	{
		//print("Begin Route ClassID:" + (this.id + 1) + "", layer); 
		
		if (this.visited) { /*print("Already Visited", layer); print("Return", layer);*/ return; }// false; }
		
		if (this.destination)
		{
			if (this.bestWeight == -1)
			{
				updateWeight(weight);
				//print("Found new Destination,  BestWeight:" + this.bestWeight, layer);
				
				//this.visitedDestination = true;
				return;// true;
			}
			else
			{
				updateWeight(weight);
				//print("Passing Previous Destination,  BestWeight:" + this.bestWeight, layer);
			}
		}
		
		this.visited = true;
		
		for (int i = 0; i < bridges.size(); i++)
		{
			//boolean result = false;
			
			//weight to send to next city. 
			//is the curring weight possible
			int newWeight = weight;// weight < this.bridges.elementAt(i).maxWeight ? weight : this.bridges.elementAt(i).maxWeight; 
			
			//print("Travelling Along Bridge:" + i + "  Weight:" + this.bridges.elementAt(i).maxWeight, layer);
			
			if (weight != -1)
			{
				if (this.bridges.elementAt(i).maxWeight < weight)
				{
					newWeight = this.bridges.elementAt(i).maxWeight;
				}
			}
			else
			{
				newWeight = this.bridges.elementAt(i).maxWeight;
			}
			
			//find other end of the bridge
			if (this.bridges.elementAt(i).cities[0] != this)
			{
				//result = 
				this.bridges.elementAt(i).cities[0].route(newWeight, layer + 1);
			}
			else
			{
				//result = 
				this.bridges.elementAt(i).cities[1].route(newWeight, layer + 1);
			}
			//if ( result ) { this.visited = false; print("Return True", layer); return; }// result; }
		}
		
		//print("Return", layer);
		this.visited = false;
		return;// false;
	}
	
	//return > 1 if found destination
	/*public int route(int weight, int layer)
	{
		//TruckingTroubles.printer.println(); TruckingTroubles.printer.flush();
		print("",layer);
		print("Begin Route ID:" + this.id + "", layer); 
		
		if (this.visited) { print("Already Visited", layer); print("Return -1", layer); return -1; }
		this.visited = true;
		
		if (this.destination)
		{
			if (!this.visitedDestination)
			{
				print("Found new Destination", layer);
				print("Return Value:" + weight, layer);
				this.visitedDestination = true;
				return weight;
			}
			else
			{
				print("Passing Previous Destination", layer);
			}
		}
		
		//go along each bridge
		for (int i = 0; i < bridges.size(); i++)
		{
			int result = -1;
			
			int newWeight = weight;// weight < this.bridges.elementAt(i).maxWeight ? weight : this.bridges.elementAt(i).maxWeight; 
			
			print("Travelling Along Bridge:" + i, layer);
			
			if (weight != -2)
			{
				if (this.bridges.elementAt(i).maxWeight < weight)
				{
					newWeight = this.bridges.elementAt(i).maxWeight;
				}
			}
			else
			{
				newWeight = this.bridges.elementAt(i).maxWeight;
			}
			
			//find other end of the bridge
			if (this.bridges.elementAt(i).cities[0] != this)
			{
				result = this.bridges.elementAt(i).cities[0].route(newWeight, layer + 1);
			}
			else
			{
				result = this.bridges.elementAt(i).cities[1].route(newWeight, layer + 1);
			}
			if ( result > 0 ) { this.visited = false; print("Return Value:" + result, layer); return result; }
		}
		
		print("Return -1", layer);
		this.visited = false;
		return -1;
	}*/
}
