
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="main.servlet.Persona" %>
  <%@ page import="java.util.ArrayList" %>
 <%@ page import="java.lang.Integer" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista di <%= request.getAttribute("personType") %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
ArrayList<Persona> persone= (ArrayList<Persona>) request.getAttribute("persone");
 Integer n=0;
 String personType=(String)request.getAttribute("personType");
 String personTypePlural = personType.substring(0, personType.length() - 1) + "i";
%>
    <div class="container">
        <h2 class="my-4">Lista di <%= personTypePlural %></h2>
        <table class="table table-hover">
            <thead>
                <tr class="table-primary">
                    <th scope="col">#</th>

                    <th scope="col">CF</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Sesso</th>
                    <th scope="col">Stato nascita</th>
                    <th scope="col">Provincia nascita</th>
                    <th scope="col">Comune nascita</th>
                    <th scope="col">Data nascita</th>
                    <th scope="col">Email</th>
                    <th scope="col">Abilitato</th>
                </tr>
            </thead>
            <tbody>
                <% for (Persona persona : persone) { %>
                    <th scope="row"><%= ++n %></th>
                        <td><%= persona.getCf() %></td>
                        <td><%= persona.getNome() %></td>
                        <td><%= persona.getCognome() %></td>
                        <td><%= persona.getSesso() %></td>
                        <td><%= persona.getStatoNascita() %></td>
                        <td><%= persona.getProvinciaNascita() %></td>
                        <td><%= persona.getComuneNascita() %></td>
                        <td><%= persona.getDataNascita() %></td>
                        <td><%= persona.getEmail() %></td>
                        <td><%= persona.getAbilitato() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <div class="back-button my-4">
            <a href="<%= request.getContextPath() %>/welcome.jsp" class="btn btn-primary">Torna alla Menu</a>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
