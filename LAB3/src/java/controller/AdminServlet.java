package controller;

import dbHandler.Alternatives;
import dbHandler.Question;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

public class AdminServlet extends HttpServlet {
    
    private dbHandler.QuestionDAO questionDao;
    private dbHandler.AlternativesDAO altDao; 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = request.getRequestDispatcher("index.html");
        try {
            
            String question = request.getParameter("question");
            
            String alt[] = new String[4];
            alt[0] = request.getParameter("alt1");
            alt[1] = request.getParameter("alt2");
            alt[2] = request.getParameter("alt3");
            alt[3] = request.getParameter("alt4");
      
            short altans[] = new short[4];
            altans[0] = 0;
            altans[1] = 0;
            altans[2] = 0;
            altans[3] = 0;
            if(request.getParameter("alt1answer") != null){
                altans[0] = 1;
            }
            if(request.getParameter("alt2answer") != null){
                altans[1] = 1;
            }
            if(request.getParameter("alt3answer") != null){
                altans[2] = 1;
            }
            if(request.getParameter("alt4answer") != null){
                altans[3] = 1;
            }
            
            this.questionDao = new dbHandler.QuestionDAO();
            this.altDao = new dbHandler.AlternativesDAO();
            Question newQuestion = new Question();
            newQuestion.setQuestion(question);
            int qid = this.questionDao.getQid();
            newQuestion.setQid(qid + 1);
            this.questionDao.addQuestion(newQuestion);
            int aid = this.altDao.getAid();
            
            for(int i=0; i<alt.length; i++) {
                Alternatives newAlt = new Alternatives();
                newAlt.setAid(++aid);
                newAlt.setAlternative(alt[i]);
                newAlt.setCorrect(altans[i]);
                newAlt.setQuestion(newQuestion);
                this.altDao.addAlternative(newAlt);
            }
            
           rd.forward(request, response);
        } catch(Exception e){
            out.println(e.getMessage());
        }
    }
}
