package dbHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Persistence;

public class UserDAO{

    private EntityManager em;
    private EntityManagerFactory emf;
    //@PersistenceContext
    public UserDAO(){
        emf = Persistence.createEntityManagerFactory("LAB3PU");
        em = emf.createEntityManager();
    }
    
    //@Override
    public void addUser(User user) {
        System.out.println("*******");
        em.getTransaction().begin();
        
        em.persist(user);
        em.getTransaction().commit();
    }

    //@Override
    public User getUser(String uname) {
        return em.find(User.class, uname);
    }
}