<jsp:useBean class="model.Quiz" id="quiz" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    
    <body>
    <h1>Register</h1>
    <form action="./RegisterServlet" method="post">
      <input type="email" name="email" placeholder="Email" /> <br/><br/>
      <input type="text" name="uname" placeholder="Username" /> <br/><br/>
      <input type="password" name="password" placeholder="Password" /> <br/><br/>
      <button type="submit" name="button">Enter</button>
    </form> <br/><br/>
    <a href="./login.jsp">already have an account</a>

    </body>
</html>
