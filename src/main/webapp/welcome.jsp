<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<%@ page import="main.menu.GestioneMenu" %>
<%@ page import="main.menu.OpzioneMenu" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Navbar Example</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-12 mt-3">
                <%-- Java code to get current date and user profile --%>
                <%
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    String sCurrentDate = sdf.format(new java.util.Date());
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

                    // prendo ArrayLists e set per ogni profilo
                    GestioneMenu gestioneMenu = new GestioneMenu();
                    List<OpzioneMenu> opzioniAmministrativo = gestioneMenu.getOpzioniAmministrativo();
                    List<OpzioneMenu> opzioniDocente = gestioneMenu.getOpzioniDocente();
                    List<OpzioneMenu> opzioniAllievo = gestioneMenu.getOpzioniAllievo();
                    Set<String> sectionsAmministrativo= gestioneMenu.getSectionsAmministrativo();
                    Set<String> sectionsDocente= gestioneMenu.getSectionsDocente();
                    Set<String> sectionsAllievo= gestioneMenu.getSectionsAllievo();
                %>
                <div class='alert alert-info' role='alert'>
                    Data di oggi: <%= sCurrentDate %>
                </div>
                <div class='alert alert-info' role='alert'>
                    Utente corrente: <%= currentUser %>
                    Profilo: <%= currentProfile %>
                </div>
            </div>
        </div>
        <div class="row">
            <nav class="navbar navbar-expand-lg navbar-light bg-light col-12">
                <a class="navbar-brand" href="#">Scuola</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
                        aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavDropdown">
                    <ul class="navbar-nav">
                    <% if(currentProfile.equals("amministrativo")) { %>
                        <!-- ***************** Amministrativo ***************** -->
                        <% for (String section : sectionsAmministrativo) { %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink<%= section %>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <%= section %>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink<%= section %>">
                                    <% for (OpzioneMenu menuOption : opzioniAmministrativo) {
                                        if (section.equals(menuOption.getDropdownSection())) { %>
                                            <a class="dropdown-item" href="<%= menuOption.getMenuHref() %>"><%= menuOption.getMenuOption() %></a>
                                    <%  }
                                       } %>
                                </div>
                            </li>
                        <% } %>
                    <% } else if (currentProfile.equals("docente")) { %>
                        <!-- ***************** Docente ***************** -->
                        <% for (String section : sectionsDocente) { %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink<%= section %>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <%= section %>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink<%= section %>">
                                    <% for (OpzioneMenu menuOption : opzioniDocente) {
                                        if (section.equals(menuOption.getDropdownSection())) { %>
                                            <a class="dropdown-item" href="<%= menuOption.getMenuHref() %>"><%= menuOption.getMenuOption() %></a>
                                    <%  }
                                       } %>
                                </div>
                            </li>
                        <% } %>
                    <% } else { %>
                        <!-- ***************** Allievo ***************** -->
                        <% for (String section : sectionsAllievo) { %>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink<%= section %>" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <%= section %>
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink<%= section %>">
                                    <% for (OpzioneMenu menuOption : opzioniAllievo) {
                                        if (section.equals(menuOption.getDropdownSection())) { %>
                                            <a class="dropdown-item" href="<%= menuOption.getMenuHref() %>"><%= menuOption.getMenuOption() %></a>
                                    <%  }
                                       } %>
                                </div>
                            </li>
                        <% } %>
                    <% } %>
                    </ul>
                    <!-- Logout Button -->
                    <form class="form-inline ml-auto" action="ScuolaServlet" method="get">
                        <input type="hidden" name="action" value="logout">
                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
                    </form>
                </div>
            </nav>
        </div>
    </div>
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
