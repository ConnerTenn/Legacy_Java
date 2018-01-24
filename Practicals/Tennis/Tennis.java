package Practical1;

//import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

public class Tennis
{
	static Scanner scanner;
	static PrintWriter printer;
	
	public static void main(String[] args) throws IOException
	{
		//scanner = new Scanner(new FileReader("Practical1/In.txt"));
		scanner = new Scanner(new FileReader("SrcPractical/Practical1/tennisData2.txt"));
		//scanner = new Scanner(System.in);
		//printer = new PrintWriter(new PrintWriter("Practical1/Out.txt"));
		printer = new PrintWriter(System.out);
		
		Player[] playerArray = new Player[2];
		playerArray[0] = new Player(scanner.next());
		playerArray[1] = new Player(scanner.next());
		
		boolean play = true;
		while(play)
		{
			String in = scanner.next();
			if (in.equals("display"))
			{
				play = false;
			}
			else
			{
				/*for (int i = 0; i < 2; i++)
				{
					if (in.equals(playerArray[i].name))
					{
						playerArray[i].score();
						playerArray[1-i].notScore();
					}
				}
				//check win game
				for (int i = 0; i < 2; i++)
				{
					if (playerArray[i].wonGame)
					{
						playerArray[i].wonGame = false;
						playerArray[1-i].resetPoints();
					}
				}*/
				
				for (int i = 0; i < 2; i++)
				{
					if (in.equals(playerArray[i].name))
					{
						playerArray[i].points++;//player scored
						
						//check if score 4 times
						/*if (playerArray[i].points >= 3)
						{
							if (playerArray[i].points - playerArray[1-i].points >= 2)
							{//win
								playerArray[i].resetPoints();
								playerArray[1-i].resetPoints();
								playerArray[i].winGame();
							}
							else
							{//do Adv calculation
								if (playerArray[1-i].points >=4 )
								{
									playerArray[i].points--;
									playerArray[1-i].points--;
								}
							}
						}*/
						playerArray[i].score(playerArray[1-i]);
					}
				}
			}
		}
		
		//for (int i = 0; i < 2; i++)
		//{
		//	printer.println(playerArray[i]);
		//}
		if (playerArray[0].points == 0 && playerArray[1].points == 0)
		{
			printer.println(playerArray[0].name + " " + playerArray[0].setsWon + " " + playerArray[0].gamesWon + " 0");
			printer.println(playerArray[1].name + " " + playerArray[1].setsWon + " " + playerArray[1].gamesWon + " 0");
		}
		else
		{
			printer.println(playerArray[0]);
			printer.println(playerArray[1]);
		}

		scanner.close();
		printer.flush();
		printer.close();
	}

}
