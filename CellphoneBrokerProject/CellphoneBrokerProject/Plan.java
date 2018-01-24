
package CellphoneBrokerProject;

import java.util.Vector;
import java.util.List;


public class Plan
{
	public String name = "";
	public float monthlyCost;
	public float minuteRate;
	public float textRate;
	
	public Plan(int monthlyCost, int minuteRate, int textRate)
	{
		this.monthlyCost = monthlyCost;
		this.minuteRate = minuteRate;
		this.textRate = textRate;
	}
	
	public Plan(int monthlyCost, int minuteRate, int textRate, String name)
	{
		this(monthlyCost, minuteRate, textRate);
		this.name = name;
	}
	
	public float getMonthlyCost()
	{
		float cost = 0;
		
		cost += this.monthlyCost;
		
		return cost;
	}
}
