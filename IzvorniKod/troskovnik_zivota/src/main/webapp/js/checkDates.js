function checkDates(startDate, endDate, string, fixed, period){

		var startDateChecker = startDate.substring(0, startDate.indexOf('/'));
		var endDateChecker = endDate.substring(0, endDate.indexOf('/'));
		
		if (startDateChecker > 12 || startDateChecker < 1 || endDateChecker > 12 || endDateChecker < 1 ){
			displayModalAlert("Krivo uneseni datumi.");
			return false;
		}
		
		startDateChecker = startDate.substring(startDate.indexOf('/')+1);
		endDateChecker = endDate.substring(endDate.indexOf('/')+1);
		
		if ( ( startDateChecker > endDateChecker ) || ( (startDateChecker[0] != 2 ) && (startDateChecker[0] != 1)) || ((endDateChecker[0] != 2) && (endDateChecker[0] != 1 )) ){
			displayModalAlert("Krivo uneseni datumi.");
			return false;
		}
		
		if (startDateChecker == endDateChecker){
			var help1 = parseInt(startDate.substring(0, startDate.indexOf('/')));
			var help2 = parseInt(endDate.substring(0, endDate.indexOf('/')));
			if ( !(help2 >= help1)){
				displayModalAlert("Krivo uneseni datumi.");
				return false;
			}
		}
				
		var amounts = [];
		var amount = $('#expenseValue').val();
		for (var i = 0; i < amount.split(";").length; i++) {
			amounts.push(amount.split(";")[i]);
		}
		var comment = $('#expenseComment').val();

		/* KAKVE SU OVO MAGIJE */
		var monthCounter = 1;
		var totalMonthsTest = 1;
		for (var i = 0 ; i < string.length ; i++){
			if (string[i] == ";"){
				monthCounter += 1; 
			}
		}
		
		if (period == "MONTHLY"){
		if ( fixed == "NE"){
		if ( startDateChecker < endDateChecker ){
			var help1 = endDateChecker - startDateChecker;
			var help3 = startDate.substring(0, startDate.indexOf('/'));
			var help2 = endDate.substring(0, endDate.indexOf('/'));
			totalMonthsTest = 1 + parseInt(help1)*12 + parseInt(help2)-parseInt(help3);
			
		} else if ( startDateChecker == endDateChecker){
			var help1 = endDate.substring(0, endDate.indexOf('/')) - startDate.substring(0, startDate.indexOf('/')) ;
			totalMonthsTest = 1 + parseInt(help1);
		}else{
			displayModalAlert("Krivo uneseni datumi.");
			return false;
		}
		if ( totalMonthsTest != monthCounter){
			displayModalAlert("Krivo uneseni iznosi u odnosu na unesene datume.");
			return false;
		} else {
			return true;
		}
		}else{
			if (monthCounter != 1){
				displayModalAlert("Za fiksan element smije postojati samo jedan iznos.");
				return false;
			}
			return true;
		}
		}else if ( period == "ANUALY"){
			if ( endDateChecker > startDateChecker){
				var help1 = parseInt(endDateChecker-startDateChecker)*12;
				var help2 = parseInt(startDate.substring(0, startDate.indexOf('/'))) - parseInt(endDate.substring(0, endDate.indexOf('/')));
				var help3 = Math.abs(help1 - help2)+1;
				
				if ( fixed == "DA" && monthCounter != 1){
					displayModalAlert("Broj iznosa se ne podudara s unesenim datumima");
					return false;
				} else if ( fixed == "DA" && monthCounter == 1){
					return true;
				} else if (fixed == "NE"){
				if ( Math.floor(help3/12) != monthCounter){
					displayModalAlert("Broj iznosa se ne podudara s unesenim datumima");
					return false;
				} else {
					return true;
				}
				}
				
			}else {
				if ( monthCounter != 1){
					displayModalAlert("Broj iznosa se ne podudara s unesenim datumima");
					return false;
				}
				return true;
			}
			
		}
		else if (period == "QUARTARLY"){
			var help1 = parseInt(endDateChecker-startDateChecker)*12;
			var help2 = parseInt(startDate.substring(0, startDate.indexOf('/'))) - parseInt(endDate.substring(0, endDate.indexOf('/')));
			var help3 = Math.abs(help1 - help2)+1;
			if (help3 % 3 == 0 ){
				if ( fixed == "DA" && monthCounter != 1){
					displayModalAlert("Za fiksne stavke smije postojati samo jedan iznos");
					return false;
				} else if (fixed == "DA" && (monthCounter == 1)){
					return true;
				}else {
				if ( (help3/3) != monthCounter){
					displayModalAlert("Broj iznosa se ne podudara s unesenim brojem kvartala");
					return false;
				} else{
					return true;
				}}
			}else {
				displayModalAlert("Dani raspon datuma se ne može spremiti kvartalno.");
				return false;
			}
			
		}
		else if (period == "ONE_TIME"){
			if ( startDate != endDate){
				displayModalAlert("Kod jednoročnog perioda datum početka i datum završetka trebaju biti identični.");
				return false;
			}else if (monthCounter != 1){
				displayModalAlert("Kod jednoročnog perioda smije postojati samo jedan iznos.");
				return false;
				} else {
					return true;
				}
			
		}
	}