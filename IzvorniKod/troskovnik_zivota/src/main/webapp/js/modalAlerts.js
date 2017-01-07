function closeModalAlert() {
	document.getElementById("modalAlert").style.display = "none";
}

function createModalAlert() {
	$('body').append('<div id="modalAlert" class="w3-modal"></div>');
	$('#modalAlert')
			.append(
					'<div class="w3-modal-content"><div id="modalAlertp3" class="w3-container w3-red w3-center w3-padding"></div></div>');
	$('#modalAlertp3')
			.append(
					'<span onclick="closeModalAlert()" class="w3-closebtn">&times;</span><p id="modalText">FEED ME SOME TEXT</p>');
	console.log("uspjesno kreiran modal");
}

function displayModalAlert(alertText) {
	document.getElementById("modalText").innerHTML = alertText;
	document.getElementById('modalAlert').style.display = 'block';

}
