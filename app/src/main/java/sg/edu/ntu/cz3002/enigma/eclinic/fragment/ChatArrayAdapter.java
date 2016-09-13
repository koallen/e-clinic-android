package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.cz3002.enigma.eclinic.R;


/**
 * Created by ZWL on 12/9/16.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {
    private TextView _chatText;
    private List<ChatMessage> _chatMessageList = new ArrayList<ChatMessage>();
    private Context _context;

    @Override
    public void add(ChatMessage obj){
        _chatMessageList.add(obj);
        super.add(obj);
    }

    // constructor
    public ChatArrayAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
        this._context = context;
    }

    public int getCount(){
        return this._chatMessageList.size();
    }

    public ChatMessage getItem(int index){
        return this._chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ChatMessage _chatMessageObj = getItem(position);
        View _row = convertView;
        LayoutInflater _inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            _row = _inflater.inflate(R.layout.chat_bubble, null);
        }

        TextView _msg = (TextView) _row.findViewById(R.id.txtMsg);
        _msg.setText(_chatMessageObj._message);

        RelativeLayout _bubble = (RelativeLayout) _row.findViewById(R.id.bubble);
        LinearLayout _bubbleContainer = (LinearLayout) _row.findViewById(R.id.chatBubbleContainer);


        if(_chatMessageObj._mine){
            _bubble.setBackgroundResource(R.drawable.msg_bg);
            _bubbleContainer.setGravity(Gravity.RIGHT);
            _row = _inflater.inflate(R.layout.fragment_chat, parent, false);
        }
        else{
            _bubble.setBackgroundResource(R.drawable.msg_bg);
            _bubbleContainer.setGravity(Gravity.LEFT);
            _row = _inflater.inflate(R.layout.fragment_chat, parent, true);
        }
        return _row;
    }
}
