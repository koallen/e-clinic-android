package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;

/**
 * chat activity.
 */
public class ChatActivity extends MvpActivity<ChatView, ChatPresenter> implements ChatView {

    private static final String TAG = "ChatFragment";
    private List<ChatMessage> _msgArrayList;
    private ChatAdapter _chatAdapter;
    private boolean _mine = true;
    // hard code users
    private String sender = "sender";
    private String user = "user";

    @BindView(R.id.messageEditText)
    EditText _enterText;
    @BindView(R.id.msgListView)
    ListView _msgListView;
    @BindView(R.id.sendButton)
    ImageButton _sendButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        setSender(intent.getStringExtra("sender"));

        RelativeLayout root = (RelativeLayout) findViewById(R.id.chattingPage);
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.notificationsetting_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        setActionBar(bar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Notification Settings");

        _enterText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getAction() == KeyEvent.KEYCODE_ENTER))
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

        _msgArrayList = new ArrayList<ChatMessage>();

        _msgListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        _msgListView.setStackFromBottom(true);

        _chatAdapter = new ChatAdapter(this, _msgArrayList);
        _chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                _msgListView.setSelection(_chatAdapter.getCount() - 1);
            }
        });

        _msgListView.setAdapter(_chatAdapter);

        // Register to receive messages.
        // Registering an observer, _broadcastReceiver, to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(_broadcastReceiver,
                new IntentFilter("new-message"));
    }

    public boolean sendChatMessage() {
        String msg = _enterText.getText().toString();
        if (!msg.equalsIgnoreCase("")) {
            _chatAdapter.add(new ChatMessage(_mine, _enterText.getText().toString(), sender, user));
            _chatAdapter.notifyDataSetChanged();
            // reset the input box
            _enterText.setText("");
        }
        return true;
    }

    // TODO save msg to database
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive broadcast");
            // display the received message on chatting page
            String[] message = intent.getStringArrayExtra("message");
            // sender, message, time
            _chatAdapter.add(new ChatMessage(!_mine, message[1], message[0], user));
            _chatAdapter.notifyDataSetChanged();

            // save the message into the database
        }
    };

    public void setSender(String s){
        this.sender = s;
    }

    @NonNull
    @Override
    public ChatPresenter createPresenter() {
        return new ChatPresenter(this);
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

