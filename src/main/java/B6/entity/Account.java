package B6.entity;

public class Account {
    private Integer aId;
    private String username;
    private String password;

    public Account(Integer aId, String username, String password) {
        this.aId = aId;
        this.username = username;
        this.password = password;
    }


    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
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

    @Override
    public String toString(){
        return "Account [ID: " + this.aId +
                ", username: " + this.username +
                ", password: " + this.password + "]";
    }
}
