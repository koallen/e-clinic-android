package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.ChatActivity;
import sg.edu.ntu.cz3002.enigma.eclinic.model.DbHelper;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.ChatListAdapter;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.ChatListElement;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.ChatMessage;

/**
 * chat fragement.
 */
public class ChatFragment extends MvpFragment<ChatView, ChatPresenter> implements ChatView {

    private static final String TAG = "ChatFragment";
    private List<ChatListElement> _chatList;
    private ChatListAdapter _chatListAdapter;
    private static DbHelper _dbHelper;
    private String _user;
    SharedPreferences _preference;
    @BindView(R.id.ChatList) ListView _chatListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        _dbHelper = new DbHelper(this.getContext());

        _preference = this.getActivity().getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        _user = _preference.getString(Value.userNamePreferenceName, "no name");

        _chatList = new ArrayList<>();
        _chatListAdapter = new ChatListAdapter(this.getContext(), _chatList);
        _chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                _chatListView.setSelection(_chatListAdapter.getCount()-1);
            }
        });

        getChatList();

        _chatListView.setAdapter(_chatListAdapter);

        _chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView s = (TextView) view.findViewById(R.id.msgFrom);
                String sender = s.getText().toString();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("sender", sender);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getChatList(){
        String text1 = "From", text2 = "Message";
        _chatListAdapter.add(new ChatListElement(text1, text2));
//        _chatListAdapter.insert(new ChatListElement(text1, text2), 0);
    }

    public void showError(String s){

    }

    @Override
    public void onResume() {
        Log.d(TAG, "--- ON RESUME ---");
        super.onResume();
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(_broadcastReceiver,
                new IntentFilter("new-message"));
        loadHistory();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(_broadcastReceiver);
    }

    public void loadHistory(){
        List<String> temp = _dbHelper.getChatHistoryList();
        _chatListAdapter.clear();
        for(int i = 0; i < temp.size(); i += 2) {
            if(!temp.get(i).equals(_user))
//                _chatListAdapter.add(new ChatListElement(temp.get(i), temp.get(i+1)));
                  _chatListAdapter.insert(new ChatListElement(temp.get(i), temp.get(i+1)), 0);
        }
    }

    // has received new message
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive broadcast");
            // display the received message on chat list
//            String[] message = intent.getStringArrayExtra("message");
//            int oldChat = 0;
//            for(ChatListElement element : _chatList){
//                if (element.getMsgFrom() == message[1]){
//                    oldChat = 1;
//                    _chatListAdapter.remove(element);
//                    break;
//                }
//            }
//            // sender, message
//            _chatListAdapter.add(new ChatListElement(message[0], message[1]));
            loadHistory();
        }
    };

    @NonNull
    @Override
    public ChatPresenter createPresenter(){
        return new ChatPresenter(this.getContext());
    }

    public static ChatFragment newInstance(int index) {
        ChatFragment f = new ChatFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }
}


