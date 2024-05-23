<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <%@ page import="java.util.ArrayList" %>
    <%@ page import="main.servlet.Persona" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Composizione della classe</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>

</head>
<body>
<div class="container">
    <div class="row">
        <%
            String sTitolo = (String) request.getAttribute("nomeClasse");
            %>
        <h2>Composizione della classe <%= sTitolo %></h2>

        <%
        ArrayList<Persona> listaAllievi = (ArrayList<Persona>) request.getAttribute("listaAllievi");
           %>

        <table>
            <tr><th>CF</th><th>Nome</th><th>Cognome</th><th>Sesso</th><th>Stato nascita</th><th>Provincia nascita</th><th>Comune nascita</th><th>Data nascita</th><th>Email</th><th>Abilitato</th></tr>

            <% for (Persona all : listaAllievi) { %>
                <tr>
                    <td><%= all.getCf()%></td>
                    <td><%= all.getNome()%></td>
                    <td><%= all.getCognome()%></td>
                     <td><%= all.getSesso()%></td>
                     <td><%= all.getStatoNascita() %></td>
                     <td><%= all.getProvinciaNascita() %></td>
                     <td><%= all.getComuneNascita() %></td>
                     <td><%= all.getDataNascita() %></td>
                    <td><%= all.getEmail() %></td>
                    <td><%= all.getAbilitato() %></td>
                </tr>
            <% } %>
        </table>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>

