package dbHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAO{

    private EntityManager em;
    private EntityManagerFactory emf;

    public UserDAO(){
        emf = Persistence.createEntityManagerFactory("LAB3PU");
        em = emf.createEntityManager();
    }

    public void addUser(User user) {
        em.getTransaction().begin();
        
        em.persist(user);
        em.getTransaction().commit();
    }

    public User getUser(String uname) {
        return em.find(User.class, uname);
    }
}