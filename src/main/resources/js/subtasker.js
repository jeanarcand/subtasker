function showthat() {
	var popup = new AJS.Dialog(860, 530);
	popup.show();
}

AJS.$(document).ready(function() {
	AJS.$("#subtasker-web-item-link").attr("href", "javascript:showthat();");
});