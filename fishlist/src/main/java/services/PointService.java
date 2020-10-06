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
import data.*;

@Path("/brainservice")
public class PointService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	@Path("/addpoint")
	public Score addPoint(@FormParam("point") String point) {
		Score pt = new Score();
		pt.setScore(point);
		String sql = "insert into leaderboard(point) values(?)";

		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
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
	public Score objectFish(Score score) {
		String sql = "insert into leaderboard(username, point) values(?,?)";

		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, score.getName());
			ps.setInt(2, (int) score.getScore());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		score.setScore(score.getName());
		score.setScore((int) score.getScore());

		return score;
	}

	//@DELETE
	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	//@Consumes(MediaType.APPLICATION_JSON)//Method receives object as a JSON string
	@Path("/deletepoint/{p1}")
	public Score delete(@PathParam("p1") int id) {
		String sql = "delete from leaderboard where id=?";

		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
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
		Score s = new Score();
		s.setId(100);
		s.setScore("point");
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return s;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getall")
	public ArrayList<Score> getAll() {
		String sql = "select * from leaderboard";
		ResultSet RS = null;
		ArrayList<Score> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				Statement stmt = conn.createStatement();
				RS = stmt.executeQuery(sql);
				while (RS.next()) {
					Score pt = new Score();
					pt.setId(RS.getInt("id"));
					pt.setName(RS.getString("name"));
					pt.setScore(RS.getInt("score"));
					list.add(pt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/get5")
	public ArrayList<Score> get5() {
		String sql = "select * from leaderboard ORDER BY score DESC LIMIT 5";
		ResultSet RS = null;
		ArrayList<Score> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				Statement stmt = conn.createStatement();
				RS = stmt.executeQuery(sql);
				while (RS.next()) {
					Score pt = new Score();
					pt.setId(RS.getInt("id"));
					pt.setName(RS.getString("name"));
					pt.setScore(RS.getInt("score"));
					list.add(pt);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllMovie")
	public ArrayList<Question> getAllMovie() {
		String sql = "select * from movie";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllSport")
	public ArrayList<Question> getAllSport() {
		String sql = "select * from sport";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllAnimals")
	public ArrayList<Question> getAllAnimals() {
		String sql = "select * from animals";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
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
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllGeography")
	public ArrayList<Question> getAllGeography() {
		String sql = "select * from geography";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
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
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllHistory")
	public ArrayList<Question> getAllHistory() {
		String sql = "select * from history";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return list;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON) // Method returns object as a JSON string
	@Path("/getAllMusic")
	public ArrayList<Question> getAllMusic() {
		String sql = "select * from music";
		ResultSet RS = null;
		ArrayList<Question> list = new ArrayList<>();
		Connection conn = null;
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				conn = Connections.getProductionConnection();
			} else {
				conn = Connections.getDevConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		try {
			stmt = conn.createStatement();
			RS = stmt.executeQuery(sql);
			while (RS.next()) {
				Question q = new Question();
				q.setId(RS.getInt("id"));
				q.setQuestion(RS.getString("question"));
				q.setCorrect(RS.getString("correct"));
				q.setFalse1(RS.getString("false1"));
				q.setFalse2(RS.getString("false2"));
				q.setFalse3(RS.getString("false3"));
				list.add(q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn != null) {
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
