package B6.dao;

import config.Database;
import B6.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO{

    @Override
    public Account login(String username, String password) {
        try(Connection cn = Database.getConnection()){
            String query = "SELECT * FROM java_database_tg.accounts "+
                            "WHERE accounts.username = ? AND accounts.password = ?";
            Object[] params = {username, password};
            PreparedStatement st = Database.createPreparedStateMent(query,cn,params);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                Integer aId = rs.getInt(1);
                String rsUsername = rs.getString(2);
                String rsPassword = rs.getString(3);
                return new Account(aId,rsUsername,rsPassword);
            }
            else{
                System.err.println("No account found");
                return null;
            }
        }
        catch (SQLException e){
            System.err.println("Connection failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean register(String username, String password) {
        try(Connection cn = Database.getConnection()){
            String query = "INSERT INTO java_database_tg.accounts (username, password) VALUES (?,?)";
            Object[] params = {username, password};
            PreparedStatement st = Database.createPreparedStateMent(query,cn,params);
            int rs = st.executeUpdate();
            if(rs != 1){
                return false;
            }
            return true;
        }
        catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return false;
    }
}
