package model;

import aux.exceptions.LoginException;

import java.util.List;

/**
 * Created by danilopinotti on 29/11/15.
 */
public interface UserDAO {
    void create(User user);
    void delete(User user);
    void update(User user);
    void updatePassword(User user);
    void updateLastAccess(User user);
    User findById(Integer id);
    List<User> findByName(String name);
    List<User> all();
    User autenticate(String email, String password) throws LoginException;
}
