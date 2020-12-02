<jsp:useBean class="model.Quiz" id="quiz" scope="session"></jsp:useBean>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>
    </head>
    <body>
        <form method="post" action="./QuizServlet">
            <%
            String[] question = quiz.getQuiz().split("\n");
            for(int i=0; i<question.length; i++){
                String[] alternatives = question[i].split("#");
                %>
                <h2><%out.println(alternatives[0]);%></h2>
                <%
                for(int j=1; j<alternatives.length; j++){
                    %>
                    <input type="checkbox" 
                        id=<%out.println(i+""+j);%>     
                        name=<%out.println(alternatives[j]);%>
                    >
                    <label><%out.println(alternatives[j]);%></label><br>     
                    <%
                }
            }
            %>
        <br>
        <input type="submit" value="Submit">
      </form>
    </body>
</html>
