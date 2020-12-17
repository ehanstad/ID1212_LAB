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

  private dbHandler.UserDAO userDao; 
    
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
      try {
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("uname");
        String writtenPassword = request.getParameter("password");
        this.userDao = new dbHandler.UserDAO();
        User user = this.userDao.getUser(uname);
        if(user==null) {
            
            out.println("No user with that name");
            //request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            String password = user.getPassword();
            Short admin = user.getAdmin();
            if(password.equals(writtenPassword)) {
                if(admin==1) {
                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("quiz.jsp").forward(request, response);
                }
            } else {
                out.println("Wrong password");
            }
        }
      } catch(Exception e) {
          e.printStackTrace();
      }
  }
}