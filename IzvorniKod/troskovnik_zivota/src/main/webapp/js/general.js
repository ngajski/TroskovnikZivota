/**
 * Function which changes window to page with given url
 * 
 * @returns void
 */
function changeWindowUrl(url) {
	window.location.href = url;
}

/**
 * Function which checks if any field with given class name is empty. *
 * 
 * @returns boolean
 */
function emptyExists(className) {
	return $('.' + className).filter(function() {
		return $.trim(this.value).length === 0;
	}).length > 0;
}

/**
 * Function which opens modal box with given id, and sets given text to text
 * holder in that modal
 * 
 * @returns void
 */
function openModal(modalID, text) {
	var textHolders = $('#' + modalID).find('.textHolder');
	textHolders[0].innerHTML = text;
	$('#' + modalID).show();
}

/**
 * Function which validates date. Valid format is dd/mm/yyyy. Valid date is also
 * undefined or null or "" due to not existing nullable restriction in date base
 * on this field.
 * 
 * @param date
 *            date to validate
 * 
 * @returns boolean
 */
function isValidDate(date) {
	if (isEmpty(date)) {
		return true;
	}
	var reg = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/g;
	if (reg.test(date)) {
		return true;
	} else {
		return false;
	}
}

/**
 * Function which checks if last visite page has declared no back button action.
 * If so back button on browser will be disabled.
 * 
 * @returns void
 */
function validateBackButton() {
	var noBackButton = sessionStorage.getItem('noBackButton');
	if (!(typeof noBackButton === "undefined" || noBackButton == null)) {
		if (noBackButton == "true") {
			window.location.hash = "no-back-button";
			window.location.hash = "Again-No-back-button";// again because
			// google chrome
			// don't insert
			// first hash into
			// history
			window.onhashchange = function() {
				window.location.hash = "no-back-button";
			}
		}
	}
}

function addNoBackButton() {
	sessionStorage.setItem('noBackButton', 'true');
}

function removeNoBackButton() {
	sessionStorage.removeItem('noBackButton');
}

/**
 * Function checks if given string is empty. Empty string is null, undefined or ""
 * 
 * @param string
 *            string to validate
 * @returns boolean
 */
function isEmpty(string) {
	if (typeof string === "undefined" || string == null || string == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * Method used to retrieve all expense lists for user with given
 * <code>username</code>. Method will call callback function with one
 * parameter , retrieved list of expense lists.
 * 
 * @param username
 *            username
 * @param callback
 *            callback function
 * @returns void
 */
function getExpenseLists(username, callback) {
	$.ajax({
		type : 'GET',
		url : "service/expenseList/" + username,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			callback(responseData);
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('GET failed expense lists.');
		}
});
}


/**
 * Method which starts synchronus sending of expenseList 
 * to udaljenaBaza.
 * 
 * @param expenseListName String
 * @param username	String
 */
function send(expenseListName,username) {
	getExpenseList(expenseListName,username);
}

/**
 * Method used to retrieve expense list with given
 * <code>expenseListName</code>. 
 * 
 * @param expenseListName
 *            expenseListName
 */
function getExpenseList(expenseListName,username) {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/troskovnik-zivota/service/expenseList/byName/"
				+ expenseListName,
		crossDomain : true,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			var expenseList = responseData; 
			setExpenseListToUser(expenseList,username);
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('GET failed.');
		}
	});
}

function getUser(expenseList,username) {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/baza-podataka/service/users/username/"
				+ username,
		crossDomain : true,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			var user = responseData[0];
			setExpenseListToUser(expenseList,user);
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('GET failed.');
		}
	});
}

function setExpenseListToUser(expenseList,username) {
	var json = JSON.stringify(expenseList);
	$.ajax({
		type : 'POST',
		url : "http://localhost:8080/baza-podataka/service/expenseList/store/"
			+ username,
		data : json,
		contentType : "application/json",
		dataType : 'text',
		contentType : "application/json",
		success : function(responseData, textStatus, jqXHR) {
			var val = responseData;
			console.log('Dodao kategoriju ' + val);
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('POST failed nisam dodao troskovnik. ' + responseData);
		}
	});
}