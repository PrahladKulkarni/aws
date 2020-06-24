<%@ page isErrorPage="true" %>  
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <style>
        body {
        background-color:azure;
        }
    </style>
    <title>Sample Application - Error</title>
    </head>
    <body>
        <h1>Ooops, we've got a problem:</h1>
        <%=exception.getMessage() %>
    </body>
</html>