<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="main.model.Persona" %>
      <%@ page import="java.lang.Integer" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Composizione della classe</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <div class="row">
        <%
            String annoScolastico = (String) request.getAttribute("annoScolastico");
            String livello = (String) request.getAttribute("livello");
            String sezione = (String) request.getAttribute("sezione");
        %>
        <h2>Allievi della classe <%= livello + sezione %> nell'anno scolastico <%= annoScolastico %>:</h2>

        <%
        ArrayList<Persona> listaAllievi = (ArrayList<Persona>) request.getAttribute("listaAllievi");
        Integer n=0;
        %>

        <table class="table table-hover">
        <thead>

            <tr class="table-primary">
                <th scope="col">#</th>
                <th scope="col">CF</th>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>

                <th scope="col">Email</th>

                </tr>
              </thead>
        <tbody>
            <% for (Persona all : listaAllievi) { %>
                <tr>
                <th scope="row"><%= ++n %></th>
                    <td><%= all.getCf() %></td>
                    <td><%= all.getNome() %></td>
                    <td><%= all.getCognome() %></td>
                    <td><%= all.getEmail() %></td>

                </tr>
            <% } %>
            </tbody>
        </table>
         </br>

       <div class="back-button my-4">
            <a href="<%= request.getContextPath() %>/welcome.jsp" class="btn btn-secondary">Torna alla Menu</a>
       </div>
    </div>
</div>



<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>
