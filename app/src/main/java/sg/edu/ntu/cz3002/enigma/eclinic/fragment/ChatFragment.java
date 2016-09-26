package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.activity.ChatActivity;
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
    @BindView(R.id.ChatList) ListView _chatListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        _chatList = new ArrayList<>();
        _chatListAdapter = new ChatListAdapter(this.getContext(), _chatList);
        _chatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                _chatListView.setSelection(_chatListAdapter.getCount()-1);
            }
        });

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(_broadcastReceiver,
                new IntentFilter("new-message"));

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
    }

    public void showError(String s){

    }

    // has received new message
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive broadcast");
            // display the received message on chat list
            String[] message = intent.getStringArrayExtra("message");
            // sender, message
            _chatListAdapter.add(new ChatListElement(message[1], message[2]));
            //_chatListAdapter.notifyDataSetChanged();
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


