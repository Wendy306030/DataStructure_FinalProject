import java.io.IOException;
import java.util.ArrayList;

public class LinkList {
	public ArrayList<Link> linkList;
	public KeywordList kLst;
	
	
	public LinkList(KeywordList kLst) {
		linkList=new ArrayList<Link>();
		this.kLst=kLst;
	}
	
	public void add(Link link) {
		linkList.add(link);
	}
	
	public void printresult() throws IOException {
		
		for(Link li:linkList) {
			System.out.println(li.getTitle());
			System.out.println(li.getUrl());
			sum(li);
			System.out.println(li.score);
		}
		

	}
	
	public void sum(Link li) throws IOException {
		
		WordCounter wordCount =new WordCounter(li.getUrl());
		
		for(int i=0;i<kLst.lst.size();i++) {
			int wordNum=wordCount.countKeyword(kLst.lst.get(i).getName());
			li.setScore(wordNum*kLst.lst.get(i).getWeight());
		}
		
	}

}
