<%-- 
    Document   : admin
    Created on : 01-Dec-2020, 20:29:43
    Author     : L V
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
        <form action="./AdminServlet" method="post">
            <h3>Add a question:</h3>
            <input type="text" name="question" placeholder="Question">
            <br><br>
            <input type="checkbox" name="alt1answer">
            <input type="text" name="alt1" placeholder="Alternative 1">
            <br><br>
            <input type="checkbox" name="alt2answer">
            <input type="text" name="alt2" placeholder="Alternative 2">
            <br><br>
            <input type="checkbox" name="alt3nswer">
            <input type="text" name="alt3" placeholder="Alternative 3">
            <br><br>
            <input type="checkbox" name="alt4answer">
            <input type="text" name="alt4" placeholder="Alternative 4">
            <br><br>    
            <button type="submit" value="submit">Submit</button>
        </form>
    </body>
</html>
