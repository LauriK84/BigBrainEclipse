package services;

import java.sql.*;
import javax.ws.rs.*;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.utils.*;

import conn.Connections;
import data.Point;

@Path("/brainservice")
public class PointService {


@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
@Path("/addpoint")
public Point addPoint(@FormParam("point") String point) {
	Point pt=new Point();
	pt.setPoint(point);
	String sql="insert into leaderboard(point) values(?)";
	
	Connection conn=null;
	try {
		if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			conn = Connections.getProductionConnection();
		}
		else {
			conn = Connections.getDevConnection();
		}
	} catch(Exception e){
		e.printStackTrace();
	}
	PreparedStatement ps;
	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, point);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return pt;
	
	}

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/addobjectpoint")
public Point objectFish(Point point) {
	String sql="insert into leaderdoard(point) values(?)";

	Connection conn=null;
	try {
		if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			conn = Connections.getProductionConnection();
		}
		else {
			conn = Connections.getDevConnection();
		}
	} catch(Exception e){
		e.printStackTrace();
	}
	PreparedStatement ps;
	try {
		ps = conn.prepareStatement(sql);
		
		ps.setInt(1, (int) point.getPoint());
		ps.execute();
	} catch(SQLException e) {
		e.printStackTrace();
	}
	point.setPoint((int) point.getPoint());
	
	return point;
}

//@DELETE
@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
//@Consumes(MediaType.APPLICATION_JSON)//Method receives object as a JSON string
@Path("/deletepoint/{p1}")
public Point delete(@PathParam("p1") int id) {
	String sql="delete from leaderboard where id=?";
	
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	PreparedStatement pstmt;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Point pt=new Point();
	pt.setId(100);
	pt.setPoint("point");
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	return pt;
}
@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAll")
public ArrayList<Point> getAll() {
	String sql="select * from leaderboard";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("point"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllMovie")
public ArrayList<Point> getAllMovie() {
	String sql="select * from movie";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllSport")
public ArrayList<Point> getAllSport() {
	String sql="select * from sport";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllAnimals")
public ArrayList<Point> getAllAnimals() {
	String sql="select * from animals";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllGeography")
public ArrayList<Point> getAllGeography() {
	String sql="select * from geography";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllHistory")
public ArrayList<Point> getAllHistory() {
	String sql="select * from history";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}

@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAllMusic")
public ArrayList<Point> getAllMusic() {
	String sql="select * from music";
	ResultSet RS=null;
	ArrayList<Point> list=new ArrayList<>();
	Connection conn=null;
	try {
	    if (SystemProperty.environment.value() ==SystemProperty.Environment.Value.Production) {  
	    	conn = Connections.getProductionConnection();
	    }
	    else {
	    	conn = Connections.getDevConnection();
	    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt;
	try {
		stmt = conn.createStatement();
		RS=stmt.executeQuery(sql);
		while (RS.next()) {
			Point pt=new Point();
			pt.setId(RS.getInt("id"));
			pt.setPoint(RS.getString("question"));
			pt.setPoint(RS.getString("correct"));
			pt.setPoint(RS.getString("false1"));
			pt.setPoint(RS.getString("false2"));
			pt.setPoint(RS.getString("false3"));
			list.add(pt);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	return list;
}



}
