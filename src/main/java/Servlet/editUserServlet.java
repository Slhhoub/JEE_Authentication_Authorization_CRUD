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

/**
 * Servlet implementation class editUserServlet
 */

@WebServlet("/editUser")
public class editUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String id = request.getParameter("id");
	        String login = request.getParameter("username");
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        String url = "jdbc:mysql://localhost:3306/jee_etudiant";
	        String user = "root";
	        String pwd = "";

	        try (Connection con = DriverManager.getConnection(url, user, pwd)) {
	            if (con != null) {
	                PreparedStatement pst = con.prepareStatement("SELECT login FROM users WHERE id = ?");
	                pst.setString(1, id);
	                ResultSet rs = pst.executeQuery();
	                if (!rs.next()) {
	                    request.setAttribute("errorMessage", "Utilisateur inexistant");
	                    request.getRequestDispatcher("register.jsp").forward(request, response);
	                } else {
	                    if (password == null || password.isEmpty()) {
	                        pst = con.prepareStatement("UPDATE users SET login=?, email=? WHERE id=?");
	                        pst.setString(1, login);
	                        pst.setString(2, email);
	                        pst.setString(3, id);
	                        
	                        int rowsAffected = pst.executeUpdate();
	                        if (rowsAffected > 0) {
	                            request.setAttribute("errorMessage", "Utilisateur modifié avec succès");
	                        } else {
	                            request.setAttribute("errorMessage", "Erreur lors de la modification de l'utilisateur");
	                        }
	                        request.getRequestDispatcher("ListUser.jsp").forward(request, response);
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
