<jsp:useBean class="model.Quiz" id="quiz" scope="session"></jsp:useBean>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>
    </head>
    <body>
        <p> FROM DB:
            <%=quiz.getQuiz()%>
        </p>
    <form>
      <h2>Question 1</h2>
      <h3>How are you?</h3>
      <input type="checkbox" id="vehicle1" name="vehicle1" value="Bike">
      <label for="vehicle1"> I have a bike</label><br>
      <input type="checkbox" id="vehicle2" name="vehicle2" value="Car">
      <label for="vehicle2"> I have a car</label><br>
      <input type="checkbox" id="vehicle3" name="vehicle3" value="Boat">
      <label for="vehicle3"> I have a boat</label><br><br>

      <h2>Question 2</h2>
      <h3>?</h3>
      <input type="checkbox" id="vehicle1" name="vehicle1" value="Bike">
      <label for="vehicle1"> I have a bike</label><br>
      <input type="checkbox" id="vehicle2" name="vehicle2" value="Car">
      <label for="vehicle2"> I have a car</label><br>
      <input type="checkbox" id="vehicle3" name="vehicle3" value="Boat">
      <label for="vehicle3"> I have a boat</label><br><br>
      <input type="submit" value="Submit">
    </form>

    </body>
</html>
