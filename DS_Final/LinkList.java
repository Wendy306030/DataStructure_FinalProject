import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
			sum(li);
		}
		linkList.sort(Comparator.comparingInt(Link::getScore).reversed());
		
		System.out.println("Sorted by Score:");
		for(Link li : linkList) {
			System.out.println(li.getTitle()+"\n"+li.getUrl()+"\n"+"   ～   "+li.getScore());
		}
	}

	//計算link裡的分數
	public void sum(Link li) throws IOException {
		
		WordCounter wordCount =new WordCounter(li.getUrl());
		int totalScore = 0;
		
		for(int i=0;i<kLst.lst.size();i++) {
			int wordNum=wordCount.countKeyword(kLst.lst.get(i).getName());
			li.setScore(wordNum*kLst.lst.get(i).getWeight());
		}
		li.setScore(totalScore);
	
		
	}

}
