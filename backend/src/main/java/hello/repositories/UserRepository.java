package hello.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import hello.dbStructure.User;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Integer>, UserRepositoryCustom {

    @Query(value="SELECT * FROM user LIMIT 10",
            nativeQuery=true)
    Collection<User> findFirstTen();

    @Query(value="SELECT COUNT(id) FROM user WHERE username=:pUsername AND password=:pPassword ",
            nativeQuery=true)
    int checkPassword(@Param("pUsername") String pUsername,@Param("pPassword") String  pPassword);

    @Query(value="SELECT id FROM user WHERE username=:pSearch OR token=:pSearch",
            nativeQuery = true)
    int getId(@Param("pSearch") String pSearch);

    @Query(value="SELECT * FROM user WHERE id=:pId",
            nativeQuery = true)
    User getUserFromId(@Param("pId") int pId);


}
