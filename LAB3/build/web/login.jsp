<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="./LoginServlet" method="post">
            <input type="text" name="uname" placeholder="Username" /> <br/><br/>
            <input type="password" name="password" placeholder="Password" /> <br/><br/>
            <button type="submit" name="button">Enter</button>
          </form> <br/><br/>
        <a href="./register.jsp">create new account</a>
    </body>
</html>
