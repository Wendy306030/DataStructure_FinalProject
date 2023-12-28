import java.io.IOException;
import java.util.ArrayList;

public class LinkList {
	public ArrayList<Link> linkList;
	
	
	public LinkList() {
		linkList=new ArrayList<Link>();
	}
	
	public void add(Link link) {
		linkList.add(link);
	}
	
	public void printresult() throws IOException {
		
		for(Link li:linkList) {
			System.out.println(li.getTitle());
			System.out.println(li.getUrl());
			sum(li);
		}

	}
	
	public void sum(Link li) throws IOException {
		
		WordCounter wordCount =new WordCounter(li.getUrl());
		int test=wordCount.countKeyword("遊戲");
		System.out.println(test);
		
	}

}
