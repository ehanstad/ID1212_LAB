package dbHandler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class QuestionDAO {

    private EntityManager em;
    private EntityManagerFactory emf;

    public QuestionDAO(){
        emf = Persistence.createEntityManagerFactory("LAB3PU");
        em = emf.createEntityManager();
    }
    
    public void addQuestion(Question question) {
        em.getTransaction().begin();
        
        em.persist(question);
        em.getTransaction().commit();
    }

    public int getQid() {
        return em.createQuery("SELECT MAX(q.qid) FROM Question q", Integer.class)
                .getSingleResult();
    }
}