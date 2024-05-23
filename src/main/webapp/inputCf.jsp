<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Input Codice Fiscale</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    String currentUser = (String) session.getAttribute("currentUser");
    Integer userProfile = (Integer) session.getAttribute("currentProfile");
    String currentProfile = null;
    if (userProfile != null) {
        switch (userProfile) {
            case 1: currentProfile = "amministrativo"; break;
            case 2: currentProfile = "docente"; break;
            case 3: currentProfile = "allievo"; break;
            default: currentProfile = "unknown";
        }
    }
%>

<div class="container mt-5">
    <h2>Inserisci il Codice Fiscale</h2>
    <form action="ScuolaServlet" method="GET">
        <div class="form-group">
            <label for="cfInput">Codice Fiscale:</label>
            <input type="text" class="form-control" id="cfInput" name="cf" placeholder="Inserisci il codice fiscale">
        </div>
        <!-- Hidden input field to pass personType -->
        <input type="hidden" id="personTypeInput" name="personType" value="allievo">
        <!-- Hidden input field to specify the action -->
        <input type="hidden" name="action" value="cercaPersona">
        <button type="submit" class="btn btn-primary" name="submitAction" value="cerca">Cerca</button>

        <!-- Button cambia stato solo per amministratore -->
        <% if(currentProfile.equals("amministrativo")) { %>
        <button type="submit" class="btn btn-primary" name="submitAction" value="cambiaStato">Cambia stato</button>
        <% } %>

    </form>
    <!-- Back button form -->
    <form id="backForm" action="ScuolaServlet" method="post">
        <input type="hidden" name="formType" value="login">
    </form>

    <br>
    <!-- Back button -->
    <div class="back-button">
        <button onclick="submitBackForm()" class="btn btn-secondary">Torna al Menu</button>
    </div>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>
    // Function to extract URL parameter value by name
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }

    // Set the value of personType input field based on the URL parameter
    var personType = getUrlParameter('personType');
    document.getElementById('personTypeInput').value = personType;

    // Function to submit the back form
    function submitBackForm() {
        document.getElementById('backForm').submit();
    }
</script>
</body>
</html>
