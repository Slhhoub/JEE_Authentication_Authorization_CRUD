package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        String hashedPassword = hashPassword(password); 
        
        String url = "jdbc:mysql://localhost:3306/jee_etudiant";
        String user = "root";
        String pwd = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pwd);
            if (con != null) {
                PreparedStatement pst = con.prepareStatement("SELECT login FROM users WHERE login = ?");
                pst.setString(1, login);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    request.setAttribute("errorMessage", "Utilisateur déjà existant");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                } else {
                    pst = con.prepareStatement("INSERT INTO users (login, email, password) VALUES (?, ?, ?)");
                    pst.setString(1, login);
                    pst.setString(2, email);
                    pst.setString(3, hashedPassword);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        request.setAttribute("errorMessage", "Utilisateur ajouté avec succès");
                    } else {
                        request.setAttribute("errorMessage", "Erreur lors de l'ajout de l'utilisateur");
                    }
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashedBytes = digest.digest(password.getBytes());
            
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
