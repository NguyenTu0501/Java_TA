package B7.entity;

public class Account {
    private int accountId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Account() {
    }

    public Account(int accountId, String username, String firstName, String lastName) {
        this.accountId = accountId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Account(int accountId, String username, String password, String firstName, String lastName) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
