
package CellphoneBrokerProject;

import java.util.Vector;
import java.util.List;


public class DataPlan extends Plan
{
	public float dataRate;
	
	public DataPlan(int monthlyCost, int minuteRate, int textRate, int dataRate)
	{
		super(monthlyCost, minuteRate, textRate);
		this.dataRate = dataRate;
	}
	
	public float getMonthlyCost()
	{
		float cost = this.monthlyCost;
		
		return cost;
	}
}
