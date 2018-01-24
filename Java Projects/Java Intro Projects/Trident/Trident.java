
import java.util.Scanner;

/**
* Main Program Class
*
*/
public class Trident 
{	
	/**
	* Main function
	*/
	public static void main(String[] args) 
	{
		//MainClass program = new MainClass();
		Scanner scanner = new Scanner(System.in);
		String out = "";
		
		int tineLength = 0, spacing = 0, handleLength = 0; 
		System.out.print("Tine Length:"); tineLength = scanner.nextInt();
		System.out.print("Spacing:"); spacing = scanner.nextInt();
		System.out.print("Handle Length:"); handleLength = scanner.nextInt();
		
		int width = spacing * 2 + 3;
		
		//display tines
		for (int i = 0; i < tineLength; i++)
		{
			String buffer = new String();
			//fill a buffer with spaces to create the space between the tines
			for (int j = 0; j < spacing; j++) { buffer += " "; }
			//add the row to the output var
			out = out + "#" + buffer + "#" + buffer + "#\n";
		}
		
		//add the horizontal line
		for (int i = 0; i < width; i++) { out += "#"; }
		out+="\n";
		
		//display the handle
		for (int i = 0; i < handleLength; i++)
		{
			//add a buffer to align the handle to the center
			for (int j = 0; j < width/2; j++) { out += " "; }
			out+= "#";
			//for (int j = 0; j < width/2; j++) { out += " "; }
			out+="\n";
		}
		
		//display the output
		System.out.print(out);
		scanner.close();
	}
}
