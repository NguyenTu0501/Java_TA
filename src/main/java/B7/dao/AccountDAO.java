package B7.dao;

import B7.entity.Account;
import config.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO {
    public static ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        try(Connection connection = Database.getConnection()){
            String query = "SELECT * FROM java_database_tg.accounts";
            Object[] params = {};
            PreparedStatement statement = Database
                    .createPreparedStateMent(query,connection,params);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int accountId = rs.getInt(1);
                String username = rs.getString(2);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);

                accounts.add(new Account(accountId,username,firstName,lastName));
            }
            return accounts;
        }
        catch (SQLException ex) {
            System.err.println("Database exception: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean updateAccount(Account account){
        try(Connection connection = Database.getConnection()){
            String query1 = "SELECT 1 FROM java_database_tg.accounts " +
                            "WHERE accounts.id = ?";
            Object[] params1 = {account.getAccountId()};
            PreparedStatement statement1 = Database
                    .createPreparedStateMent(query1,connection,params1);
            ResultSet rs1 = statement1.executeQuery();
            if(!rs1.next()){
                System.out.println("Account not found: " + account.getAccountId());
                return false;
            }

            String query2 = "UPDATE java_database_tg.accounts SET " +
                            "username = ?, password = ?, first_name = ?, last_name = ? " +
                            "WHERE id = ?";

            String query3 = "UPDATE java_database_tg.accounts SET " +
                            "username = ?, first_name = ?, last_name = ? " +
                            "WHERE id = ?";

            Object[] params2 = {
                    account.getUsername(),
                    account.getPassword(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getAccountId()
            };
            Object[] params3 = {
                    account.getUsername(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getAccountId()
            };
            PreparedStatement statement2 = Database
                    .createPreparedStateMent(
                            account.getPassword().isEmpty() ? query3 : query2,
                            connection,
                            account.getPassword().isEmpty() ? params3 : params2);
            int rs2 = statement2.executeUpdate();
            return rs2 != 0;
        }
        catch (SQLException ex){
            System.err.println("Database exception: " + ex.getMessage());
            return false;
        }
    }

    public static boolean deleteAccount(int accountId){
        try(Connection connection = Database.getConnection()){
            String query1 = "SELECT 1 FROM java_database_tg.accounts " +
                            "WHERE id = ?";
            Object[] params = {accountId};
            PreparedStatement statement1 = Database
                    .createPreparedStateMent(query1,connection, params);
            ResultSet rs1 = statement1.executeQuery();
            if(!rs1.next()){
                System.out.println("Account not found: " + accountId);
                return false;
            }

            String query2 = "DELETE FROM java_database_tg.accounts " +
                            "WHERE id = ?";
            PreparedStatement statement2 = Database
                    .createPreparedStateMent(query2,connection,params);
            int rs2 = statement2.executeUpdate();
            return rs2 != 0;
        }
        catch (SQLException ex){
            System.err.println("Database exception: " + ex.getMessage());
            return false;
        }
    }
}
