package hello.dbStructure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer idConversation;

    private Integer idSender;
    public Integer getIdSender() {
        return idSender;
    }

    public void setIdSender(Integer idSender) {
        this.idSender = idSender;
    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
