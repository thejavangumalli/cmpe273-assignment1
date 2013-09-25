package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder(alphabetic=false)
public class Book {
	public Book(){
		
	}
//	 @JsonProperty("Isbn")
    private int isbn;
    @JsonProperty("num-pages")
    private int pages;
    @NotEmpty(message= "Title is required")
    private String title;
    @NotEmpty(message= "Date is required")
   @JsonProperty("publication-date")
    private String date;
    private String language;
    private String status="available";
    private ArrayList<Authors> authors=new ArrayList<Authors>();
    public ArrayList<Reviews> reviews=new ArrayList<Reviews>();
    public static int rc=1;
    /**
     * @return the isbn
     */
   
    public int getIsbn() {
	return isbn;
    }

    /**
     */
    public void setIsbn(int isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    public String getDate(){
    	return date;
        }
    public void setDate(String date){
    	this.date=date;
        }
    public String getLanguage(){
    	return language;
        }
    public void setLanguage(String language){
    	this.language=language;
        }
    public int getPages(){
    	return pages;
        }
    public void setPages(int pages){
    	this.pages=pages;
        }
    public String getStatus(){
    	return status;
        }
    public void setStatus(String status){
    	this.status=status;
    }
    public ArrayList<Authors> getAuthors() {
    		return authors;
    	}
    public void setAuthors(ArrayList<Authors> authors) {
    		this.authors = authors;
    	}
    public ArrayList<Reviews> getReviews() {
		return reviews;
	}
    public void setReviews(ArrayList<Reviews> reviews) {
	  this.reviews=reviews;
      }

    public ArrayList<Reviews> addReviews(Reviews reviews2) {
	
		reviews2.setId(rc);
		if(reviews.size()==0)
		{
			reviews=new ArrayList<Reviews>();	
		}
		reviews.add(reviews2);
		rc=rc+1;
		return reviews;
	}

    public Reviews ReviewId(int id) {
	Reviews review=new Reviews();
	for(int i=1;i<=reviews.size();i++){
		if(i==id){
			review=reviews.get(id-1);
		}
		
	}
	return review;
}

    public ArrayList<Reviews> AllReviews() {
	ArrayList<Reviews> review=new ArrayList<Reviews>();
	for(int i=1;i<=reviews.size();i++){
		review.add(reviews.get(i-1));
	}
return review;
}

    public Authors AuthorsId(int id) {
	Authors author=new Authors();
	for(int i=1;i<=authors.size();i++){
		if(i==id){
			author=authors.get(id-1);
		}
		
	}
	return author;
}

    public ArrayList<Authors> AllAuthors() {
	ArrayList<Authors> author=new ArrayList<Authors>();
	for(int i=1;i<=authors.size();i++){
		author.add(authors.get(i-1));
	}
return author;
}
}