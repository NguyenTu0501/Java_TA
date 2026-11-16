package B6.dao;

import B6.entity.Account;

public interface AccountDAO {
    Account login(String username, String password);
    boolean register(String username, String password);
}

