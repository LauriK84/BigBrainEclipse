package data;

public class Score {

	private int id;
	private int score;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setScore(String p) {
		try {
			this.score = (int) Float.parseFloat(p);
		}
		catch(NumberFormatException e) {
			score=-1;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return ""+score+"\n";
	}		
}
