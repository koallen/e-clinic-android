package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZWL on 12/9/16.
 */
public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private TextView _chatText;
    private List<ChatMessage> _chatMessageList = new ArrayList<ChatMessage>();
    private Context _context;

//    @Override
//    public void add(ChatMessage obj){
//        _chatMessageList.add(obj);
//        super.add(obj);
//    }

    // constructor
    public ChatAdapter(Context context, List<ChatMessage> list){
        super(context, R.layout.chat_bubble, list);
        _context = context;
        _chatMessageList = list;
    }

    @Override
    public int getCount(){
        return this._chatMessageList.size();
    }

    @Override
    public ChatMessage getItem(int index){
        return this._chatMessageList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChatMessage _chatMessageObj = _chatMessageList.get(position);
//        View _row = convertView;
//        LayoutInflater _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if(convertView == null){
//            _row = _inflater.inflate(R.layout.chat_bubble, null);
//        }

//        RelativeLayout _bubble = (RelativeLayout) _row.findViewById(R.id.bubble);
//        LinearLayout _bubbleContainer = (LinearLayout) _row.findViewById(R.id.chatBubbleContainer);
//        TextView _msg = (TextView) _row.findViewById(R.id.txtMsg);
//        _msg.setText(_chatMessageObj._message);

//        if(_chatMessageObj._mine){
//            _bubble.setBackgroundResource(R.drawable.msg_bg);
//            _bubbleContainer.setGravity(Gravity.RIGHT);
//            _row = _inflater.inflate(R.layout.chat_bubble, parent, false);
//        }
//        else{
//            _bubble.setBackgroundResource(R.drawable.msg_bg);
//            _bubbleContainer.setGravity(Gravity.LEFT);
//            _row = _inflater.inflate(R.layout.chat_bubble, parent, true);
//        }

        if (_chatMessageObj._mine) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_bubble, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.txtMsg);
            textView.setText(_chatMessageObj._message);

        }
        else{
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_bubble, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.txtMsg);
            textView.setText(_chatMessageObj._message);
        }

        TextView msgInfo = (TextView) convertView.findViewById(R.id.msgInfo);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM");
        String time = format.format(date);
        msgInfo.setText(time);

        convertView.findViewById(R.id.chatBubbleContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
            }
        });



        return convertView;
    }
}
