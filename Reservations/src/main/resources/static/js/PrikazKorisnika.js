var getAllLink = "/admin/view-all";

$(document).ready(function(){
    $("#prikaz").on('click', function(e) {
        
        e.preventDefault();
        
        $("#all").remove();
        $("#error").remove();
        
        $.ajax({
            url: getAllLink,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            success: function(users) {
                displayUsers(users);
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
        

    });
});

function displayUsers(users) {
    var text = "<table id=\"all\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>ID</th>";
    text += "<th>Ime</th>";
    text += "<th>Prezime</th>";
    text += "<th>Korisnicko ime</th>";
    text += "<th>Email</th>";
    text += "<th>Adresa</th>";
    text += "<th>Grad</th>";
    text += "<th>Drzava</th>";
    text += "<th>Broj telefona</th>";
    text += "</tr>";
    text += "</thead><tbody>";
    
    for (var user of users) {
    	
	        text += "<tr>";
	        text += "<td>" + user.id + "</td>";
	        text += "<td>" + user.ime + "</td>";
	        text += "<td>" + user.prezime + "</td>";
	        text += "<td>" + user.korisnickoIme + "</td>";
	        text += "<td>" + user.email + "</td>";
	        text += "<td>" + user.adresa + "</td>";
	        text += "<td>" + user.grad + "</td>";
	        text += "<td>" + user.drzava + "</td>";
	        text += "<td>" + user.brojTel + "</td>";
	
	        text += "</tr>";
    }
    text += "</tbody></table>";
    $("#tabela").append(text);

}
