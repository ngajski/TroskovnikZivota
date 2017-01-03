/**
 * Method which starts synchronus sending of expenseList 
 * to udaljenaBaza.
 * 
 * @param expenseListName String
 * @param username	String
 */
function send(expenseListName,username) {
	getExpenseListFromLokalna(expenseListName,username);
}

/**
 * Method used to retrieve expense list with given
 * <code>expenseListName</code>. 
 * 
 * @param expenseListName
 *            expenseListName
 */
function getExpenseListFromLokalna(expenseListName,username) {
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
/**
 * Method used for attaching expenseList to user and storing that
 * expense list to the distant database.
 * 
 * @param expenseList
 * @param username
 */
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
			console.log('GET failed nisam dodao troskovnik. ' + responseData);
		}
	});
}

/**
 * Checks distant database for ExpenseList whose owner is
 * User given with username.
 * 
 * @param username of User
 * @returns	true if ExpenseList exists
 */
function checkInbox(username) {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/baza-podataka/service/expenseList/check/"
			+ username,
		dataType : 'text',
		contentType : "application/json",
		success : function(responseData, textStatus, jqXHR) {
			console.log('Provjera novih troškovnika uspješna! Status: ' + responseData);
			if (responseData == "true") {
				openModal('modal2', 'Imate pristigle troškovnike');
				getExpenseListsFromUdaljena(username);
			} 
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('GET failed Nisam provjerio troškovnike!');
		}
	});
}

/**
 * Retrieves list of ExpenseLists whose owner is User
 * given with username.
 * 
 * @param username User
 * @returns list of ExpenseList
 */
function getExpenseListsFromUdaljena(username) {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/baza-podataka/service/expenseList/get/"
			+ username,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			console.log("Dohvaćeni troškovnici.");
			saveExpenseListsToLokalna(responseData,username);
			
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('GET failed Nisam dohvatio troškovnike!');
		}
	});
}

/**
 * Saves list of ExpenseLists to local database and
 * sets thir owner to User given with username.
 * 
 * @param expenseLists ExpenseList
 * @param username User
 * 
 */
function saveExpenseListsToLokalna(expenseLists,username) {
	var json = JSON.stringify(expenseLists);
	$.ajax({
		type : 'POST',
		url : "http://localhost:8080/troskovnik-zivota/service/expenseList/store/"
			+ username,
		data : json,
		contentType : "application/json",
		dataType : 'text',
		contentType : "application/json",
		success : function(responseData, textStatus, jqXHR) {
			var val = responseData;
			console.log("Troškovnici spremljeni u lokalnu.");
			for (var i = 0; i < expenseLists.length; i++) {
				removeExpenseListFromUdaljena(expenseLists[i].name);
			}
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('GET failed nisam dodao troskovnik. ' + responseData);
		}
	});
}

/**
 * Removes ExpenseList given with its name from distant 
 * database.
 * 
 * @param expenseListName ExpenseList name
 *
 */
function removeExpenseListFromUdaljena(expenseListName) {
	$.ajax({
		type : 'DELETE',
		url : "http://localhost:8080/baza-podataka/service/expenseList/remove/"
			+ expenseListName,
		dataType : 'text',
		success : function(responseData, textStatus, jqXHR) {
			console.log('Izbrisao troškovnik: ' + expenseListName);
		},
		error : function(responseData, textStatus, errorThrown) {
			console.log('POST failed nisam izbriso troskovnik.');
		}
	});
}