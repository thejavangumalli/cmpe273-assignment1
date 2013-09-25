package edu.sjsu.cmpe.library.domain;

public class Authors {
	static int id=1;
	private String name;
	public int getId(){
		return id;
			}
	public void setId(){
		this.id=id++;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}

}
