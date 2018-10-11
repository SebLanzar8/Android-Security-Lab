package damnvuln.exercise.lab.vulntestlab.generalPurpouse;

public class Message {
    private String username;
    private String id;
    private String  message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }
    private int idSender;
    public int getIdSender() {
        return idSender;
    }

    public String getIdSenderString(){
        return String.valueOf(this.idSender);
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
