package com.websiteName.filters;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

/**
 * Servlet Filter implementation class DetectLanguage
 */
@WebFilter("/index.jsp")
public class DetectLanguage implements Filter {

    /**
     * Default constructor. 
     */
    public DetectLanguage() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/**
		 * LICENCA (ovo treba navesti negdje) This product includes GeoLite2
		 * data created by MaxMind, available from
		 * <a href="http://www.maxmind.com">http://www.maxmind.com</a>.
		 * 
		 * TUTORIAL
		 * http://o7planning.org/en/10455/retrieving-geographic-information-
		 * based-on-ip-address-using-geoip2-java-api
		 */

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String clientIpAdress = httpRequest.getRemoteAddr();

		// Redirect user only the first time
		Boolean firstVisit = (Boolean) httpRequest.getSession().getAttribute("firstVisit");

		if (firstVisit == null) {
			// get database of countries
			ClassLoader classLoader = DetectLanguage.class.getClassLoader();
			File dbFile = new File(classLoader.getResource("GeoLite2-Country.mmdb").getFile());

			DatabaseReader reader = new DatabaseReader.Builder(dbFile).build();
			InetAddress ipAddress = InetAddress.getByName(clientIpAdress);

			// Country Info
			CountryResponse countryResponse;
			Country country;
			String countryCode = "";
			try {
				countryResponse = reader.country(ipAddress);
				country = countryResponse.getCountry();
				countryCode = country.getIsoCode();
			} catch (GeoIp2Exception e) {
				e.printStackTrace();
			}
			
			httpRequest.getSession().setAttribute("firstVisit", false);
			httpRequest.getSession().setAttribute("ip", ipAddress);
			httpRequest.getSession().setAttribute("countryCode", countryCode);
			
			// Check and redirect page to language
			String balkanCodes = "BA-XK-AL-HR-SR-ME-MK-SI";
			String germanCodes = "DE-AT-CH";
			
			if (countryCode.isEmpty() || balkanCodes.contains(countryCode)) {
				// send to index.jsp (bosnian language)
			} else if (germanCodes.contains(countryCode)) {
				httpResponse.sendRedirect("de.jsp");
			} else {
				httpResponse.sendRedirect("en.jsp");
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
