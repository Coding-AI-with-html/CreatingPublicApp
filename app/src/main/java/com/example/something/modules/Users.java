package modules;

public class Users {

    String user_uid;
    private long phone_num;
    private String email;
    private String password;
    private String username;

    public Users(String user_uid, long phone_num, String email, String password) {
        this.user_uid = user_uid;
        this.phone_num = phone_num;
        this.email = email;
        this.password = password;
        this.username = username;
    }


    public Users() {
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public long getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(long phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_uid='" + user_uid + '\'' +
                ", phone_num=" + phone_num +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
