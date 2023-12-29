import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery 
{
	public String searchKeyword;
	public String url;
	public String content;
	public LinkList linkList;
	public KeywordList kLst;
	
	public GoogleQuery(String searchKeyword, KeywordList kLst)
	{
		this.searchKeyword = searchKeyword;
		this.kLst=kLst;
		linkList=new LinkList(kLst);
		try 
		{
			// This part has been specially handled for Chinese keyword processing. 
			// You can comment out the following two lines 
			// and use the line of code in the lower section. 
			// Also, consider why the results might be incorrect 
			// when entering Chinese keywords.
			String encodeKeyword=java.net.URLEncoder.encode(searchKeyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
			
			// this.url = "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private String fetchContent() throws IOException
	{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null)
		{
			retVal += line;
		}
		return retVal;
	}
	
	public void query() throws IOException {
	    if (content == null) {
	        content = fetchContent();
	    }

	    //using Jsoup analyze html string
	    Document doc = Jsoup.parse(content);

	    // select particular element(tag) which you want
	    Elements lis = doc.select("div.kCrYT");

	    for (Element li : lis) {
	        try {
	            Elements links = li.select("a");
	            if (links.size() > 0) {
	            	String citeUrl = extractActualUrl(links.get(0).attr("href"));
	                String title = links.get(0).select(".vvjwJb").text();

	                if (title.equals("")) {
	                    continue;
	                }

	                Link link = new Link(title, citeUrl);
	                linkList.add(link);
	            }
	        } catch (IndexOutOfBoundsException e) {
	            // e.printStackTrace();
	        }
	    }
	    
	    
	    linkList.printresult();	 
	}
	
	private String extractActualUrl(String url) {
	    // Extract the actual URL from the Google search result URL
	    int startIdx = url.indexOf("/url?q=");
	    if (startIdx != -1) {
	        int endIdx = url.indexOf("&", startIdx);
	        if (endIdx != -1) {
	            return url.substring(startIdx + 7, endIdx);
	        }
	    }
	    return url;
	}


}