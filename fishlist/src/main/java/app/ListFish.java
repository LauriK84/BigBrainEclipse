package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.utils.SystemProperty;

import conn.Connections;
import data.Fish;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class ListFish extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	    response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    
	    String breed=request.getParameter("breed");
	  
	    
	    
	    PrintWriter out=response.getWriter();
	    ArrayList<Fish> fishlist=new ArrayList<>();
		util.HTML.printStart(out);
	    Connection conn=null;
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	out.println("Production version");
	    	try {
				conn=Connections.getProductionConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else {    
	    	out.println("Development version");
			try {
				conn=Connections.getDevConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    try {
	    	if (conn!=null) {
				Statement stmt=conn.createStatement();
				
				ResultSet RS=stmt.executeQuery("select * from fish");
				
				while (RS.next()) {
					Fish f=new Fish();
					f.setId(RS.getInt("id"));
					f.setBreed(RS.getString("breed"));
					fishlist.add(f);
				}
				conn.close();
				util.HTML.printTable(out, fishlist);
	    	}
	    	else {
	    		out.println("No connection to database!");
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		util.HTML.printEnd(out);
  }
}