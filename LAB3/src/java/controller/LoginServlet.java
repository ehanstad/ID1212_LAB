package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.io.IOException;
import dbHandler.User;
import dbHandler.UserDAOLocal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name="LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
   @EJB
   private UserDAOLocal userDao;
    
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
      PrintWriter out = response.getWriter();
      short admin = 1;
      User user = new User("root", "root@me.se", "kanye", admin);
      out.println(user);
      out.println(userDao);
      //userDao.addUser(user);
      //request.getRequestDispatcher("login.jsp").forward(request, response);
  }
}
