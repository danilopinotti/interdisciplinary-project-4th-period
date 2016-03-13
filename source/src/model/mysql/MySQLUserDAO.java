package model.mysql;

import aux.Encrypts;
import aux.exceptions.LoginException;
import model.*;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopinotti on 29/11/15.
 */
public class MySQLUserDAO extends MySQLMainDatabase implements UserDAO {
    @Override
    public void create(User user) {
        openDB();
        try{
            pstm = conn.prepareStatement("INSERT INTO users " +
                    "VALUES(NULL, ? ,?, ? ,NOW() ,NOW(), NULL)");
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, Encrypts.SHA1(user.getPassword()));
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    @Override
    public void delete(User user) {
        openDB();
        try{
            pstm = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            pstm.setInt(1, user.getId());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    @Override
    public void update(User user) {
        openDB();
        try{
                pstm = conn.prepareStatement("UPDATE users " +
                        "SET name = ?, email = ?, last_update = NOW()" +
                        "WHERE id = ?");
                pstm.setString(1, user.getName());
                pstm.setString(2, user.getEmail());
                pstm.setInt(3, user.getId());
                pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    @Override
    public void updatePassword(User user) {
        openDB();
        try{
            pstm = conn.prepareStatement("UPDATE users " +
                    "SET password = ?, last_update = NOW() " +
                    "WHERE id = ?");
            pstm.setString(1, Encrypts.SHA1(user.getPassword()));
            pstm.setInt(2, user.getId());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    @Override
    public void updateLastAccess(User user){
        openDB();
        try{
            pstm = conn.prepareStatement("UPDATE users SET " +
                    "last_access = NOW() " +
                    "WHERE id = ?");
            pstm.setInt(1, user.getId());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    @Override
    public User findById(Integer id) {
        if(id == null)
            return null;

        User result = null;
        openDB();
        try{
            pstm = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if(rs.next())
                result  = new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update"),
                        rs.getTimestamp("last_access")
                );
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return result;
    }

    @Override
    public List<User> findByName(String name) {
        List<User> result = new ArrayList<>();
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM users WHERE name LIKE ? ORDER BY id ASC");
            pstm.setString(1, "%"+name+"%");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                result.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update"),
                        rs.getTimestamp("last_access")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }

    @Override
    public List<User> all() {
        List<User> result = new ArrayList<>();
        openDB();

        try{
            pstm = conn.prepareStatement("SELECT * FROM users ORDER BY id ASC");
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                result.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("last_update"),
                        rs.getTimestamp("last_access")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeDB();
        }
        return result;
    }

    @Override
    public User autenticate(String email, String password) {
        User result = null;
        openDB();
        try {
            pstm = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            pstm.setString(1, email);
            ResultSet account = pstm.executeQuery();
            if(!account.next())
                throw new LoginException("Conta inexistente");
            else if(!account.getString("password").equals(Encrypts.SHA1(password)))
                throw new LoginException("Login ou senha inválidos");
            else{
                result = new User(account.getInt("id"),
                        account.getString("name"),
                        account.getString("email"),
                        account.getString("password"),
                        account.getTimestamp("created_at"),
                        account.getTimestamp("last_update"),
                        account.getTimestamp("last_access")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }

        return result;
    }
}
