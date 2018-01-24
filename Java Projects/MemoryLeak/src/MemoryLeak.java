import java.io.IOException;

/**
 * Created by Conner on 22-Dec-2016.
 */
public class MemoryLeak
{
	public static class Leak
	{
		private double[] memory;
		public Leak ptr;
		Leak()
		{
			memory = new double[100];
		}
	}
	
	public static void main(String[] args) throws java.lang.InterruptedException, java.io.IOException
	{
		long count = 1000000000;
		
		for (long i = 0; i < count; i+=0)
		{
			Leak a = new Leak();
			Leak b = new Leak();
			
			a.ptr = b;
			b.ptr = a;
		}
		//Thread.sleep(1000);
		System.out.print("Press Enter To Continue:");
		System.in.read();
		System.out.println("Done!");
	}
}
