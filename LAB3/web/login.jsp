<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>

<%
  out.println(session.getId());
  out.println(request.getHeader("Cookie"));
%>

    <h2>Welcome</h2>
    <form>
      <input type="email" name="email" placeholder="Email" /> <br/><br/>
      <input type="text" name="uname" placeholder="Username" /> <br/><br/>
      <input type="password" name="password" placeholder="Password" /> <br/><br/>
      <button type="submit" name="button">Enter</button>
    </form>

    </body>
</html>
