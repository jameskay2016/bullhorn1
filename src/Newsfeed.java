import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhpost;
import customTools.DbBullhorn;

@WebServlet("/Newsfeed")
public class Newsfeed extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Newsfeed() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet will run if we come to this servlet by clicking on a url or by using the 
		//getRequestDispatcher method of the servlet. This is how we come to most servlets in this application
		//so doGet just calls doPost which contains the code.
		//Alternatively, we could move all the code from doPost to doGet and remove doPost but we do use that sometimes
		//when submitting forms.
		
		
		//since some of the parameters come in the url, pass get to post where all code is handled
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long filterByUserID = 0; 
		String searchtext = "";
		
		String nextURL = "/error.jsp";
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
		
		//get posts based on parameters; if no parameters then get all posts
		List<Bhpost> posts = null;
		if (request.getParameter("userid")!=null){
			filterByUserID = Integer.parseInt(request.getParameter("userid"));
			posts = DbBullhorn.postsofUser(filterByUserID);
		}else if (request.getParameter("searchtext")!=null){
			searchtext = request.getParameter("searchtext").toString();
			posts = DbBullhorn.searchPosts(searchtext);
		}else{
			try {
				posts = DbBullhorn.AllPosts();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//add posts to session
		session.setAttribute("posts", posts);
		//display posts in newsfeed.jsp
		nextURL = "/newsfeed.jsp";
		//redirect to next page as indicated by the value of the nextURL variable
		getServletContext().getRequestDispatcher(nextURL).forward(request,response);
	}

}