var popup;

function show_subtask_popup() {
	popup = new AJS.Dialog({
	    width:650, 
	    height:175, 
	    id:"subtasker-dialog", 
	    closeOnOutsideClick: true
	});
	
	popup.addHeader("", "subtasker-popup-title");
	popup.addButton("", function() {popup.hide();}, "subtasker-popup-cancel-button");
	popup.addButton("", function() {submit_subtask_creation_form();}, "subtasker-popup-create-sub-tasks-button");

	AJS.$.get(contextPath + "/SubtaskerListTemplates.jspa", function(data) {
		AJS.$("#subtasker-dialog .dialog-page-menu").attr("style", "width:0%;height:100%;margin:0;padding:0;");
		AJS.$("#subtasker-dialog .dialog-page-body").html(data);
		popup.show();
	});
}

AJS.$(document).ready(function() {
	AJS.$("#subtasker-web-item-link").attr("href", "javascript:show_subtask_popup();");
});

function submit_subtask_creation_form() {
	var currentIssueId = AJS.$("#key-val").text();
	AJS.$("#subtasker-current-issue-id").val(currentIssueId);
    var form = AJS.$('#subtasker-popup-form');
	AJS.$.ajax({
        type: "post",
        data: form.serialize(),
        url: contextPath + "/SubtaskerCreateSubtasks.jspa",
	        success: function(data) {
	        	AJS.$("#subtasker-dialog .dialog-page-body").html(data);
	        }
		});
}