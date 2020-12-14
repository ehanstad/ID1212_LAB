package com.id1212.lab3maven.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        com.id1212.lab3maven.model.Quiz quiz = (com.id1212.lab3maven.model.Quiz)session.getAttribute("quiz");
        String altString = quiz.getAlternatives();
        String[] alts = altString.split("#");
        for(int i=0; i<alts.length; i++){
            if(request.getParameter(alts[i])!=null){
                quiz.setAnswered(Integer.parseInt(alts[i+1]),alts[i]);
            }
        }
        out.println("<h1>Your score!</h1>");
        out.println("<h2>You got "+quiz.countPoints()+" points!</h2>");
    }

}
