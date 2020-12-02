/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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

/**
 *
 * @author L V
 */
public class AdminServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        RequestDispatcher rd = request.getRequestDispatcher("index.html");
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/mysql");
            Connection con = ds.getConnection();
            
            String question = request.getParameter("question");
            
            String alt[] = new String[4];
            alt[0] = request.getParameter("alt1");
            alt[1] = request.getParameter("alt2");
            alt[2] = request.getParameter("alt3");
            alt[3] = request.getParameter("alt4");
      
            boolean altans[] = new boolean[4];
            altans[0] = false;
            altans[1] = false;
            altans[2] = false;
            altans[3] = false;
            if(request.getParameter("alt1answer") != null){
                altans[0] = true;
            }
            if(request.getParameter("alt2answer") != null){
                altans[1] = true;
            }
            if(request.getParameter("alt3answer") != null){
                altans[2] = true;
            }
            if(request.getParameter("alt4answer") != null){
                altans[3] = true;
            }
            
            String query1 = "INSERT INTO quiz.question (question) VALUES (?);";
            PreparedStatement st1 = con.prepareStatement(query1);
            st1.setString(1, question);
            st1.execute();
            st1.close();
            
            String query2 = "SELECT qid FROM quiz.question WHERE ? = question";
            PreparedStatement st2 = con.prepareStatement(query2);
            st2.setString(1, question);
            ResultSet rs = st2.executeQuery();
            int qid = 0;
            while (rs.next()) {
                qid = rs.getInt("qid");
            }
            st2.close();
            
            String query3 = "INSERT INTO quiz.alternatives (alternative, correct, question)\n" +                
                    "	VALUES (?, ?, ?);\n";
            for(int i = 0; i < alt.length; i++){
                PreparedStatement st3 = con.prepareStatement(query3);
                st3.setString(1, alt[i]);
                st3.setBoolean(2, altans[i]);
                st3.setInt(3, qid);
                st3.execute();
                st3.close();
            }
            con.close();
            rd.forward(request, response);
        } catch(Exception e){
            out.println(e.getMessage());
        }
    }
}
