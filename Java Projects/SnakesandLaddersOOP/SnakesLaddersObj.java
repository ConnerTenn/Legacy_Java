package SnakesandLaddersOOP;

/**
 * Snakes and Ladders - OOP
 * @author Mr. Reid
 * @course ICS4U
 */
public class SnakesLaddersObj 
{

	public static void main(String[] args) 
	{
		// Create the pieces
		Obstacle[] board = 
			{
				new Obstacle(10, 34),
				new Obstacle(25, 5),
				new Obstacle(32, 15),
				new Obstacle(60, 45),
				new Obstacle(92, 30)
			};
		
		// Create the players
		Player[] players =
			{
				new Player("Fred"),
				new Player("Rosa"),
				new Player()
			};
		
		// Play the game
		int turn = 0;
		boolean gameOver = false;
		while (!gameOver)
		{
			// Take a turn
			gameOver = players[turn].move(board);
			System.out.println(players[turn]);
			
			// Next turn
			if (!gameOver)
			{
				turn++;
				if (turn >= players.length)
				{
					turn = 0;
				}
			}
		}
		System.out.println(players[turn].getName() + " Wins!");
	}
}
