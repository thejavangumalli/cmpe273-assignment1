package edu.sjsu.cmpe.library.domain;

public class Authors {
	int id;
	static int i=1;
	private String name;
	public Authors(){
				
	}
	
	public int getId(){
		return i++;
			}
	public void setId(int i){
		this.id=i;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}

}
