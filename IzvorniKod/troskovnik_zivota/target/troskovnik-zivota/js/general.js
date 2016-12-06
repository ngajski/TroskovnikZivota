/*Function which changes window to page with given url*/
function changeWindowUrl(url) {
	window.location.href = url;
}

/*Function which checks if any field with given class name is empty.*/
function emptyExists(className){
	return $('.'+className).filter(function(){
	    return $.trim(this.value).length === 0;
	}).length > 0;
}

/*Function which validates date. Valid formats are dd/mm/yyyy,dd-mm-yyyy or dd.mm.yyyy*/
function isValidateDate(date) {
	return date
			.test('^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$');
}