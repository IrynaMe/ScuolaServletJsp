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
    <h2>Inserisci Prova dell'allievo</h2>

<%
String step =(String) request.getParameter("step");
if (step != null) {
if(step.equals("materiaSelect")){
%>
   <form action="ScuolaServlet" method="GET">
       <!-- Hidden input field to specify the action -->
       <input type="hidden" name="action" value="confirmMateria">
        <input type="hidden" name="step" value="materiaSelect">
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

       <button type="submit" class="btn btn-primary" >Conferma</button>

   </form>
<%

String materia = request.getParameter("materia");
session.setAttribute("materiaScelta", materia);

}
else if(step.equals("allievoSelect")){
%>

   <form action="ScuolaServlet" method="GET">
       <!-- Hidden input field to specify the action -->
       <input type="hidden" name="action" value="confirmAllievo">
        <input type="hidden" name="step" value="allievoSelect">

       <div class="form-group">
           <label for="selectAllievo">Allievo</label>
           <select name="allievo" class="form-control" id="selectAllievo">
               <%
               ArrayList<Persona> allieviInClasse = (ArrayList<Persona>) request.getAttribute("listaAllieviInClasse");
               if (allieviInClasse != null && !allieviInClasse.isEmpty()) {
                   for (Persona allievo : allieviInClasse) {

                   %>
                        <option value="<%= allievo.getCf() %>_<%= allievo.getNome() %>_<%= allievo.getCognome() %>">
                            <%= allievo.getCf() %> <%= allievo.getNome() %> <%= allievo.getCognome() %>
                        </option>

                   <% }
               } else { %>
                   <option>Nessun allievo disponibile</option>
               <% }%>
           </select>

       </div>

       <button type="submit" class="btn btn-primary">Conferma</button>
   </form>

<%
String allievoSelected = request.getParameter("allievo");
       String[] allievoParts = null;
       if (allievoSelected != null) {
           allievoParts = allievoSelected.split(" ");
           session.setAttribute("cfAllievo", allievoParts[0]);
              request.setAttribute("step","provaInput");

       }
       }
else if(step.equals("provaInput")){
%>
<form action="ScuolaServlet" method="post">
    <input type="hidden" name="formType" value="newProva">
       <input type="hidden" name="step" value="provaSelect">
    <div class="row mb-3">
        <label for="inputVoto" class="col-sm-3 col-form-label">Voto</label>
        <div class="col-sm-9">
            <input type="number" class="form-control" name="voto" id="inputVoto" min="1" max="10" placeholder="Voto da 1 a 10" required>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Inserisci prova</button>
</form>
<%}
}%>

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