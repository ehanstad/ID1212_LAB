package dbHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AlternativesDAO {
    
    private EntityManager em;
    private EntityManagerFactory emf;
    
    public AlternativesDAO(){
        emf = Persistence.createEntityManagerFactory("LAB3PU");
        em = emf.createEntityManager();
    }
    
    public void addAlternative(Alternatives alt) {
        em.getTransaction().begin();
        
        em.persist(alt);
        em.getTransaction().commit();
    }
    
    public int getAid() {
        return em.createQuery("SELECT MAX(a.aid) FROM Alternatives a", Integer.class)
                .getSingleResult();
    }
}
