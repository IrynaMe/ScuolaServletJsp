<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="java.lang.Integer" %>
    <%@ page import="main.servlet.Persona" %>


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


String userCf = (String) session.getAttribute("userCf");
%>
 <div class='alert alert-info' role='alert'>
                    Utente corrente: <%= currentUser %>
                    Profilo: <%= currentProfile %>
                    Cf: <%= userCf %>
                </div>

<div class="container mt-5">
    <h2>Scegli la materia</h2>

   <form action="ScuolaServlet" method="GET">
       <!-- Hidden input field to specify the action -->
       <input type="hidden" name="action" value="scegliMateria">
       <div class="form-group">
           <label for="selectMateria">Materia</label>
           <select name="materia" class="form-control" id="selectMateria">
               <%
               ArrayList<String> materieInsegnate = (ArrayList<String>) request.getAttribute("listaMaterie");
               if (materieInsegnate != null && !materieInsegnate.isEmpty()) {
                   for (String materia : materieInsegnate) { %>
                       <option value="<%= materia %>"><%= materia %></option>
                   <% }
               } else { %>
                   <option>Nessuna materia disponibile</option>
               <% } %>
           </select>
       </div>
       <button type="submit" class="btn btn-primary">Conferma</button>

   </form>
<%

String materia = request.getParameter("materia");
session.setAttribute("materiaScelta", materia);

%>

<%}%>

    </br>
    <!-- Back button form -->
    <form id="backForm" action="ScuolaServlet" method="post">
        <input type="hidden" name="formType" value="login">
    </form>

    </br>
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

    // Function to submit the back form
    function submitBackForm() {
        document.getElementById('backForm').submit();
    }
</script>
</body>
</html>