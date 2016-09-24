package sg.edu.ntu.cz3002.enigma.eclinic.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.cz3002.enigma.eclinic.R;

/**
 * Chat list adapter
 */
public class ChatListAdapter extends ArrayAdapter<ChatListElement> {
    private Context _context;
    private List<ChatListElement> _list = new ArrayList<>();

    public ChatListAdapter(Context context, List<ChatListElement> list){
        super(context, R.layout.chat_list_element, list);
        _context = context;
        _list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ChatListElement element = _list.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_list_element, parent, false);
        TextView _msgFrom = (TextView) convertView.findViewById(R.id.msgFrom);
        TextView _msgShort = (TextView) convertView.findViewById(R.id.msgShort);
        _msgFrom.setText(element.getMsgFrom());
        _msgShort.setText(element.getMsgShort());
        return convertView;
    }


    @Override
    public int getCount(){
        return _list.size();
    }


    @Override
    public ChatListElement getItem(int position) {
        return _list.get(position);
    }
}
