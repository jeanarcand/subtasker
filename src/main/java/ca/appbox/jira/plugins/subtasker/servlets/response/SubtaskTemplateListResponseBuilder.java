package ca.appbox.jira.plugins.subtasker.servlets.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;

public class SubtaskTemplateListResponseBuilder {

	private IssueManager issueManager;
	
	public SubtaskTemplateListResponseBuilder(IssueManager issueManager) {
		super();
		this.issueManager = issueManager;
	}

	public SubtaskTemplateListResponse buildResponseFromProjectId(Long projectId) {
		
		Collection<Long> templateIssuesIds = new ArrayList<Long>();
		SubtaskTemplateListResponse response = new SubtaskTemplateListResponse();
		
		try {
			templateIssuesIds = issueManager.getIssueIdsForProject(1000L);
		} catch (GenericEntityException e) {
			//handle me
		}
		
		List<Issue> templateIssues = issueManager.getIssueObjects(templateIssuesIds);

		for (Issue currentTemplateIssue : templateIssues) {
			SubtaskTemplate currentTemplate = new SubtaskTemplate(currentTemplateIssue.getKey(),
					currentTemplateIssue.getSummary());
			response.addSubtaskTemplate(currentTemplate);
		}
		
		return response;
	}
}
