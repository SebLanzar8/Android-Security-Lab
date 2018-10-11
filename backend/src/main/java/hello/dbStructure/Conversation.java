package hello.dbStructure;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer idSender;

    private Integer idReceiver;

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    private String nameReceiver;

    private String nameSender;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSender() {
        return idSender;
    }

    public void setIdSender(Integer idSender) {
        this.idSender = idSender;
    }

    public Integer getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(Integer idReceiver) {
        this.idReceiver = idReceiver;
    }
}
