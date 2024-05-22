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
            String annoScolastico = (String) request.getAttribute("annoScolastico");
            String livello = (String) request.getAttribute("livello");
            String sezione = (String) request.getAttribute("sezione");
        %>
        <h2>Allievi della classe <%= livello + sezione %> nell'anno scolastico <%= annoScolastico %>:</h2>

        <%
        ArrayList<Persona> listaAllievi = (ArrayList<Persona>) request.getAttribute("listaAllievi");
        %>

        <table>
            <tr>
                <th>CF</th>
                <th>Nome</th>
                <th>Cognome</th>

                <th>Email</th>

            </tr>

            <% for (Persona all : listaAllievi) { %>
                <tr>
                    <td><%= all.getCf() %></td>
                    <td><%= all.getNome() %></td>
                    <td><%= all.getCognome() %></td>

                    <td><%= all.getEmail() %></td>

                </tr>
            <% } %>
        </table>
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
