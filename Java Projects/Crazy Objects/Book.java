/**
 * The "Book" class for the CrazyObjects problem.
 * @author Mr. Reid
 * @date Oct 2013
 * @version 2.2
 */
public class Book
{
	private String title;
	private String course;
	
	/**
	* Default constructor
	*/
	public Book(String aCourse, String aTitle)
	{
		//init values
		this.title = aTitle;
		this.course = aCourse;
	}
	
	/**
	* Returns the title
	*/
	public String getTitle()
	{
		return this.course;
	}
	
	/**
	* Returns the course name
	*/
	public String getCourse()
	{
		return this.course;
	}
	
	/** 
	* Display Book information
	*/
	public String toString()
	{
		return title + " " + course;
	}
} // Book class
