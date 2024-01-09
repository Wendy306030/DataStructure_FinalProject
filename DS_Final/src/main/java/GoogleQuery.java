import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
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
	public TreeList treeLst;
	
	public GoogleQuery(String searchKeyword, ArrayList<Keyword> kLst)
	{
		this.searchKeyword = searchKeyword;
		treeLst=new TreeList(kLst);
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
	
	public String fetchHTML(String url) throws IOException {
	    StringBuilder contentBuilder = new StringBuilder();

	    try {
	        URL u = new URL(url);
	        URLConnection conn = u.openConnection();
	        conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");

	        try (InputStream in = conn.getInputStream();
	             InputStreamReader inReader = new InputStreamReader(in, "utf-8");
	             BufferedReader bufReader = new BufferedReader(inReader)) {

	            String line;
	            while ((line = bufReader.readLine()) != null) {
	                contentBuilder.append(line);
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("File not found: " + url);
	    } catch (IOException e) {
	        System.err.println("Error fetching HTML from " + url + ": " + e.getMessage());
	    }

	    return contentBuilder.toString();
	}
	
	
	public void query() throws IOException {
	    if (content == null) {
	        content = fetchContent();
	    }

	    //using Jsoup analyze html string
	    Document doc = Jsoup.parse(content);

	    // select particular element(tag) which you want
	    Elements lis = doc.select("div.kCrYT");
	    
	    int count = 0;
	    for (Element li : lis) {
	        try {
	            Elements links = li.select("a");
	            if (links.size() > 0) {
	            	String citeUrl = extractActualUrl(links.get(0).attr("href"));
	                String title = links.get(0).select(".vvjwJb").text();

	                if (title.equals("")) {
	                    continue;
	                }

	                WebPage rootPage = new WebPage(citeUrl, title);		
	        		WebTree tree = new WebTree(rootPage);
	        		
	        		try {
	                    String htmlContent = fetchHTML(citeUrl);
	                    Document doc1 = Jsoup.parse(htmlContent);
	                    Elements lis1 = doc1.select("a");
	                    for (Element link : lis1) {
	                        String subpageUrl = link.attr("href");
	                        if (isValidSubpage(subpageUrl)) {
	                            tree.root.addChild(new WebNode(new WebPage(subpageUrl, "SubPage")));
	                            count++;
	                            if (count >= 5) {
	                                break; 
	                            }
	                        }
	                    }
	                     
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	        		
	        		
                    treeLst.add(tree);
	            }
	        } catch (IndexOutOfBoundsException e) {
	             e.printStackTrace();
	        }
	    }
	    treeLst.sort();
	    treeLst.printRst();
	    	 
	}
	
	private boolean isValidSubpage(String subpageUrl) {
		return subpageUrl.startsWith("https://");
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