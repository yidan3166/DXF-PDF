package com.websiteName.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.websiteName.bo.BO_Check;
import com.websiteName.bo.BO_User;
import com.websiteName.dto.User;
import com.websiteName.util.PasswordEncryption;

/**
 * Servlet implementation class Register
 */
public class RegisterNoHTML5 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterNoHTML5() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String errorPage = "register.jsp"; // redirect here after bad username,
											// email or password

		String email = request.getParameter("email");
		System.out.println("email input iz register.jsp je: " + email);
		String password = request.getParameter("password");
		String username = request.getParameter("username");

		if (email == null || email.equals("") || password == null || password.equals("") || username == null
				|| username.equals("")) {
			request.getSession().setAttribute("message", "Please fill all required fields");
			response.sendRedirect(errorPage);

		} else {
			Map<String, Boolean> exists = new BO_Check().existsUsernameOrEmail(username, email);

			if (exists == null) {
				// email and username are free
				if (new BO_Check().isPasswordLengthOK(password)) {

					//Password encryption
					PasswordEncryption encryption = new PasswordEncryption(); 
					String encryptedPassword = encryption.mixPasswordText(password);
					encryptedPassword = encryption.encryptPassword(encryptedPassword);
					//End of encryption
					
					//Email and username encryption
					String encryptedEmail = encryption.encryptOtherText(email);
					String encryptedUsername = encryption.encryptOtherText(username);
					//End of encryption

					User user = new User(encryptedUsername, encryptedEmail, encryptedPassword);
					boolean userAdded = new BO_User().addUser(user);

					if (userAdded) {
						request.getSession().setAttribute("message", "You are successfully registered! Please login.");
						response.sendRedirect("login.jsp");
					} else {
						request.getSession().setAttribute("message", "Sorry, an error occured. Please try again");
						response.sendRedirect("register.jsp");
					}

				} else {
					request.getSession().setAttribute("message",
							"Sorry, password must be between 8 and 30 charaters long");
					response.sendRedirect(errorPage);
				}

			} else if (exists.get("email") && exists.get("username")) {
				request.getSession().setAttribute("message",
						"Sorry, username '" + username + "' and email '" + email + "' already exist!");
				response.sendRedirect(errorPage);

			} else if (exists.get("email")) {
				request.getSession().setAttribute("message", "Sorry, email '" + email + "' already exists!");
				response.sendRedirect(errorPage);

			} else {
				request.getSession().setAttribute("message", "Sorry, username '" + username + "' already exists!");
				response.sendRedirect(errorPage);
			}
		}

	}

}
