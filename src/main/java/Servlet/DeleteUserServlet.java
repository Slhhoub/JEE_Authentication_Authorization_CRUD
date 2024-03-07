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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String id = request.getParameter("id");
		

	        String url = "jdbc:mysql://localhost:3306/jee_etudiant";
	        String user = "root";
	        String pwd = "";

	        Connection con = null;
	        PreparedStatement pst = null;
	        ResultSet rs = null;
	        
	     // Inside your doPost() method
	        try {
	            // Your connection setup code
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(url, user, pwd);

	            // Check if connection is established
	            if (con != null) {
	                pst = con.prepareStatement("SELECT * FROM users WHERE id=?");
	                pst.setString(1, id);
	                rs = pst.executeQuery();
	                if (rs.next()) {
	                    pst = con.prepareStatement("DELETE FROM users WHERE id=?");
	                    pst.setString(1, id);
	                    int rowsAffected = pst.executeUpdate(); // Use executeUpdate() for DELETE statement

	                    if (rowsAffected > 0) { // Check if deletion was successful
	                        request.setAttribute("errorMessage", "User removed successfully");
	                        request.getRequestDispatcher("ListUser.jsp").forward(request, response);
	                    } else {
	                        request.setAttribute("errorMessage", "Failed to remove user");
	                        request.getRequestDispatcher("ListUser.jsp").forward(request, response);
	                    }
	                } else {
	                    request.setAttribute("errorMessage", "User not found");
	                    request.getRequestDispatcher("ListUser.jsp").forward(request, response);
	                }
	            } else {
	                System.out.println("Error connecting to the database");
	                response.sendRedirect("login.jsp");
	            }
	        } catch (Exception e) {
	            // Handle exceptions gracefully
	            System.out.println("Error: " + e);
	            response.sendRedirect("login.jsp"); // Redirect to an error page
	        } finally {
	            // Close resources in finally block
	            try {
	                if (rs != null) rs.close();
	                if (pst != null) pst.close();
	                if (con != null) con.close();
	            } catch (SQLException ex) {
	                System.out.println("Error closing resources: " + ex);
	            }
	        }

	        
	        

	   
	        
	}

}
