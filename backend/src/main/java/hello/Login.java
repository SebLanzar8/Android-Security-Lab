package hello;

import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Login{
    private final String password;
    private final String username;
    private String token = "invalid";

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public Login( String username,String password){
        this.password = password;
        this.username = username;
    }
    @Autowired
    public boolean checkLogin(UserRepository rc) {

        int id = rc.checkLogin(username, password);

        if (id != -1) {
            this.token = generateToken();
            rc.updateToken(this.token, id);
            return true;
        } else {
            return false;
        }
    }

    private String generateToken(){

        int length = 36;

        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return builder.toString();

    }

    public String getToken() {
        return token;
    }
}