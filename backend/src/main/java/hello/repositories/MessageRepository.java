package hello.repositories;


import hello.dbStructure.Message;
import hello.dbStructure.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query(value="SELECT * FROM message WHERE id_conversation=:idConversation",
            nativeQuery=true)
    Collection<Message> getMyMessage(@Param("idConversation") int idConversation);



}
