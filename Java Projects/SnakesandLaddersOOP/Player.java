package SnakesandLaddersOOP;
import java.util.Random;

/**
 * Player class
 * @author Mr. Reid
 *
 */
public class Player 
{
	// Attribute
	private String name;
	private int currentPos;

	// Constructor
	public Player(String n) 
	{
		this.name = n;
		this.currentPos = 1;
	}

	public Player() 
	{
		this("Ray");
	}
	
	private void Jump(Obstacle[] board)
	{
		boolean doJump = true;
		for (int i = 0; i < board.length && doJump; i++)
		{
			if (this.currentPos == board[i].Start)
			{
				this.currentPos = board[i].End;
				doJump = false;
				if (board[i].Start < board[i].End)
				{
					System.out.println(this.name + " climbed up a ladder from " + board[i].Start + " to " + board[i].End + ".");
				}
				else if (board[i].Start > board[i].End)
				{
					System.out.println(this.name + " slid down a snake from " + board[i].Start + " to " + board[i].End + ".");
				}
			}
		}
	}

	// Behaviours
	public boolean move(Obstacle[] board) 
	{
		boolean result = false;
		Random rand = new Random();
		currentPos += rand.nextInt(6) + 1;
		
		Jump(board);
		
		if (currentPos >= 100)
		{
			result = true;
		}
		return result;
	}

	public String toString()
	{
		return "Player: "+this.name+" Pos:"+this.currentPos;
	}
	
	public String getName()
	{
		return this.name;
	}
}
