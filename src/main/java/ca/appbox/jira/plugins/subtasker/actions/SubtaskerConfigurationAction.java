package ca.appbox.jira.plugins.subtasker.actions;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.message.I18nResolver;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class SubtaskerConfigurationAction extends JiraWebActionSupport {

	private static final long serialVersionUID = -3267404496946491955L;

	private static final String PROJECT_KEY_SETTING = "projectKeySetting";

	private String templateProjectKey = "";
	private String submitted;

	private PluginSettings subtaskerSettings;

	private ProjectManager projectManager;

	// FIXME : shouldn't really need to use that resolver but the getText() of the super class
	// seems broken since it can't locate the resources.
	private I18nResolver i18nResolver;
	
	public SubtaskerConfigurationAction(PluginSettingsFactory pluginSettingsFactory,
			ProjectManager projectManager, I18nResolver i18nResolver) {
		this.subtaskerSettings = pluginSettingsFactory.createGlobalSettings();
		this.projectManager = projectManager;
		this.i18nResolver = i18nResolver;
		
		String templateProjectKeySettingValue = (String) subtaskerSettings.get(PROJECT_KEY_SETTING);
		
		if (templateProjectKeySettingValue != null) {
			templateProjectKey = templateProjectKeySettingValue;
		}
	}
	
	@Override
	protected String doExecute() throws Exception {
		if (hasAnyErrors()) {
			return ERROR;
		} else {
			if (templateProjectKey != null) {
				templateProjectKey = templateProjectKey.toUpperCase();
				subtaskerSettings.put(PROJECT_KEY_SETTING, templateProjectKey);
			}
		}
		return INPUT;
	}

	@Override
	protected void doValidation() {
		if ("true".equals(submitted)) {
			if (this.templateProjectKey == null || !isValidProjectKey()) {
				addError("templateProjectKey", i18nResolver.getText("ca.appbox.jira.plugins.subtasker.administration.invalid.project.key.error"));
			}
		}
	}
	
	private boolean isValidProjectKey() {
		Project projectObjByKey = projectManager.getProjectObjByKey(templateProjectKey);
		return projectObjByKey != null;
	}

	public String getTemplateProjectKey() {
		return templateProjectKey;
	}

	public void setTemplateProjectKey(String templateProjectKey) {
		this.templateProjectKey = templateProjectKey;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}
}
