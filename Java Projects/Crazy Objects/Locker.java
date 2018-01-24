/**
 * The "Locker" class for the CrazyObjects problem.
 * @author Mr. Reid
 * @date Oct 2013
 * @version 2.2
 */
public class Locker
{
	private Student owner;
	private Jacket studentJacket;
	private int numBooks;
	private Book books[];

	/**
	* Default constructor
	*/
	public Locker(Student me)
	{
		//init values
		this.owner = me;
		
		//the locker has 4 slots for books
		this.books = new Book[4];
		this.books[0] = new Book("MCV4U1", "Mr. Fernandes");
		this.books[1] = new Book("SPH4U1", "Mr. Jabir");
		this.books[2] = new Book("ICS4U1", "Mr. Reid");
		this.books[3] = new Book("HRE4M1", "Mr. Lombardi");
	}
	
	/**
	* Returns the owner
	*/
	public Student getOwner()
	{
		return this.owner;
	}
	
	/**
	* gets a book from the locker
	*/
	public Book getABook(String course)
	{
		Book book = null;
		//find book in locker
		for (int i = 0; i < this.books.length; i++)
		{
			//if the book exists   then   if book is the correct book
			if (this.books[i] != null && this.books[i].getCourse() == course)
			{
				//transfer book
				book = this.books[i];
				this.books[i] = null;
				i = this.books.length;
			}
		}
		return book;
	}
	
	/**
	* puts a book in the locker
	*/
	public void putABook(Book book)
	{
		//find empty slot and place book
		for (int i = 0; i < this.books.length; i++)
		{
			//if slot is empty
			if (this.books[i] == null)
			{
				//transfer book
				this.books[i] = book;
				i = this.books.length;
			}
		}
	}
	
	/**
	* Returns the jacket
	*/
	public Jacket getJacket()
	{
		return this.studentJacket;
	}
	
	/**
	* puts the jacket in the locker
	*/
	public void putJacket(Jacket thisJacket)
	{
		this.studentJacket = thisJacket;
	}
	
	/** 
	* Display student information
	*/
	public String toString()
	{
		return "Locker Temp";
	}
} // Locker class
