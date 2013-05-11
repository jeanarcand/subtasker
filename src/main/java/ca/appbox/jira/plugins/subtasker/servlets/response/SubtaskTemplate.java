package ca.appbox.jira.plugins.subtasker.servlets.response;

public class SubtaskTemplate {

	private String templateKey;
	private String templateSummary;

	public SubtaskTemplate(String templateKey, String templateSummary) {
		super();
		this.templateKey = templateKey;
		this.templateSummary = templateSummary;
	}

	public String getTemplateKey() {
		return templateKey;
	}

	public String getTemplateSummary() {
		return templateSummary;
	}
}
