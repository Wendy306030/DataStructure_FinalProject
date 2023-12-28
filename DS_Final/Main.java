
import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		String search=sc.next()+"評價";
		
		try 
		{
			GoogleQuery gl=new GoogleQuery(search);
			gl.query();
//			System.out.println(new GoogleQuery("µf­X").query());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}