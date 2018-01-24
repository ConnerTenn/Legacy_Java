/**
 * The "Jacket" class for the CrazyObjects problem.
 * @author Mr. Reid
 * @date Oct 2013
 * @version 2.2
 */
public class Jacket
{
	private Student owner;
	
	/**
	* Default constructor
	*/
	public Jacket(Student me)
	{
		//init values
		this.owner = me;
	}
	
	/**
	* Returns the owner
	*/
	public Student getOwner()
	{
		return this.owner;
	}
	
	/** 
	* Display jacket information
	*/
	public String toString()
	{
		return owner + "'s Jacket";
	}
} // Jacket class
