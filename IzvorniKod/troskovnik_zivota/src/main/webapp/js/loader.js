function showLoader(loaderHolder, buttonToDisable) {
	$('#' + loaderHolder).show('100');
	if (typeof buttonToDisable === "undefined") {

	} else {
		$('#' + buttonToDisable).prop("disabled", true);
	}
}

function hideLoader(loaderHolder, buttonToEnable) {
	$('#' + loaderHolder).hide();
	if (typeof buttonToEnable === "undefined") {

	} else {
		 $('#' + buttonToEnable).prop("disabled", false);
	}
 }