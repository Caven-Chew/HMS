package users;

public abstract class User {
    protected String userId;
    protected String password;
    protected String name;
    protected String role;

    public boolean login(String userId, String password) {
        //Validation
        if(userId.equals(this.userId) && password.equals(this.password)) {
            //Successful Login
            return true;
        }
        //Unsuccessful Login
        return false;
    }

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public abstract void showMainPage();
}
