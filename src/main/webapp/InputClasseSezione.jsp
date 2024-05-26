<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="java.lang.Integer" %> <!-- Added import for Integer -->

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Input Codice Fiscale</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2>Inserisci l'anno e la classe</h2>
    <form action="ScuolaServlet" method="GET">
        <!-- Hidden input field to specify the action -->
        <input type="hidden" name="action" value="stampaClasse">
        <div class="form-group">
            <label for="selectAnno">Anno scolastico</label>
            <select name="annoScolastico" class="form-control" id="selectAnno">
            <%
                ArrayList<String> anniScolastici = (ArrayList<String>) request.getAttribute("listaAnniScolastici");
                if (anniScolastici != null) {
                    for (String anno : anniScolastici) { %>
                        <option><%= anno %></option>
                    <% }
                } else { %>
                    <option>Nessun anno disponibile</option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label for="selectLivello">Livello</label>
            <select name="livello" class="form-control" id="selectLivello">
            <%
                ArrayList<Integer> livelli = (ArrayList<Integer>) request.getAttribute("listaLivelli");
                if (livelli != null) {
                    for (Integer livello : livelli) { %>
                        <option><%= livello %></option>
                    <% }
                } else { %>
                    <option>Nessun livello disponibile</option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label for="selectSezione">Sezione</label> <!-- Corrected label -->
            <select name="sezione" class="form-control" id="selectSezione">
            <%
                ArrayList<String> sezioni = (ArrayList<String>) request.getAttribute("listaSezioni");
                if (sezioni != null) {
                    for (String sezione : sezioni) { %>
                        <option><%= sezione %></option>
                    <% }
                } else { %>
                    <option>Nessuna sezione disponibile</option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Stampa allievi</button> <!-- Added submit button -->
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

