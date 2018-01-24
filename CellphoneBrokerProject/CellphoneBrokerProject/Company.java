
package CellphoneBrokerProject;

import java.util.Vector;
import java.util.List;


public class Company
{
	public String name;
	public Vector<Plan> planList;
	public Vector<Consumer> consumerList;
	
	public Company()
	{
		this.planList = new Vector<Plan>();
		this.consumerList = new Vector<Consumer>();
		this.name = "";
	}
	
	public Company(String name)
	{
		this.name = name;
		this.planList = new Vector<Plan>();
		this.consumerList = new Vector<Consumer>();
	}
	
	public Company(String name, Vector<Plan> planList)
	{
		this.name = name;
		this.planList = planList;
		this.consumerList = new Vector<Consumer>();
	}
	
	public Company(String name, Vector<Plan> planList, Vector<Consumer> consumerList)
	{
		this.name = name;
		this.planList = planList;
		this.consumerList = consumerList;
	}
	
	public Plan getPlan(int planID)
	{
		return planList.elementAt(planID);
	}
	
	public Vector<Plan> getPlans()
	{
		return planList;
	}
	
	public Consumer getConsumer(int consumerID)
	{
		return consumerList.elementAt(consumerID);
	}
	
	public void addConsumer(Consumer consumer)
	{
		consumerList.addElement(consumer);
	}
	
	public float calculateTotalIncome()
	{
		float cost = 0;
		
		for (int i = 0; i < consumerList.size(); i++)
		{
			cost += consumerList.elementAt(i).getMonthlyCost();
		}
		
		
		return cost;
	}
}
