package ca.appbox.jira.plugins.subtasker.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.appbox.jira.plugins.subtasker.servlets.response.SubtaskTemplateListResponseBuilder;

import com.atlassian.jira.issue.IssueManager;
import com.google.gson.Gson;

/**
 * Servlet that the sub-task creation.
 * 
 * @author Jean Arcand
 */
public class SubtaskerServlet extends HttpServlet {

	private static final long serialVersionUID = -4486823641817126268L;

	private IssueManager issueManager;

	private Gson gson = new Gson();
	
	public SubtaskerServlet(IssueManager issueManager) {
		super();
		this.issueManager = issueManager;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		SubtaskTemplateListResponseBuilder subtaskTemplateListResponseBuilder = 
				new SubtaskTemplateListResponseBuilder(issueManager);
		
		String response = gson.toJson(subtaskTemplateListResponseBuilder.buildResponseFromProjectId(10000L));
		
		resp.getWriter().write(response);
	}
}
