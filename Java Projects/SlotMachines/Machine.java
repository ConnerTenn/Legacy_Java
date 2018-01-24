package SlotMachines;

public class Machine
{
	private int pay = 0;
	private int count = 0;
	private int maxCount = 0;
	
	public Machine(int pay, int maxCount, int count)
	{
		this.maxCount = maxCount;
		this.pay = pay;
		this.count = count;
	}
	
	public int play()
	{
		int pay = 0;
		this.count++;
		if (this.count >= this.maxCount)
		{
			this.count = 0;
			pay = this.pay;
		}
		return pay;
	}
}