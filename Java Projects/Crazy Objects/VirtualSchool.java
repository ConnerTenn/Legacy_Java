/**
 s* The "VirtualSchool" class for the CrazyObjects problem.
 * @author Mr. Reid
 * @date Apr 2013
 * @version 2.2
 */
public class VirtualSchool
{
	/**
	*  Demo test driver for the Crazy Objects problem
	*/
	public static void main (String[] args)
	{   // Create a student and display
		Student me = new Student("Arthur");
		System.out.println("Student: " + me); 
		

		// Here is your **dream** schedule
		ClassRoom[] schedule = new ClassRoom[4];
		schedule[0] = new ClassRoom("MCV4U1", "Mr. Fernandes");
		schedule[1] = new ClassRoom("SPH4U1", "Mr. Jabir");
		schedule[2] = new ClassRoom("ICS4U1", "Mr. Reid");
		schedule[3] = new ClassRoom("HRE4M1", "Mr. Lombardi");
		
		
		// Follow your schedule
		for (int i=0; i<4; i++)
		{
			// Go to class
			System.out.println("Period " + (i + 1));
			if (schedule[i].enter(me) == true)
			{
				System.out.println(me + " was allowed into class.");
			}  
			else
			{
				System.out.println(me + " was sent to the office.");        
			}

			// End of period 2
			if (i == 1)
			{
				// Don't forget lunch
				me.doLunch();
				System.out.println(me + " had lunch.");
			}
		}
	} // main method
} // VirtualSchool class

