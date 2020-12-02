package dbHandler;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class DBServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("index.html");
        PrintWriter out = response.getWriter();
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            Connection con = ds.getConnection();
            model.Quiz quiz = new model.Quiz();
            HttpSession session = request.getSession(true);
            session.setAttribute("quiz", quiz);
            String query = "SELECT * FROM question";
            PreparedStatement st = con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                quiz.addQuestion(rs.getInt("qid"), rs.getString("question"));
            }
            query = "SELECT * FROM alternatives";
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                quiz.addAlternative(rs.getInt("question"), rs.getInt("aid"), rs.getString("alternative"), rs.getBoolean("correct"));
            }
            out.println(quiz.getQuiz());
            st.close();
            rd.forward(request, response);
        } catch(Exception e){
            out.println(e.getMessage());
        }
    }
}
