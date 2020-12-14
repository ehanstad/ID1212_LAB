package com.id1212.lab3maven;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import com.id1212.lab3maven.User;

public class App {
    
    public static void main(String args[]) {

      short admin = 1;
      User user = new User("root", "root@me.se", "kanye", admin);
      
      EntityManagerFactory emf=Persistence.createEntityManagerFactory("pu"); 
      EntityManager em = emf.createEntityManager();
      
      em.getTransaction().begin();
      em.persist(user);
      em.getTransaction().commit();
      emf.close();  
      em.close(); 
    }
}
