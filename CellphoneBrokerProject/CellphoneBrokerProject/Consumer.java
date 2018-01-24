
package CellphoneBrokerProject;

import java.util.Vector;
import java.util.List;


public class Consumer
{
	public String name;
	public float totalCost = 0;
	public Vector<Company> companyList;
	public Vector<Plan> planList;
	public float minUsed = 0;

	public Consumer()
	{
		this.companyList = new Vector<Company>();
		this.planList = new Vector<Plan>();
	}
	
	public Consumer(String name)
	{
		this.name = name;
		this.companyList = new Vector<Company>();
		this.planList = new Vector<Plan>();
	}
	
	public Consumer(String name, Vector<Company> companyList, Vector<Plan> planList)
	{
		this.name = name;
		this.companyList = companyList;
		this.planList = planList;
	}
	
	public void addCompany(Company company)
	{
		companyList.addElement(company);
	}
	
	public void addPlan(Plan plan)
	{
		planList.addElement(plan);
	}
	
	public float getMonthlyCost()
	{
		float cost = 0;
		
		for (int i = 0; i < planList.size(); i++)
		{
			cost += planList.elementAt(i).getMonthlyCost();
		}
		
		return cost;
	}
}
