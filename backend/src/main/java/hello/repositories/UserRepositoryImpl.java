package hello.repositories;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public int checkLogin(String username, String password) {
        Query q = em.createNativeQuery("SELECT id FROM user WHERE username='"+username+"' AND password='"+password+"'");
        List<Integer> result  = q.getResultList();

        if(result.size() > 0)
            return result.get(0);
        else
            return -1;
    }

    @Override
    @Transactional
    public int updateToken(String token, int id) {
        Query q = em.createNativeQuery("UPDATE user SET token='"+token+"' WHERE id="+id);
        int result  = q.executeUpdate();

        return result;
    }

}
