package damnvuln.exercise.lab.vulntestlab.generalPurpouse;

public class User {
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String token;

    public String getPassword() {        return password;    }

    public void setPassword(String password) {        this.password = password;    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

