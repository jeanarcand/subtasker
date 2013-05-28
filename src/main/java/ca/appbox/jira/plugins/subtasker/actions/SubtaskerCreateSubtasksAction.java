package ca.appbox.jira.plugins.subtasker.actions;

import java.util.Collection;
import java.util.Iterator;

import com.atlassian.jira.config.SubTaskManager;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.exception.RemoveException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueFactory;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.message.I18nResolver;

public class SubtaskerCreateSubtasksAction extends JiraWebActionSupport {

	private static final long serialVersionUID = -3267404496946491955L;

	private static final String PROJECT_KEY_SETTING = "projectKeySetting";

	private ProjectManager projectManager;
	private IssueFactory issueFactory;
	private IssueManager issueManager;
	private SubTaskManager subtaskManager;
	
	// FIXME : shouldn't really need to use that resolver but the getText() of the super class
	// seems broken since it can't locate the resources.	
	private I18nResolver i18nResolver;
	
	private String templateIssue;
	private String currentIssueId;
	private String submitted;
	
	public SubtaskerCreateSubtasksAction(ProjectManager projectManager, 
			IssueManager issueManager, SubTaskManager subTaskManager,
			IssueFactory issueFactory, I18nResolver i18nResolver) {
		this.projectManager = projectManager;
		this.issueManager = issueManager;
		this.subtaskManager = subTaskManager;
		this.issueFactory = issueFactory;
		this.i18nResolver = i18nResolver;
		
	}
	
	@Override
	protected String doExecute() throws Exception {
		
		if (hasAnyErrors()) {
			return ERROR;
		}
		if ("true".equals(submitted)) {
			System.out.println("creating the subtasks");
			createSubtasks();
			return "close";
		}
		
		return INPUT;
	}

	private void createSubtasks() {

		// get the current issue (parent of the new subtasks)
		MutableIssue currentIssueObject = issueManager.getIssueObject(currentIssueId);
		
		// get the template issue
		MutableIssue templateIssueObject = issueManager.getIssueObject(Long.valueOf(templateIssue));
		
		Collection<Issue> templateIssueSubtasks = templateIssueObject.getSubTaskObjects();
		
		Iterator<Issue> templateIssueSubtasksIter = templateIssueSubtasks.iterator();
		
		while (templateIssueSubtasksIter.hasNext()) {
			
			Issue currentSubtaskToCreate = templateIssueSubtasksIter.next();
			MutableIssue newSubtask = issueFactory.getIssue();
			
			newSubtask.setProjectId(currentSubtaskToCreate.getProjectObject().getId());
			newSubtask.setIssueTypeId(currentSubtaskToCreate.getIssueTypeObject().getId());
			newSubtask.setParentId(currentSubtaskToCreate.getId());
			newSubtask.setSummary(currentSubtaskToCreate.getSummary());
			newSubtask.setAssignee(currentSubtaskToCreate.getAssignee());
			newSubtask.setDescription(currentSubtaskToCreate.getDescription());
			newSubtask.setReporter(getRemoteUser());
			
			try {
				issueManager.createIssue(getRemoteUser(), newSubtask);
			} catch (CreateException subtaskCreationException) {
			}
			
			try {
				subtaskManager.createSubTaskIssueLink(currentIssueObject, newSubtask, getRemoteUser());
			} catch (CreateException subtaskLinkCreationException) {
				try {
					issueManager.deleteIssue(getRemoteUser(), newSubtask, EventDispatchOption.ISSUE_DELETED, false);
				} catch (RemoveException e) {}
			}
		}
	}

	@Override
	protected void doValidation() {
		if ("true".equals(submitted)) {
			if (templateIssue == null || templateIssue.isEmpty()) {
				addError("templateIssue", i18nResolver.getText("ca.appbox.jira.plugins.subtasker.create.subtask.form.no.template.selected.error"));
			}
		}
	}

	public String getTemplateIssue() {
		return templateIssue;
	}

	public void setTemplateIssue(String templateIssue) {
		this.templateIssue = templateIssue;
	}

	public String getCurrentIssueId() {
		return currentIssueId;
	}

	public void setCurrentIssueId(String currentIssueId) {
		this.currentIssueId = currentIssueId;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}
}
