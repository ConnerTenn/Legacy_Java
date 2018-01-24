package SlotMachines;

public class Player
{
	private int coins = 0;
	private int currentMachine = 0;
	
	public Player(int coins)
	{
		this.coins = coins;
	}
	
	public boolean play(Machine[] machines)
	{
		this.coins--;
		this.coins += machines[this.currentMachine].play();
		
		this.currentMachine++;
		if (this.currentMachine >= machines.length) { this.currentMachine = 0; }
		
		if (this.coins <= 0) { return false; }
		else { return true; }
	}
}