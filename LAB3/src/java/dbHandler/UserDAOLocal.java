package dbHandler;

import javax.ejb.Local;

@Local
public interface UserDAOLocal {

    void addUser(User user);
    User getUser(String uname);
    
}
