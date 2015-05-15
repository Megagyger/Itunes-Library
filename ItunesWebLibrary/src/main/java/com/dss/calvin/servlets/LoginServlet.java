package com.dss.calvin.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dss.calvin.entity.User;
import com.dss.calvin.services.UserServiceLocal;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	@EJB
	private UserServiceLocal service;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		User user = service.getUserByUserName(userName);

		if (user != null) {
			if (user.getPassword().equals(password)) {
				// login
				HttpSession session = request.getSession();
				session.setAttribute("pID", user.getPersistenceID());
				session.setMaxInactiveInterval(30 * 60);
				Cookie cookie = new Cookie("persistID", user.getPersistenceID());
				cookie.setMaxAge(30 * 60);
				response.addCookie(cookie);
				response.sendRedirect("http://localhost:8080/dssAssignment/userPage.jsp");
			} else {
				// wrongPassword
				response.sendRedirect("http://localhost:8080/dssAssignment/");
			}
		} else {
			// user does not exist
			response.sendRedirect("http://localhost:8080/dssAssignment/");
		}
	}

}
