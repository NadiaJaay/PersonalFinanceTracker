package com.PersonalFinance.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.PersonalFinance.beans.User;
import com.PersonalFinance.beans.UserRole;
import com.PersonalFinance.dao.UserDao;
import com.PersonalFinance.dao.UserRoleDao;
import com.PersonalFinance.libs.AdminConfig;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");

	    String email = request.getParameter("email").toLowerCase();
	    String password = request.getParameter("password");
	    String rememberMe = request.getParameter("remember");

	    try {
	        // Check for admin login
	        if (email.equals(AdminConfig.ADMIN_EMAIL)) {
	            if (password.equals(AdminConfig.ADMIN_PASSWORD)) { 
	                HttpSession session = request.getSession();
	                session.setAttribute("loggedInUserId", "admin");
	                session.setAttribute("loggedInUser", AdminConfig.ADMIN_USERNAME);
	                session.setAttribute("email", AdminConfig.ADMIN_EMAIL);
	                session.setAttribute("role", "sysAdmin");

	                response.sendRedirect("adminDashboard");
	                return;
	            } else {
	                request.setAttribute("message", "Invalid admin credentials.");
	                request.getRequestDispatcher("index.jsp").forward(request, response);
	                return;
	            }
	        }

	        // Regular user authentication
	        User user = UserDao.authenticateUser(email, password);
	        if (user == null) {
	            request.setAttribute("message", "Invalid email or password.");
	            request.getRequestDispatcher("index.jsp").forward(request, response);
	            return;
	        }

	        String userId = UserDao.findIdByEmail(email);
	        UserRole role = UserRoleDao.findUserRoleById(userId);

	        HttpSession session = request.getSession();
	        session.setAttribute("user", user);
	        session.setAttribute("loggedInUserId", userId);
	        session.setAttribute("loggedInUser", user.getUsername());
	        session.setAttribute("email", email);
	        session.setAttribute("role", role.getRoleName());

	        if ("sysAdmin".equalsIgnoreCase(role.getRoleName())) {
	            response.sendRedirect("adminDashboard");
	        } else {
	            response.sendRedirect("dashboard");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("message", "An error occurred. Please try again.");
	        request.getRequestDispatcher("index.jsp").forward(request, response);
	    }
	}
}
