package controller;

import dbHandler.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    
    private dbHandler.UserDAO userDao; 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

        String email = request.getParameter("email");
        String uname = request.getParameter("uname");
        String password = request.getParameter("password");
        Short admin = 0;
        this.userDao = new dbHandler.UserDAO();
        User newUser = new User();
        newUser.setUname(uname);
        newUser.setEpost(email);
        newUser.setAdmin(admin);
        newUser.setPassword(password);
        this.userDao.addUser(newUser);
        request.getRequestDispatcher("quiz.jsp").forward(request, response);
      } catch(Exception e) {
          e.printStackTrace();
      }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
