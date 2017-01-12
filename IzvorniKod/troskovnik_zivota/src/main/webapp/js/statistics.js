function searchExpenseListsForStatistics() {
	var username = sessionStorage.getItem('username');
	getExpenseLists(username,generateExpenseListsAnalisisRadioBox);
}

function generateExpenseListsAnalisisRadioBox(expenseLists) {
	var hookup = $("#expenseListAnalizeRadioBox").empty();
	
	if (expenseLists.length != 0) {
		hookup.append("<h3>Troškonvici za analizu</h3>");
		for (var i = 0; i < expenseLists.length; ++i) {
			var checkboxTemplate = $("#radioTemplate");
			var cloned = checkboxTemplate.clone().removeAttr("style")
					.removeAttr("id");

			cloned.children("input").attr("name", "analizeRadioBtn").attr(
					"value", expenseLists[i].name);
			cloned.children("label").html(expenseLists[i].name);

			if (i == 0) {
				cloned.children("input").attr("checked", "checked");
			}

			hookup.append(cloned);
		}

		$("#statsForm").show();
		
	} else {
		hookup
				.append("<label style='font-size: 20px'>Nema postojećih troškovnika. </label>");
	}
}

function analizeExpenseList() {
	var expenseListName = $('input[name="analizeRadioBtn"]:checked').val();
	var startDate = $('input[name="startForm"]').val();
	var endDate = $('input[name="endForm"]').val();
	
	var hookup = $("#slika").empty();
	hookup.append("<hr>");
	hookup.append("<img alt='Graf 1' src='http://localhost:8080/troskovnik-zivota/graph/incomes?" 
			+ "expenseList=" + expenseListName + "&" 
			+ "startDate=" + startDate + "&" 
			+ "endDate=" + endDate + "'/>");
	hookup.append("<hr>");
	hookup.append("<img alt='Graf 2' src='http://localhost:8080/troskovnik-zivota/graph/expenses?" 
			+ "expenseList=" + expenseListName + "&" 
			+ "startDate=" + startDate + "&" 
			+ "endDate=" + endDate + "'/>");
	hookup.append("<hr>");
	hookup.append("<img alt='Graf 3' src='http://localhost:8080/troskovnik-zivota/graph/sum?" 
			+ "expenseList=" + expenseListName + "&" 
			+ "startDate=" + startDate + "&" 
			+ "endDate=" + endDate + "'/>");
}