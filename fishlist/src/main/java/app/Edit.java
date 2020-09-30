package app;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
/**
 * Servlet implementation class Edit
 */
@WebServlet("/Edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 response.setContentType("text/html");
		    response.setCharacterEncoding("UTF-8");
		    PrintWriter out=response.getWriter();  

		   
		
		String fishBreed = null;
		String fishWeight = null;
	    String editId=request.getParameter("editId");
	    String modBreed=request.getParameter("modifybreed");
	    String modWeight=request.getParameter("modifyweight");
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key fishKey = KeyFactory.stringToKey(editId);
		
		try {
			Entity fish = datastore.get(fishKey);
			fishBreed = (String) fish.getProperty("breed");
			fishWeight = (String) fish.getProperty("weight");
			
			// Update
			if (modBreed != null && modWeight != null) {
				fish.setProperty("breed", modBreed);
				fish.setProperty("weight", modWeight);
				
				datastore.put(fish);
				
				fishBreed = modBreed;
				fishWeight = modWeight;
			}
		} catch (EntityNotFoundException e) {

		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    RequestDispatcher rd=request.getRequestDispatcher("/starthtml.html");
	    rd.include(request, response);		
		
	    out.println("<h2> Modify Fish breed & weight!</h2>");
		out.println("<form action='/modify' method='get'>");
		out.println("<input type='text' name='modifybreed' value='" + fishBreed + "'>");
		out.println("<input type='text' name='modifyweight' value='" + fishWeight + "'>");
		out.println("<input type='submit' value='Update'>");
		out.println("</form>");
		out.println("<a href='/fishnosql'>Back to Fish list</a>");
   
	   // Testi setti: ?modifyId="+modifyId+"
	    rd=request.getRequestDispatcher("endhtml.html");
	    rd.include(request, response);
	    
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
