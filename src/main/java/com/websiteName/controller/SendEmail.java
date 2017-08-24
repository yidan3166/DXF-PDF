package com.websiteName.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.websiteName.bo.BO_SendEmail;

/**
 * Servlet implementation class SendEmail
 */
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String mailSender = request.getParameter("email");
		String emailMessage = request.getParameter("textarea");
		String language = request.getParameter("language");
		
		request.getSession().setAttribute("poruka", emailMessage);

		System.out.println(mailSender);
		System.out.println(emailMessage);
		System.out.println(language);
		
		//send email
		boolean messageSent = new BO_SendEmail().sendInfoEmail(mailSender, emailMessage);

		if (messageSent) {
			//Respond to client (email received)
			boolean emailReceived = new BO_SendEmail().sendResponseEmail(mailSender, language);
			System.out.println("Email received, automatic response, sent? -------********--------- "+ emailReceived);
			
			
			if (language.equals("ba")) {
				request.getSession().setAttribute("message", "Poruka poslana.");
				request.getSession().setAttribute("thanks", "Hvala vam! ");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else if (language.equals("de")) {
				request.getSession().setAttribute("message", "Ihre Nachrich wurde gesendet.");
				request.getSession().setAttribute("thanks", "Dankeschön! ");
				request.getRequestDispatcher("de.jsp").forward(request, response);
			}else {
				request.getSession().setAttribute("message", "Message sent successfully.");
				request.getSession().setAttribute("thanks", "Thank you! ");
				request.getRequestDispatcher("en.jsp").forward(request, response);
			}
			
		} else {
			if (language.equals("ba")) {
				request.setAttribute("message", "Nažalost, javila se greška. Molimo pokušajte ponovo.");
			}else if (language.equals("de")) {
				request.setAttribute("message", "Leider ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.");
			}else {
				request.setAttribute("message", "Sorry, some error occurred. Please try again.");
			}
			
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
