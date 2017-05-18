package voting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try
		{
			String email = request.getParameter("email");
			String password = request.getParameter("pwd");
			System.out.println(this.getClass().getSimpleName()+" Debug1:"+email+" password:"+password);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/evoting?autoReconnect=true&useSSL=false","root","");
			System.out.println(this.getClass().getSimpleName()+" Debug2: After connection established...");
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery("select * from voters where email='"+email+"' AND password='"+password+"'");
			System.out.print(this.getClass().getSimpleName()+" Debug3: Query Fired ");
			System.out.println("select * from voters where email='"+email+"' AND password='"+password+"'");
			if(rs.next())
			{
				System.out.print(this.getClass().getSimpleName()+" Debug4: Redirecting to profilePage ");
				request.setAttribute("key", "success");
				ProcessRequest preq = new ProcessRequest(conn);
				UserBean uBean = new UserBean();
				uBean.setMember_id(rs.getInt("MEMBER_ID"));
				ElectionBean eBean = preq.processing();
				HttpSession session = request.getSession(true);	    
		        session.setAttribute("currentElection",eBean); 
		        session.setAttribute("currentUser", uBean);
	            RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
	            rd.forward(request, response);
			}
			else
			{
				request.setAttribute("key", "failed");
	            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
	            rd.forward(request, response);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
