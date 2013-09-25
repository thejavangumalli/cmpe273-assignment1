package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Authors;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Reviews;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	private static HashMap <Integer,Book> hash = new HashMap <Integer,Book>();
	static int i=1;

    public BookResource() {
	
    }
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
   
    
    public BookDto getBookByIsbn(@PathParam("isbn") int isbn) {
	Book book =hash.get(isbn);	
	
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(),"DELETE"));	
	bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn()+"/reviews", "POST"));
	if(book.reviews.size()>0)
	bookResponse.addLink(new LinkDto("view-all-review","/books/" + book.getIsbn()+"/reviews", "GET"));
	
	return bookResponse;
    }
    
    @POST
    @Timed(name = "create-book")
    public Response CreateBook(@Valid Book book){
    	book.setIsbn(i);
    	hash.put(new Integer(i),book);
    	i++;
      	LinksDto bookResponse = new LinksDto();
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
    	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book","/books/" +book.getIsbn(),"DELETE"));
    	bookResponse.addLink(new LinkDto("create-review","/books/" +book.getIsbn()+ "/reviews", "POST"));
    	return Response.status(201).entity(bookResponse).build();
}
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response UpdateBook(@PathParam("isbn") int isbn, @QueryParam("status") String status){
    	Book book=hash.get(isbn);
    	book.setStatus(status);
    	LinksDto bookResponse = new LinksDto();
    	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
    	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(),"DELETE"));	
    	bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn()+"/reviews", "POST"));
    	if(book.reviews.size()>0)
    	bookResponse.addLink(new LinkDto("view-all-review","/books/" + book.getIsbn()+"/reviews", "GET"));
   	  	return Response.status(200).entity(bookResponse).build();
  }
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response DeleteBook(@PathParam("isbn") int isbn){ 
    	Book book=hash.get(isbn);
    	hash.remove(isbn);
    	LinksDto bookResponse=new LinksDto();
    	bookResponse.addLink(new LinkDto("create-book", "/books/"+book.getIsbn(),"POST"));
    	return Response.status(200).entity(bookResponse).build();
}
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
   
    
    public Response createReview(@PathParam("isbn") int isbn,Reviews reviews) {
	Book book =hash.get(isbn);	
	@SuppressWarnings("unused")
	ArrayList<Reviews> review=book.addReviews(reviews);
	LinksDto bookResponse = new LinksDto();
	bookResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn()+"/reviews/"+reviews.getId(),"GET"));
	return Response.status(201).entity(bookResponse).build();
    }
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name="view-reviews")
    public Response viewReviewsById(@PathParam("isbn") int isbn,@PathParam("id") int id){
    	Book book =hash.get(isbn);	
    	Reviews reviews=new Reviews();
    	reviews=book.ReviewId(id);
    	ReviewsDto reviewResponse = new ReviewsDto(reviews);
    	reviewResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn()+"/reviews/"+reviews.getId(),"GET"));
    	return Response.status(200).entity(reviewResponse).build();	
    }
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name="view-reviews")
    public Response viewReviews(@PathParam("isbn") int isbn){
    	Book book =hash.get(isbn);	
    	ArrayList<Reviews> reviews=new ArrayList<Reviews>();
    	reviews=book.AllReviews();
    	@SuppressWarnings("unused")
		ReviewsDto reviewResponse = new ReviewsDto();
    	for(int i=0;i<reviews.size();i++)
    	{
    		reviewResponse=new ReviewsDto(reviews.get(i));
    		
    	}
    	return Response.status(200).entity(reviews).build();	
    }
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name="view-author")
    public Response viewAuthorsById(@PathParam("isbn") int isbn,@PathParam("id") int id){
    	Book book =hash.get(isbn);	
    	Authors authors=new Authors();
    	authors=book.AuthorsId(id);
    	AuthorsDto authorResponse=new AuthorsDto(authors);
    	authorResponse.addLink(new LinkDto("view-author", "/books/" + book.getIsbn()+"/authors/"+authors.getId(),"GET"));
    	return Response.status(200).entity(authorResponse).build();
    	
    }
    @GET
    @Path("/{isbn}/authors")
    @Timed(name="view-authors")
    public Response viewAllAuthors(@PathParam("isbn") int isbn){
    	Book book =hash.get(isbn);	
    	ArrayList<Authors> authors=new ArrayList<Authors>();
    	authors=book.AllAuthors();
    	@SuppressWarnings("unused")
		AuthorsDto authorResponse = new AuthorsDto();
    	for(int i=0;i<authors.size();i++)
    	{
    		authorResponse=new AuthorsDto(authors.get(i));
    		
    	}
    	return Response.status(200).entity(authors).build();	
    }
}