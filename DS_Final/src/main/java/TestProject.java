

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestProject
 */
@WebServlet("/TestProject")
public class TestProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestProject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if(request.getParameter("keyword")== null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		
		//讀我們設的關鍵字
		File file = new File("C:\\Users\\user\\eclipse-workspace\\HW11_111306028_1\\src\\main\\webapp\\input.txt");
		Scanner scanner = new Scanner(file);
		ArrayList<Keyword> kLst = new ArrayList<Keyword>();
				
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
				
		GoogleQuery google = new GoogleQuery(request.getParameter("keyword")+"電影評價",kLst);
		google.query();
		TreeList treeList=google.treeLst;
		
		String[][] s = new String[treeList.size()][2];
		request.setAttribute("query", s);
		int num = 0;
		for(int i=0;i<treeList.size();i++) {
		    String key = treeList.get(i).root.webPage.name;
		    String value = "https:/"+treeList.get(i).root.webPage.url;
		    s[num][0] = key;
		    s[num][1] = value;
		    num++;
		}
		request.getRequestDispatcher("googleitem.jsp")
		 .forward(request, response); 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
