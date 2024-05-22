 <!DOCTYPE html>
 <html lang="en">
 <head>
     <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
             <%
                 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                 String sCurrentDate = sdf.format(new java.util.Date());
                 String currentUser = (String) session.getAttribute("currentUser");
             %>
             <div class='alert alert-info' role='alert'>
                 Data di oggi: <%= sCurrentDate %>
             </div>
             <div class='alert alert-info' role='alert'>
                 Utente corrente: <%= currentUser %>
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
                     <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLinkAllievi"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             Operazioni con Allievi
                         </a>
                         <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLinkAllievi">
                             <a class="dropdown-item" href="inserimentoPersona.html?personType=allievo">Aggiungi Allievo</a>
                             <a class="dropdown-item" href="inputCf.html?personType=allievo">Cerca/Cambia stato Allievo</a>
                             <a class="dropdown-item" href="ScuolaServlet?action=stampaLista&personType=allievo">Stampa lista di tutti allievi</a>
                             <a class="dropdown-item" href="ScuolaServlet?method=get&action=scegliClasse">Stampa allievi di una Classe</a>
                         </div>
                     </li>
                     <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLinkDocenti"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             Operazioni con Docenti
                         </a>
                         <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLinkDocenti">
                             <a class="dropdown-item" href="inserimentoPersona.html?personType=docente">Aggiungi docente</a>
                             <a class="dropdown-item" href="inputCf.html?personType=docente">Cerca/Cambia stato Docente</a>
                             <a class="dropdown-item" href="ScuolaServlet?action=stampaLista&personType=docente">Stampa lista Docenti</a>
                         </div>
                     </li>
                     <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLinkAmministrativi"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                             Operazioni con Amministrativi
                         </a>
                         <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLinkAmministrativi">
                             <a class="dropdown-item" href="inserimentoPersona.html?personType=amministrativo">Aggiungi Amministrativo</a>
                             <a class="dropdown-item" href="inputCf.html?personType=amministrativo">Cerca/Cambia stato Amministrativo</a>
                             <a class="dropdown-item" href="ScuolaServlet?action=stampaLista&personType=amministrativo">Stampa lista amministrativi</a>
                         </div>
                     </li>
                 </ul>
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