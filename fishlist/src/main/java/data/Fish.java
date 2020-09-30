package data;

public class Fish {
	private int id;
	private String breed;
	private float weight;
	private int lenght;
	private String city;
	private String waters;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public void setWeight(String s) {
		try {
			this.weight = Float.parseFloat(s);
		}
		catch(NumberFormatException e) {
			weight=-1;
		}
	}
	public float getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	public void setLenght(String l) {
		try {
			this.lenght = (int) Float.parseFloat(l);
		}
		catch(NumberFormatException e) {
			weight=-1;
		}
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getWaters() {
		return waters;
	}
	public void setWaters(String waters) {
		this.waters = waters;
	}
	
	public String toString() {
		return id+" "+breed+" "+weight+" "+lenght+" "+city+" "+waters+"\n";
	}
	
}
