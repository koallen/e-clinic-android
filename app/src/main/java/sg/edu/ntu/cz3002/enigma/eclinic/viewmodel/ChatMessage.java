package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

/**
 * Chat message
 */
public class ChatMessage {
    private boolean _mine;
    private String _message;
    private String _sender;
    private String _receiver;
    private String _time;

    public ChatMessage(boolean mine, String message, String sender, String receiver, String time) {
        super();
        this._mine = mine;
        this._message = message;
        this._receiver = receiver;
        this._sender = sender;
        this._time = time;
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

    public void setTime(String time) {
        this._time = time;
    }

    public String getTime() {
        return this._time;
    }

    public String getMessage() {
        return this._message;
    }
}
