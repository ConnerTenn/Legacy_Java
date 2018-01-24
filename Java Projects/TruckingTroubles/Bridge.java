package TruckingTroubles;
import java.util.Vector;

public class Bridge
{
	public City[] cities;
	public int maxWeight;
	
	public Bridge(City[] cities, int maxWeight)
	{
		this.cities = cities;
		this.maxWeight = maxWeight;
	}
}