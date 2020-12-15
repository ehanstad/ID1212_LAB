package dbHandler;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDAO implements UserDAOLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public User getUser(String uname) {
        return em.find(User.class, uname);
    }
}
