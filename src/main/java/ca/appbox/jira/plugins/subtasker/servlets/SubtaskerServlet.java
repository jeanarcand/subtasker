package ca.appbox.jira.plugins.subtasker.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.core.entity.GenericEntityException;

import ca.appbox.jira.plugins.subtasker.servlets.response.SubtaskTemplateListResponseBuilder;

import com.atlassian.jira.issue.Issue;
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

	private Gson gson;
	
	public SubtaskerServlet(IssueManager issueManager) {
		super();
		this.issueManager = issueManager;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		SubtaskTemplateListResponseBuilder subtaskTemplateListResponseBuilder = 
				new SubtaskTemplateListResponseBuilder(issueManager);
		
		String response = gson.toJson(subtaskTemplateListResponseBuilder.buildResponseFromProjectId(1000L));
		
		resp.getWriter().write(response);
	}
}
