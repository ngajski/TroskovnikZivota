function getNotifications(username) {
	$.ajax({
		type : 'GET',
		url : "http://localhost:8080/baza-podataka/service/notifications/all/"
				+ username,
		crossDomain : true,
		dataType : 'json',
		success : function(responseData, textStatus, jqXHR) {
			displayNotifications(responseData, username)
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('POST failed.');
		}
	});
}

function displayNotifications(notifications, username) {
	var html = "";
	if (notifications.length > 0) {
		for (var i = 0; i < notifications.length; ++i) {
			html += createNotificationDiv(notifications[i]);
		}
		openModal('modal1', html);
		removeNotifications(username);
	}
}

function removeNotifications(username) {
	$.ajax({
		type : 'DELETE',
		url : "http://localhost:8080/baza-podataka/service/notifications/all/"
				+ username,
		crossDomain : true,
		dataType : 'text',
		success : function(responseData, textStatus, jqXHR) {
		},
		error : function(responseData, textStatus, errorThrown) {
			alert('POST failed.');
		}
	});
}

function createNotificationDiv(notification) {
	var html = "<div class='w3-row w3-section'>";
	html += notification.message;
	html += "</div>";
	return html;
}