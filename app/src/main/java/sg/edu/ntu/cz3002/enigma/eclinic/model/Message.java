package sg.edu.ntu.cz3002.enigma.eclinic.model;

/**
 * Created by ZWL on 20/9/16.
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

}
