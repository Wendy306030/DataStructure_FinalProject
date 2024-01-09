import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class TreeList {
	public ArrayList<WebTree> treeList;
	public ArrayList<Keyword> kLst;
	
	public TreeList(ArrayList<Keyword> kLst) {
		treeList=new ArrayList<WebTree>();
		this.kLst=kLst;
	}
	
	public void add(WebTree webtree) {
		treeList.add(webtree);
	}
	
	public WebTree get(int i) {
		return treeList.get(i);
	}
	
	public int size() {
		return treeList.size();
	}
	
	public void sort() throws IOException {
		for(int i=0;i<treeList.size();i++) {
			treeList.get(i).setPostOrderScore(kLst);
		}
		treeList.sort(Comparator.comparingDouble(WebTree::rootScore).reversed());
	}
	
	public void printRst() throws IOException {
		for(int i=0;i<treeList.size();i++) {
			treeList.get(i).eularPrintTree();
		}
		
	}
	
	

}
