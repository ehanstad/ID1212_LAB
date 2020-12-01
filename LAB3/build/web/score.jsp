<jsp:useBean class="model.Quiz" id="quiz" scope="session"></jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Score</title>
    </head>
    <body>
        <h1>Your score!</h1>
        <h2>You got <%quiz.countPoints();%> points!</h2>
    </body>
</html>
