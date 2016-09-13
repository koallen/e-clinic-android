package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;

/**
 * Created by koAllen on 9/2/2016.
 */
public class ChatFragment extends MvpFragment<ChatView, ChatPresenter> implements ChatView{

    private static final String TAG = "ChatFragment";
    private ArrayList _arrayList;
    private ChatArrayAdapter _chatArrayAdapter;
    private boolean _mine = true;
    // hard code users
    private String user1 = "patient";
    private String user2 = "doctor";

    @BindView(R.id.messageEditText) EditText _enterText;
    @BindView(R.id.msgListView) ListView _msgList;
    @BindView(R.id.sendButton) Button _sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(view);

        _enterText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getAction() == KeyEvent.KEYCODE_ENTER))
                    return sendChatMessage();
                else
                    return false;
            }
        });

        _sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

        _msgList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        _msgList.setAdapter(_chatArrayAdapter);

        _chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                _msgList.setSelection(_chatArrayAdapter.getCount()-1);
            }
        });
        return view;
    }

    public boolean sendChatMessage(){
        _chatArrayAdapter.add(new ChatMessage(_mine, _enterText.getText().toString(), user1, user2));
        _chatArrayAdapter.notifyDataSetChanged();
        _enterText.setText("");
        return true;
    }


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
