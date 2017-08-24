package com.websiteName.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class RedirectToLogin
 */
// @WebFilter("/nameOfServlet") - this works on only one servlet
// @WebFilter( {"/nameOfServlet1", "/nameOfServlet2"} ) - this works on multiple
// servlets
// @WebFilter("/*") - this works on everything
// @WebFilter("/folderName/*") - this works on everything in a specific folder
// @WebFilter("/folderName/fileName.jsp") - this works only on the fileName.jsp
// @WebFilter("/folderName/*.jsp") - this DOES NOT WORK ERROR in TOMCAT

/* ------------------ for content in WEB_INF: --------------------------- */
/* you can enter WEB-INF only using request.getDispatcher, so that we must */
/* define the dispatcher type we also want to filter: */
// @WebFilter(value = { "/WEB-INF/jsp/*" }, dispatcherTypes = {
// DispatcherType.REQUEST, DispatcherType.FORWARD })

@WebFilter(value = { "/WEB-INF/jsp/*" }, dispatcherTypes = { DispatcherType.REQUEST, DispatcherType.FORWARD })
public class RedirectToLogin implements Filter {

	/**
	 * Default constructor.
	 */
	public RedirectToLogin() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);

		System.out.println("---------------Filter redirecting-----------------");

		if (session == null || session.getAttribute("user") == null) {
			httpResponse.sendRedirect("index.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
