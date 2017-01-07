function searchForExpenseListsToSend() {
	var username = sessionStorage.getItem('username');
	getExpenseLists(username, generateSendEmailExpenseListRBG);
}

function generateSendEmailExpenseListRBG(expenseLists) {
	var hookup = $("#sendEmailExpenseListRBGHookup").empty();
	if (expenseLists.length != 0) {
		hookup.append("<h5>Raspoloživi troškovnici</h5>");
		for (var i = 0; i < expenseLists.length; ++i) {
			var radioTemplate = $("#radioTemplate");
			var cloned = radioTemplate.clone().removeAttr("style").removeAttr(
					'id');
			cloned.children("label").html(expenseLists[i].name);
			if (i == 0) {
				cloned.children("input")
						.attr("name", "sendEmailExpenseListRBG").attr('value',
								expenseLists[i].name).attr('checked', true);
			} else {
				cloned.children("input")
						.attr("name", "sendEmailExpenseListRBG").attr('value',
								expenseLists[i].name);
			}
			hookup.append(cloned);
		}
		$('#sendEmailSearchUsersForm').show(750);
	} else {
		hookup
				.append("<label style='font-size: 20px'>Za slanje troškovnika potrebno je prvo izraditi jedan. </label>");
		$('#sendEmailSearchUsersForm').hide();
	}
}

function generateSendEmailUsersRBG(users, searchParameter, parameterValue) {
	var hookup = $("#sendEmailUsersRBGHookup").empty();
	if (users.length != 0) {
		hookup.append("Označite kome želite poslat troškovnik<p>");

		for (var i = 0; i < users.length; ++i) {
			var checkboxTemplate = $("#radioTemplate");
			var cloned = checkboxTemplate.clone().removeAttr("style")
					.removeAttr("id");

			var html = "<i>Korisničko ime:</i>  <b>" + users[i].username
					+ "</b><i>  Ime:</i>  <b>" + users[i].firstName
					+ "</b><i>  Prezime:</i>  <b>" + users[i].lastName
					+ "</b><i>  Email:</i>  <b>" + users[i].email + "</b>";
			cloned.children("label").html(html);
			if (i == 0) {
				cloned.children("input").attr("name", "sendEmailUsersRBG")
						.attr('value', users[i].email).attr('checked', true);
			} else {
				cloned.children("input").attr("name", "sendEmailUsersRBG")
						.attr('value', users[i].email);
			}

			hookup.append(cloned);
		}

		hookup
				.append("<div id='loaderHolder1' style='display: none;'>Šaljem mail<div class='loader'></div></div>");

		hookup
				.append("<button class='w3-btn w3-red w3-round-large' onclick='sendEmail()' id='sendEmailButton'>Pošalji troškovnik</button><p>");
	} else {
		hookup.append("Za odabrani parametar pretrage'"
				+ "'i vrijednost paramtera '" + parameterValue
				+ "'ne postoji niti jedan rezultat rezultat.<p>");
	}
}

function searchForUsers() {
	var searchParameter = $("select[name='searchParametersOptions']").find(
			":selected").val();
	var parameterValue = $("#parameterValue").val();
	if (parameterValue == "") {
		return;
	}

	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/baza-podataka/service/users/"
				+ searchParameter + "/" + parameterValue,
		crossDomain : true,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			generateSendEmailUsersRBG(responseData, searchParameter,
					parameterValue);
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('GET failed.');
		}
	});

}

function sendEmail() {
	var expenseListName = $('#sendEmailExpenseListRBGHookup').find(
			'input:checked').val();
	var userEmailTo = $('#sendEmailUsersRBGHookup').find('input:checked').val();
	var username = sessionStorage.getItem('username');

	var url = "/troskovnik-zivota/service/expenseList/sendEmail/";
	url += username + "/" + expenseListName + "/" + userEmailTo;

	showLoader('loaderHolder1', 'sendEmailButton');

	$.ajax({
		type : 'POST',
		url : url,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			openModal('modal1', 'Troskovnik ' + expenseListName
					+ ' uspješno poslan.');
			hideLoader('loaderHolder1', 'sendEmailButton');
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('POST failed.');
		}
	});
}