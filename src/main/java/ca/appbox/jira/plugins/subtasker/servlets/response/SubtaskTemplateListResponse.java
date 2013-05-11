package ca.appbox.jira.plugins.subtasker.servlets.response;

import java.util.ArrayList;
import java.util.List;

public class SubtaskTemplateListResponse {

	private List<SubtaskTemplate> subtaskTemplates = new ArrayList<SubtaskTemplate>();
	
	public void addSubtaskTemplate(SubtaskTemplate subtaskTemplate) {
		this.subtaskTemplates.add(subtaskTemplate);
	}
}
