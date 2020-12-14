package com.id1212.lab3maven.controller;
import com.id1212.lab3maven.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.net.URLEncoder;
public class LoginServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
          throws ServletException, IOException {
      //String message = "hello";
      //response.sendRedirect("login.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
      response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch(Exception e) {
            e.printStackTrace();
        }
      /*short admin = 1;
      User user = new User("root", "root@me.se", "kanye", admin);
      
      EntityManagerFactory emf=Persistence.createEntityManagerFactory("pu"); 
      EntityManager em = emf.createEntityManager();
      
      em.getTransaction().begin();
      em.persist(user);
      em.getTransaction().commit();
      emf.close();  
      em.close(); */
  }
  
  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
