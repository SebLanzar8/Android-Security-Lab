package hello.repositories;

import hello.dbStructure.Conversation;
import hello.dbStructure.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {

    @Query(value="SELECT id FROM conversation WHERE id_receiver=:idAccount Or id_sender=:idAccount",
            nativeQuery=true)
    Collection<Integer> getMyConversations(@Param("idAccount") int idAccount);

    @Query(value="SELECT * FROM conversation WHERE id=:idConversation",
            nativeQuery=true)
    Conversation getConversations(@Param("idConversation") int idConversation);

    @Query(value="SELECT * FROM conversation WHERE (id_sender=:id1 OR id_sender=:id1) AND (id_receiver=:id2 OR id_receiver=:id2)",
            nativeQuery=true)
    Conversation checkDoubleConv(@Param("id1") int id1, @Param("id2") int id2);
}
