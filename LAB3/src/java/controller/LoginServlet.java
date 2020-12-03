package controller;
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
import dbHandler.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;

public class LoginServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
      PrintWriter out = response.getWriter();
      EntityManagerFactory emf=Persistence.createEntityManagerFactory("user"); 
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
      
      short admin = 1;
      User user = new User("root", "root@me.se", "kanye", admin);
      
      em.merge(user);
      em.getTransaction().commit();
      emf.close();  
      em.close(); 
      out.println(user.getUname());
  }
}
