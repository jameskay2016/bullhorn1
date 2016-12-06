//this file submits data to database
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import model.Bhpost;
import customTools.DbBullhorn;


@WebServlet("/PostServ")
public class PostServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PostServ() {
        super();
    }

    
    protected void doPost(HttpServletRequest request, 
    							HttpServletResponse response) 
    									throws ServletException, IOException {

    	//this is how you get today's date in Java
    	java.util.Date today = new java.util.Date();
    	//we need to convert today's date to a sql date since the database
    	//stores dates differently than java. For that we use a java.sql.Date object
    	
		//java.sql.Date postdate = new java.sql.Date(d.getTime());
		
    	java.sql.Date postdate = DbBullhorn.convertJavaDateToSqlDate(today);
		
		
		String posttext = request.getParameter("posttext");
		String nextURL = "/error.jsp";
		//need a reference to the session
		//get user out of session. If they don't exist then send them back to the login page.
		//kill the session while you're at it.
		HttpSession session = request.getSession();
		if (session.getAttribute("user")==null){
			//http://stackoverflow.com/questions/13638446/checking-servlet-session-attribute-value-in-jsp-file
			nextURL = "/login.jsp";
			session.invalidate();
			response.sendRedirect(request.getContextPath() + nextURL);
		    return;//return prevents an error
		}
		
		//get user information from session so we can connect to the db
		Bhuser bhuser = (Bhuser)session.getAttribute("user");

		//To insert a post into the database we need to get all the values from the form
		//we can then call the DbBullhorn.insert method. It takes three arguments, date, text and id
		//We don't have to create a new post here - we could simply pass the values as function parameters
		//But I want to let you make that modification. Get it working like this first.
		//The you should modify the DbBullhorn.insert method to take one parameter which is a BhPost object.
		Bhpost bhPost = new Bhpost();
		bhPost.setBhuserid(bhuser.getBhuserid());
		bhPost.setPostdate(postdate);
		bhPost.setPosttext(posttext);
		
		int result = DbBullhorn.insert(bhPost.getPostdate(),bhPost.getPosttext(),bhPost.getBhuserid());
		if (result > 0){
			//the nextURL needs to go to another servlet, this time the newsfeed servlet
			//this is because the newsfeed servlet contains the code to display all the posts
			//Recall that all the posts are actually in the session but the newsfeed servlet 
			//puts them there. Since we just added another post to the database we need to also refresh
			//the session. We could do that here. Or we could simply call the newsfeed servlet. Most developers
			//would prefer to call the newsfeed servlet rather than create potentially duplicate code somewhere.
			//To call the newsfeed servlet set the nextURL to the @WebServlet annotation that can be found 
			//at the top of the newsfeed servlet.
			//Note also that we need a leading slash here because .getRequestDispatcher requires it.
			nextURL = "/Newsfeed";
		}
		
		//go to the newsfeed or error - whichever page is in the nextURL variable
		//this should always be the last line of the doPost or doGet method since it's 
		//redirecting us to another page. Our job here is done.
		getServletContext().getRequestDispatcher(nextURL).forward(request, response);
		
	}

}