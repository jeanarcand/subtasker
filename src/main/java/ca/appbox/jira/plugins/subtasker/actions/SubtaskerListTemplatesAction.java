package ca.appbox.jira.plugins.subtasker.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ofbiz.core.entity.GenericEntityException;

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.message.I18nResolver;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class SubtaskerListTemplatesAction extends JiraWebActionSupport {

	private static final long serialVersionUID = -3267404496946491955L;

	private static final String PROJECT_KEY_SETTING = "projectKeySetting";

	private ProjectManager projectManager;
	private IssueManager issueManager;
	
	// FIXME : shouldn't really need to use that resolver but the getText() of the super class
	// seems broken since it can't locate the resources.	
	private I18nResolver i18nResolver;
	
	private boolean subtaskTemplateProjectDefined = false;
	
	private List<MutableIssue> templateIssues = new ArrayList<MutableIssue>();
	
	public SubtaskerListTemplatesAction(PluginSettingsFactory pluginSettingsFactory,
			ProjectManager projectManager, IssueManager issueManager,
			I18nResolver i18nResolver) {
		
		this.projectManager = projectManager;
		this.issueManager = issueManager;
		this.i18nResolver = i18nResolver;
		
		String templateProjectKeySettingValue = (String) pluginSettingsFactory.createGlobalSettings().get(PROJECT_KEY_SETTING);
		
		if (templateProjectKeySettingValue != null && !templateProjectKeySettingValue.isEmpty()) {

			Project templateProject = projectManager.getProjectObjByKey(templateProjectKeySettingValue);
			
			if (templateProject != null) {
				subtaskTemplateProjectDefined = true;
				
				try {
					Collection<Long> issueIdsForProject = issueManager.getIssueIdsForProject(templateProject.getId());
					
					for (Long currentTemplateIssueId : issueIdsForProject) {
						MutableIssue issueObject = issueManager.getIssueObject(currentTemplateIssueId);
						if (!issueObject.isSubTask()) {
							templateIssues.add(issueObject);
						}
					}
				} catch (GenericEntityException e) {
				}
			}
		}
	}
	
	@Override
	protected String doExecute() throws Exception {
		
		if (hasAnyErrors()) {
			return ERROR;
		}
		
		return INPUT;
	}

	@Override
	protected void doValidation() {
		if (!subtaskTemplateProjectDefined) {
			addErrorMessage(i18nResolver.getText("ca.appbox.jira.plugins.subtasker.create.subtask.form.no.template.defined.error"));
		}
	}

	public List<MutableIssue> getTemplateIssues() {
		return templateIssues;
	}

	public void setTemplateIssues(List<MutableIssue> templateIssues) {
		this.templateIssues = templateIssues;
	}
}
