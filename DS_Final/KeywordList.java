import java.util.LinkedList;

public class KeywordList {
	public LinkedList<Keyword> lst;

	public KeywordList()
	{
		this.lst = new LinkedList<Keyword>();
	}

	public void add(Keyword keyword)
	{
		lst.add(keyword);
	}
}
