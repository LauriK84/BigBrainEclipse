package app;

import java.io.*;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;


/**
 * Servlet implementation class FishNoSql
 */
@WebServlet(
		name = "FishNoSQL",
		urlPatterns ={"/fishnosql"})

public class FishNoSql extends HttpServlet {
	
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		
		
	    response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out=response.getWriter();  

	    RequestDispatcher rd=request.getRequestDispatcher("/starthtml.html");
	    rd.include(request, response);
	    
	    out.print("<h2>Hello DataStore Example Application!</h2>");
	    
	    String removeId=request.getParameter("removeId");
	    String edit=request.getParameter("editID");
	    
	    String breed=request.getParameter("breed");
	    String strWeight=request.getParameter("weight");

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    String sortOrder=request.getParameter("sortOrder");
	    if (removeId!=null) {
	    	Key key=KeyFactory.stringToKey(removeId);
	    	datastore.delete(key);
	    }
	    if (edit!=null) {
	    	Key key1=KeyFactory.stringToKey(edit);
	    	Entity entity=new Entity(key1);
	    	out.println("<input type='text' name='editbreed' value='" + breed + "'>");
			out.println("<input type='text' name='editweight' value='" + strWeight + "'>");
	    	
	    	
	    	entity.setProperty("editbreed", breed);
		    entity.setProperty("editweight", strWeight);
		    datastore.put(entity);
	    	
	    }
	    
	    /*See which datatypes are allowed in Entities
	     * https://cloud.google.com/appengine/docs/standard/java/datastore/entities */
	    else if (breed!=null && strWeight!=null){
		    Entity entity=new Entity("fish");
		    entity.setProperty("breed", breed);
		    entity.setProperty("weight", strWeight);
		    datastore.put(entity);
	    }
	    printFish(out, datastore, sortOrder);
	    
	    rd=request.getRequestDispatcher("endhtml.html");
	    rd.include(request, response);
	  }
	  
		private void printFish(PrintWriter out, DatastoreService datastore, String sortOrder) {
			Query query = new Query("fish");
			addSortingOrder(query, sortOrder);
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> results = pq.asList(FetchOptions.Builder.withDefaults());

			out.println("<table>");
		    out.println("<tr><td>  <td>"+
		    		"<a href='/fishnosql?sortOrder=-breed'>A-a</a> | "+
		    		"<a href='/fishnosql?sortOrder=breed'>a-A</a>"+
		    		"<td>"+
		    		"<a href='/fishnosql?sortOrder=-weight'>G-g</a> | "+
		    		"<a href='/fishnosql?sortOrder=weight'>g-G</a>"+
		    		"<td> ");
			for (Entity entity : results) {
				String strFishID = KeyFactory.keyToString(entity.getKey());
				String breed = ""+entity.getProperty("breed");
				String weight = ""+entity.getProperty("weight");
			    String output = "<tr><td>"+strFishID+"<td>"+breed+ "<td>"+weight+
			    		"<td><a href='/fishnosql?removeId="+strFishID+"'>Remove</a>" + " or " +
			    		"<td><a href='/fishnosql?editID="+strFishID+"'>Edit</a>";
				out.println(output);
			}		
			out.println("</table>");
			out.println("<a href='/index.html'>Back to form</a>");
		}
	  
	  private void addSortingOrder(Query query, String sortOrder) {
			// TODO Auto-generated method stub
			if (sortOrder==null) {
				return;
			}
			SortDirection direction=SortDirection.ASCENDING;
			if (sortOrder.startsWith("-")) {
				direction=SortDirection.DESCENDING;
				sortOrder=sortOrder.substring(1);
			}
			query.addSort(sortOrder, direction);
		}
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
