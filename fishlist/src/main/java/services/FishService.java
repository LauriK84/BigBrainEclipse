package services;

import java.sql.*;
import javax.ws.rs.*;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.utils.*;

import conn.Connections;
import data.Fish;
@Path("/fishservice")
public class FishService {

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/x-www-form-urlencoded")
@Path("/addpoint")
public Fish addFish(@FormParam("breed") String breed) {
	Fish fish=new Fish();
	fish.setBreed(breed);
	String sql="insert into point(point) values(?)";
	
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
		ps.setString(1, breed);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return fish;
	
	}

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/addobjectfish")
public Fish objectFish(Fish fish) {
	String sql="insert into fish(breed) values(?)";

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
		
		ps.setString(1, fish.getBreed());
		ps.execute();
	} catch(SQLException e) {
		e.printStackTrace();
	}
	fish.setBreed(fish.getBreed());
	
	return fish;
}

//@DELETE
@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
//@Consumes(MediaType.APPLICATION_JSON)//Method receives object as a JSON string
@Path("/deletefish/{p1}")
public Fish deleteFish(@PathParam("p1") int id) {
	String sql="delete from fish where id=?";
	
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
	Fish f=new Fish();
	f.setId(100);
	f.setBreed("breed");
	if (conn!=null) {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	return f;
}
@GET
@Produces(MediaType.APPLICATION_JSON)//Method returns object as a JSON string
@Path("/getAll")
public ArrayList<Fish> getAllFish() {
	String sql="select * from fish";
	ResultSet RS=null;
	ArrayList<Fish> list=new ArrayList<>();
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
			Fish f=new Fish();
			f.setId(RS.getInt("id"));
			f.setBreed(RS.getString("breed"));
			list.add(f);
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
	

