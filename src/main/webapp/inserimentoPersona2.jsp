<!DOCTYPE html>
<html lang="en">
<head>
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inserimento Persona</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>
        body {
            padding-top: 50px;
            padding-bottom: 50px;
        }
        .container {
            max-width: 800px;
            margin: auto;
        }
        .form-heading {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="form-heading text-center">Inserimento di un nuovo <%= request.getParameter("personType") %></h2>
<%
String step = (String) request.getParameter("step");
if (step != null) {
    if (step.equals("datiStato")) {
%>

<form action="ScuolaServlet" method="GET">
    <input type="hidden" name="step" value="datiStato">
    <input type="hidden" name="action" value="confermaStato">
    <input type="hidden" name="personType" id="personType" value="<%= request.getParameter("personType") %>">
    <div class="row mb-3">
        <label for="inputStatoNascita" class="col-sm-3 col-form-label">Stato di Nascita</label>
        <div class="col-sm-9">
            <select class="form-select" name="statoNascita" id="inputStatoNascita" required>
                <option value="Italia">Italia</option>
                <option value="Estero">Estero</option>
            </select>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Conferma stato</button>
</form>

<%
    } else if (step.equals("datiProvincia")) {
    Set<String> province=(TreeSet <String>) request.getAttribute("listaProvince");
%>

<form action="ScuolaServlet" method="GET">
    <input type="hidden" name="step" value="datiProvincia">
    <input type="hidden" name="action" value="confermaProvincia">
      <input type="hidden" name="personType" id="personType" value="<%= request.getParameter("personType") %>">
    <div class="row mb-3">
        <label for="inputProvinciaNascita" class="col-sm-3 col-form-label">Provincia di Nascita</label>
        <div class="col-sm-9">
            <select class="form-select" name="provinciaNascita" id="inputProvinciaNascita" required>
                <% for (String provincia : province) { %>
                    <option value="<%= provincia %>"><%= provincia %></option>
                <% } %>
            </select>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Conferma provincia</button>
</form>

<%
    } else if (step.equals("datiComune")) {
        ArrayList<String> comuni = (ArrayList<String>) request.getAttribute("listaComuni");
%>

<form action="ScuolaServlet" method="GET">
    <input type="hidden" name="step" value="datiComune">
    <input type="hidden" name="action" value="confermaComune">
 <input type="hidden" name="personType" id="personType" value="<%= request.getParameter("personType") %>">
    <div class="row mb-3">
        <label for="inputComuneNascita" class="col-sm-3 col-form-label">Comune di Nascita</label>
        <div class="col-sm-9">
            <select class="form-select" name="comuneNascita" id="inputComuneNascita" required>
                <% for (String comune : comuni) { %>
                    <option value="<%= comune %>"><%= comune %></option>
                <% } %>
            </select>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Conferma comune</button>
</form>

<%
    } else if (step.equals("datiPersona")) {
%>

<form action="ScuolaServlet" method="POST" onsubmit="return validateCodiceFiscale()">
    <input type="hidden" name="formType" value="newPerson">
    <input type="hidden" name="step" value="datiPersona">
    <input type="hidden" name="personType" id="personType" value="<%= request.getParameter("personType") %>">

    <div class="row mb-3">
        <label for="inputCodiceFiscale" class="col-sm-3 col-form-label">Codice Fiscale</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" name="codiceFiscale" id="inputCodiceFiscale" maxlength="16" placeholder="Codice Fiscale" required pattern="[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]">
            <div class="invalid-feedback" id="codiceFiscaleFeedback">
                Please enter a valid Codice Fiscale (e.g., ABCDEF12G34H567J).
            </div>
        </div>
    </div>

    <div class="row mb-3">
        <label for="inputNome" class="col-sm-3 col-form-label">Nome</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" name="nome" id="inputNome" placeholder="Nome" required>
        </div>
    </div>

    <div class="row mb-3">
        <label for="inputCognome" class="col-sm-3 col-form-label">Cognome</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" name="cognome" id="inputCognome" placeholder="Cognome" required>
        </div>
    </div>

    <fieldset class="row mb-3">
        <legend class="col-form-label col-sm-3 pt-0">Sesso</legend>
        <div class="col-sm-9">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="sesso" id="sessoM" value="m" checked required>
                <label class="form-check-label" for="sessoM">M</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="sesso" id="sessoF" value="f" required>
                <label class="form-check-label" for="sessoF">F</label>
            </div>
        </div>
    </fieldset>

    <div class="row mb-3">
        <label for="inputDataNascita" class="col-sm-3 col-form-label">Data di Nascita</label>
        <div class="col-sm-9">
            <input type="date" class="form-control" name="dataNascita" id="inputDataNascita" required>
        </div>
    </div>

    <div class="row mb-3">
        <label for="inputEmail" class="col-sm-3 col-form-label">Email</label>
        <div class="col-sm-9">
            <input type="email" class="form-control" name="email" id="inputEmail" placeholder="Email" required>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Aggiungi <%= request.getParameter("personType") %></button>
</form>
<%
    }
} else {
%>
    <p>Invalid step parameter. Please go back and try again.</p>
<%
}
%>

    <div class="mandatory-fields">*Tutti i campi sono obbligatori</div>
    <div class="back-button my-4">
        <a href="<%= request.getContextPath() %>/welcome.jsp" class="btn btn-secondary">Torna alla Menu</a>
    </div>
</div>
<script>
    function validateCodiceFiscale() {
        var codiceFiscale = document.getElementById('inputCodiceFiscale').value;
        var regex = /^[A-Za-z]{6}[0-9]{2}[A-Za-z][0-9]{2}[A-Za-z][0-9]{3}[A-Za-z]$/;
        if (!regex.test(codiceFiscale)) {
            document.getElementById('codiceFiscaleFeedback').style.display = 'block';
            return false;
        }
        return true;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>
