function show_subtask_popup() {
	var popup = new AJS.Dialog(860, 530);
	popup.show();
}

AJS.$(document).ready(function() {
	AJS.$("#subtasker-web-item-link").attr("href", "javascript:show_subtask_popup();");
});