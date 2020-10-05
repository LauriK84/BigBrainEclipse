package data;

public class Question {

	private int id;
	private String question;
	private String correct;
	private String false1;
	private String false2;
	private String false3;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrect() {
		return correct;
	}
	public void setCorrect(String correct) {
		this.correct = correct;
	}
	public String getFalse1() {
		return false1;
	}
	public void setFalse1(String false1) {
		this.false1 =false1;
	}
	public String getFalse2() {
		return false2;
	}
	public void setFalse2(String false2) {
		this.false2 =false2;
	}
	public String getFalse3() {
		return false3;
	}
	public void setFalse3(String false3) {
		this.false3 =false3;
	}
}
