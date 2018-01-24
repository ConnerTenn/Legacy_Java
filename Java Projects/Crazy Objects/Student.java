/**
 * The "Student" class for the CrazyObjects problem.
 * @author Mr. Reid
 * @date Oct 2013
 * @version 2.2
 */
public class Student
{
	// Attributes
	private String name;
	private Locker myLocker;
	private Jacket myJacket;
	private Book books[];
	
	/**
	* Constructor
	* @param String myName - name of the student
	*/
	public Student(String myName)
	{
		//init items
		this.name = myName;
		this.myLocker = new Locker(this);
		this.myJacket = null;//new Jacket(this);
		
		//put jacket in locker
		//this.myLocker.putJacket(this.myJacket); this.myJacket = null;
		this.myLocker.putJacket(new Jacket(this));
		
		//pick up 2 books from the locker
		this.books = new Book[2];
		this.books[0] = this.myLocker.getABook("MCV4U1");
		this.books[1] = this.myLocker.getABook("SPH4U1");
	}

	/** 
	* Display student information
	*/
	public String toString()
	{
		return this.name;
	}

	/**
	* This method gets called when the student is sent to the office
	*/
	public void sentToOffice(String reason)
	{
		System.out.println("because ...."+reason);
	}

	/**
	* This method to be called at Lunch time
	*/
	public void doLunch() 
	{
		//put books in locker
		this.myLocker.putABook(this.books[0]);
		this.myLocker.putABook(this.books[1]);
		
		//retrieve books for next classes
		this.books[0] = this.myLocker.getABook("ICS4U1");
		this.books[1] = this.myLocker.getABook("HRE4M1");
	}

	/**
	* Return the students locker
	*/
	public Locker getLocker()
	{
		return this.myLocker;
	}

	/**
	* Returns the students Jacket
	*/
	public Jacket getJacket()
	{
		return this.myJacket;
	}

	/**
	* Returns the students books
	*/
	public Book[] getBooks()
	{
		return this.books;
	}
} // Student class
