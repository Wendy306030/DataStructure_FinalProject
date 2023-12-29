
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException 
	{	
		//讀我們設的關鍵字
		File file = new File("input.txt");
		Scanner scanner = new Scanner(file);
		KeywordList kLst = new KeywordList();
		
		while(scanner.hasNext()){
		    String cmd = scanner.next();
		    switch(cmd){
	        	case "add":
	        	{
	        		String name = scanner.next();
	        		int weight = scanner.nextInt();
	        		Keyword k = new Keyword(name, weight);
	        		kLst.add(k);
	        	}
	        		break;
	        		
	        	default:
        	    	System.out.println("InvalidOperation2");       	    	      
		    }
		}
		scanner.close();
		
		//呼叫谷哥
		Scanner sc=new Scanner(System.in);
		String search=sc.next()+"評價";
		sc.close();
		
		try 
		{
			GoogleQuery gl=new GoogleQuery(search, kLst);
			gl.query();
//			System.out.println(new GoogleQuery("µf­X").query());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}