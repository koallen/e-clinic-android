package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

/**
 * Chat message
 */
public class ChatMessage {
    private boolean _mine;
    private String _message;
    private String _sender;
    private String _receiver;

    public ChatMessage(boolean mine, String message, String sender, String receiver) {
        super();
        this._mine = mine;
        this._message = message;
        this._receiver = receiver;
        this._sender = sender;
    }

    public void setMine(boolean mine) {
        this._mine = mine;
    }

    public boolean getMine() {
        return this._mine;
    }

    public void setSender(String sender) {
        this._sender = sender;
    }

    public String getSender() {
        return this._sender;
    }

    public String getMessage() {
        return this._message;
    }
}
