package sg.edu.ntu.cz3002.enigma.eclinic.model;

/**
 * Message
 */
public class Message {
    private String from_user;
    private String to_user;
    private String message;

    public Message(String from_user, String to_user, String message){
        this.from_user = from_user;
        this.to_user = to_user;
        this.message = message;
    }

    public String getSender() {
        return from_user;
    }

    public String getReceiver() {
        return to_user;
    }

    public String getMessage() {
        return message;
    }
}
