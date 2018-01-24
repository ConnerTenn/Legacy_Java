
package CellphoneBrokerProject;

import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.List;


public class CellphoneBroker
{
	static Scanner scanner;
	static PrintWriter printer;
	
	public static <T> Vector<T> getSubList(Vector<T> list, int begin, int end)
	{
		return (new Vector<T>(list.subList(begin, end + 1)));
	}

	public static void main(String[] args)
	{
		//scanner = new Scanner(new FileReader("TruckingTroubles/In.txt"));
		scanner = new Scanner(System.in);
		//printer = new PrintWriter(new PrintWriter("TruckingTroubles/Out.txt"));
		printer = new PrintWriter(System.out);
		
		Vector<Plan> plans;
		Vector<Company> companyList;
		Vector<Consumer> consumers;
		
		{/*Create all the Plans, Companies, and Consumers*/}
		{
			//Available Plans
			plans = new Vector<Plan>();
			plans.addElement(new Plan(2,0,0));
			plans.addElement(new Plan(3,0,0));
			plans.addElement(new Plan(4,0,0));
			plans.addElement(new DataPlan(10, 0, 0, 0));
			
			//Available Companies
			companyList = new Vector<Company>();
			//companyList.addElement(new Company("Bell", new Vector<Plan>(plans.subList(0,2))));
			//companyList.addElement(new Company("Rogers", new Vector<Plan>(plans.subList(2,4))));
			//companyList.addElement(new Company("Fido", new Vector<Plan>(plans.subList(0,4))));
			companyList.addElement(new Company("Bell", getSubList(plans,0,1)));
			companyList.addElement(new Company("Rogers", getSubList(plans,2,3)));
			companyList.addElement(new Company("Fido", getSubList(plans,0,3)));
			
			//Consumers
			consumers = new Vector<Consumer>();
			//consumers.addElement(new Consumer("Joe", new Vector<Company>(companyList.subList(0,1)), new Vector<Plan>(companyList.elementAt(0).getPlans().subList(0,1))));
			//consumers.addElement(new Consumer("Bob", new Vector<Company>(companyList.subList(1,2)), new Vector<Plan>(companyList.elementAt(1).getPlans().subList(1,2))));
			//consumers.addElement(new Consumer("Mike", new Vector<Company>(companyList.subList(1,2)), new Vector<Plan>(companyList.elementAt(1).getPlans().subList(0,1))));
			//consumers.addElement(new Consumer("Nason", new Vector<Company>(companyList.subList(2,3)), new Vector<Plan>(companyList.elementAt(2).getPlans().subList(1,2))));
			consumers.addElement(new Consumer("Joe", getSubList(companyList,0,0), getSubList(companyList.elementAt(0).getPlans(),0,0)));
			consumers.addElement(new Consumer("Bob", getSubList(companyList,1,1), getSubList(companyList.elementAt(1).getPlans(),1,1)));
			consumers.addElement(new Consumer("Make", getSubList(companyList,1,1), getSubList(companyList.elementAt(1).getPlans(),0,0)));
			consumers.addElement(new Consumer("Nason", getSubList(companyList,2,2), getSubList(companyList.elementAt(2).getPlans(),1,1)));
			
			//Add customers to the Company
			companyList.elementAt(0).addConsumer(consumers.elementAt(0));
			companyList.elementAt(1).addConsumer(consumers.elementAt(1));
			companyList.elementAt(1).addConsumer(consumers.elementAt(2));
			companyList.elementAt(2).addConsumer(consumers.elementAt(3));
		}
		
		
		for (int i = 0; i < companyList.size(); i++)
		{
			printer.println(companyList.elementAt(i).name + " Income: " + companyList.elementAt(i).calculateTotalIncome()); printer.flush();
		}
		
		scanner.close();
		printer.flush();
		printer.close();
	}
}

