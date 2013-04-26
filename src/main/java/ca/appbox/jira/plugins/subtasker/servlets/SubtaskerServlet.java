package ca.appbox.jira.plugins.subtasker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that the sub-task creation.
 * 
 * @author Jean Arcand
 */
public class SubtaskerServlet extends HttpServlet {

	private static final long serialVersionUID = -4486823641817126268L;
	
	public SubtaskerServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.getWriter().write("");
	}
}
