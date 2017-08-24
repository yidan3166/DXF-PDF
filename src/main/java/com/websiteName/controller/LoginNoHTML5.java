package com.websiteName.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.websiteName.bo.BO_GetUser;
import com.websiteName.dto.User;
import com.websiteName.util.PasswordEncryption;

/**
 * Servlet implementation class Login
 */
public class LoginNoHTML5 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginNoHTML5() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String emailOrUsername = request.getParameter("emailOrUsername");
		String password = request.getParameter("password");

		User user;

		if (emailOrUsername == null || emailOrUsername.equals("") || password == null || password.equals("")) {
			request.getSession().setAttribute("message", ("Please fill all required fields"));
			response.sendRedirect("login.jsp");
		} else {
			// check if input is email or username
			if (emailOrUsername.contains("@")) {
				user = new BO_GetUser().getUserByEmail(emailOrUsername);
				if (user == null) {
					request.getSession().setAttribute("message",
							("Email '" + emailOrUsername + "' is not registered!"));
					response.sendRedirect("login.jsp");

					return;
				}

			} else {
				user = new BO_GetUser().getUserByUsername(emailOrUsername);
				if (user == null) {
					request.getSession().setAttribute("message",
							("Username '" + emailOrUsername + "' is not registered!"));
					response.sendRedirect("login.jsp");

					return;
				}

			}

			// if user exists get the results
			if (user != null) {
				
				//Password encryption
				PasswordEncryption encryption = new PasswordEncryption(); 
				password = encryption.mixPasswordText(password);
				password = encryption.encryptPassword(password);
				//End of encryption
				
				if (user.getPassword().equals(password)) {
					request.getSession().setAttribute("user", user);
					request.getRequestDispatcher("WEB-INF/jsp/userProfile.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("message", ("Password wrong!"));
					response.sendRedirect("login.jsp");
				}

			}
		}

	}
}
