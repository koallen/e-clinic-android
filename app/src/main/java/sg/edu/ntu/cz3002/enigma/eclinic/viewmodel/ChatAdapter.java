package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Chat adapter
 */
public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private TextView _chatText;
    private List<ChatMessage> _chatMessageList = new ArrayList<>();
    private Context _context;
    private String _time;

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

    public void setTime(String t){
        this._time = t;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ChatMessage _chatMessageObj = _chatMessageList.get(position);

        if (_chatMessageObj.getMine()) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_bubble, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.txtMsg);
            textView.setText(_chatMessageObj.getMessage());

        }
        else{
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_bubble_l, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.txtMsg);
            textView.setText(_chatMessageObj.getMessage());
        }

        TextView msgInfo = (TextView) convertView.findViewById(R.id.msgInfo);

        msgInfo.setText(_chatMessageObj.getTime());

//        convertView.findViewById(R.id.chatBubbleContainer).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "onClick", Toast.LENGTH_LONG).show();
//            }
//        });

        return convertView;
    }
}
