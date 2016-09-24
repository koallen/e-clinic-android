package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

/**
 * Created by ZWL on 20/9/16.
 */
public class ChatListElement {
    private String _msgFrom;
    private String _msgShort;
    public ChatListElement(String userName, String msgShort){
        _msgFrom = userName;
        _msgShort = msgShort;
    }
    public String getMsgFrom(){
        return _msgFrom;
    }
    public void setMsgFrom(String msgFrom){
        _msgFrom = msgFrom;
    }
    public String getMsgShort(){
        return _msgShort;
    }
    public void setMsgShort(String msgShort){
        _msgShort = msgShort;
    }

}
