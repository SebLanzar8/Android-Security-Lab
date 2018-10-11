package hello.repositories;

public interface UserRepositoryCustom {
    int checkLogin(String username, String password);
    int updateToken(String token, int id);
}
