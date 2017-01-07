function prepareGeneratePDF() {
	var username = sessionStorage.getItem('username');
	getExpenseLists(username, generatePDFExpenseListsRGB);
}

function generatePDFExpenseListsRGB(expenseLists) {
	var hookup = $("#generatePDFExpenseListsRGBHookup").empty();
	if (expenseLists.length != 0) {
		hookup.append("<h5>Izaberi troškovnik za generirat PDF</h5>");
		for (var i = 0; i < expenseLists.length; ++i) {
			var radioTemplate = $("#radioTemplate");
			var cloned = radioTemplate.clone().removeAttr("style").removeAttr(
					"id");
			cloned.children("label").html(expenseLists[i].name);
			if (i == 0) {
				cloned.children("input").attr("name",
						"generatePDFExpenseListRBG").attr('value',
						expenseLists[i].name).attr('checked', true);
			} else {
				cloned.children("input").attr("name",
						"generatePDFExpenseListRBG").attr('value',
						expenseLists[i].name);
			}
			cloned.children("input").attr('value', expenseLists[i].name);
			hookup.append(cloned);
		}
	} else {
		hookup
				.append("<label style='font-size: 20px'>Za generiranje troškovnika potrebno je prvo izraditi jedan. </label>");
	}
}

function setHref() {
	var username = sessionStorage.getItem('username');

	var name = $('#generatePDFExpenseListsRGBHookup').find('input:checked')
			.val();

	$('#generatePDFLinkButton').attr('href',
			'service/expenseList/generate/' + username + "/" + name);

	return true;
}
