<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Input CF</title>
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
            <label for="cfInput">Inserisci CF di <%= request.getParameter("personType") %></label>
            <input type="text" class="form-control" id="cfInput" name="cf" placeholder="Inserisci il codice fiscale">
        </div>
        <!-- Hidden input field to pass personType -->
        <input type="hidden" id="personTypeInput" name="personType" value="<%= request.getParameter("personType") %>">
        <!-- Hidden input field to specify the action -->
        <input type="hidden" name="action" value="cercaPersona">
        <button type="submit" class="btn btn-primary" name="submitAction" value="cerca">Cerca</button>

        <!-- Button cambia stato solo per amministratore -->
        <% if(currentProfile.equals("amministrativo")) { %>
        <button type="submit" class="btn btn-primary" name="submitAction" value="cambiaStato">Cambia stato</button>
        <% } %>

    </form>
       <div class="back-button my-4">
            <a href="<%= request.getContextPath() %>/welcome.jsp" class="btn btn-secondary">Torna alla Menu</a>
       </div>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


</body>
</html>
