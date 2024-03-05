package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String id = request.getParameter("id");
	    

        String url = "jdbc:mysql://localhost:3306/jee_etudiant";
        String user = "root";
        String pwd = "";

        Connection con = null;
        PreparedStatement pst = null;
       

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);
            if(con != null) {
            	System.out.println("success connection db ");
            	/*pst = con.prepareStatement("DELETE FROM users WHERE  id=?");
            	pst.setString(1, id);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                  request.setAttribute("errorMessage", " remove user sucess");
            		request.getRequestDispatcher("ListUser.jsp").forward(request, response);
            		response.sendRedirect("ListUser.jsp");
            	}else {
            		request.setAttribute("errorMessage", "user not remove");
            		
            		request.getRequestDispatcher("ListUser.jsp").forward(request, response);
            	   
            		response.sendRedirect("ListUser.jsp");
            	}*/
            }else {
            	System.out.println("error connection db ");   
            }    
        } catch (Exception e) {
            // Handle exceptions gracefully
            System.out.println("Error: " + e);
        } 
	}

	

}
