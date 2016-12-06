import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import customTools.DbUser;
/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginServlet")//this is where the 
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //if you don't include a method, it goes to doGet by default
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String message = "You entered " + email + " " + password;
		
		request.setAttribute("message", message);
		
		//redirect to next page as indicated by the value of the nextURL variable
		String nextURL = "/output.jsp";
		getServletContext().getRequestDispatcher(nextURL).forward(request, response);
		//response.getWriter().append(message);	
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//this page does not require user to be logged in
		//we could make it an html page or a jsp page
		//the jsp page allows me to use code from other pages so 
		String useremail = request.getParameter("email");
		String userpassword = request.getParameter("password");
		String action = request.getParameter("action");
		String nextURL = "/error.jsp";
		
		//get an instance of the session so we can set attributes to it
		//the JSP and NavBar will read from the session 
		//The session is one of the primary ways we maintain state
		//in an otherwise stateless web application
		HttpSession session = request.getSession();//creating a seeion- sesseion is available on every page
		
		//create a variable to hold our user. The variable must be defined
		//so it is visible to all code within the method. So define it here
		//but set it to null since we don't have a user yet.
		Bhuser user = null;//defined at high scope to be accessible after the if stmet
		//create an instance of the user and put it in the session
		//only add the user to the session if the user if valid.
		//The presence of the user is used to determine who 
		//owns the site and will be used to connect to the database
		if (action.equals("logout")){
			session.invalidate();//kills the session created on line 64, timer handled at webserver level
			nextURL = "/login.jsp";
			
		}else{
			user = DbUser.getUserByEmail(useremail);
			if (DbUser.isValidUser(useremail,userpassword)){//if email n pw is correct
				session.setAttribute("user", user);
				int size = 30;
				nextURL = "/home.jsp";
			}else{
				nextURL = "/login.jsp";//sent here if email n  pw not correct
			}
				
		}
		//redirect to next page as indicated by the value of the nextURL variable
		//getServletContext().getRequestDispatcher(nextURL).forward(request,response);
		response.sendRedirect(request.getContextPath() + nextURL);
	}

}