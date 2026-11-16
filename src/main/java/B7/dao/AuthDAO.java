package B7.dao;

import B7.entity.Account;
import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    public static boolean register(Account account) {
        try(Connection connection = Database.getConnection()){

            String query1 = "SELECT 1 FROM java_database_tg.accounts " +
                            "WHERE accounts.username = ?";
            Object[] params1 = {account.getUsername()};
            PreparedStatement statement1 = Database
                    .createPreparedStateMent(query1,connection,params1);
            ResultSet rs1 = statement1.executeQuery();
            if(rs1.next()){
                System.out.println("Username has been used: " + account.getUsername());
                return false;
            }

            String query2 = "INSERT INTO java_database_tg.accounts " +
                            "(username, password, first_name, last_name) " +
                            "VALUES (?,?,?,?)";
            Object[] params2 = {
                account.getUsername(),
                account.getPassword(),
                account.getFirstName(),
                account.getLastName()
            };
            PreparedStatement statement2 = Database
                    .createPreparedStateMent(query2,connection,params2);
            int rs2 = statement2.executeUpdate();
            if(rs2 == 0){
                System.out.println("Something went wrong in rs2");
                return false;
            }
            return true;
        }
        catch (SQLException ex){
            System.err.println("Database exception: " + ex.getMessage());
            return false;
        }
    }

    public static Account login(String username, String password) {
        try(Connection connection = Database.getConnection()){
            String query = "SELECT * FROM java_database_tg.accounts " +
                            "WHERE username = ? AND password = ?";
            Object[] params = {username,password};
            PreparedStatement statement = Database
                    .createPreparedStateMent(query,connection,params);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                System.out.println("Account not found: " + username + " | " + password);
                return null;
            }
            return new Account(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
            );
        }
        catch (SQLException ex){
            System.err.println("Database exception: " + ex.getMessage());
            return null;
        }
    }
}
