package Tennis;

public class Player
{
	public String name;
	
	public int setsWon = 0;
	
	//public boolean wonGame = false;
	public int gamesWon = 0;
	
	public int points = 0;
	
	public Player(String name)
	{
		this.name = name;
	}
	
	//player scores
	/*public boolean score()
	{
		boolean win = false;
		if (this.points < 3)
		{
			this.points += 1;
		}
		else
		{
			if (this.points == 3) { this.points += 1; }
			else
			{
				//won game
				win = true;
				resetPoints();
				winGame();
			}
		}
		return win;
	}
	
	//player did not score
	public void notScore()
	{
		if (this.points > 3)
		{
			
		}
	}*/
	
	public void score(Player oponent)
	{
		this.points++;
		
		//check if score 4 times
		if (this.points >= 4)
		{
			if (this.points - oponent.points >= 2)//check difference
			{//win
				resetPoints();
				oponent.resetPoints();
				winGame(oponent);
			}
			else
			{//do Adv calculation
				if (oponent.points >=4 )
				{
					this.points--;
					oponent.points--;
				}
			}
		}
	}
	
	//a game was won; reset the points
	public void resetPoints()
	{
		this.points = 0;
	}
	
	//record a win game
	public void winGame(Player oponent)
	{
		this.gamesWon++;
		//this.wonGame = true;
		
		if (this.gamesWon >= 6)
		{
			if (this.gamesWon - oponent.gamesWon >= 2)//check difference
			{//win
				resetGames();
				oponent.resetGames();
				winSet();
			}
			/*else
			{//do Adv calculation
				if (oponent.gamesWon >=6 )
				{
					//this.gamesWon--;
					//oponent.gamesWon--;
				}
			}*/
		}
	}
	
	public void resetGames()
	{
		this.gamesWon = 0;
	}
	
	public void winSet()
	{
		this.setsWon++;
	}
	
	public String toString()
	{
		String points = "";
		if (this.points == 0) {points = "love";}
		else if (this.points == 1) {points = "15";}
		else if (this.points == 2) {points = "30";}
		else if (this.points == 3) {points = "40";}
		else if (this.points == 4) {points = "Adv";}
		return this.name + " " + this.setsWon + " " + this.gamesWon + " " + points;
	}
}
