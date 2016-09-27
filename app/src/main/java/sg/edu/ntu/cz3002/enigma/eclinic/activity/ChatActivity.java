package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.model.DbHelper;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.ChatAdapter;
import sg.edu.ntu.cz3002.enigma.eclinic.viewmodel.ChatMessage;

/**
 * chat activity.
 */
public class ChatActivity extends MvpActivity<ChatView, ChatPresenter> implements ChatView {

    private static final String TAG = "ChatActivity";
    private List<ChatMessage> _msgArrayList;
    private ChatAdapter _chatAdapter;
    private boolean _mine = true;
    // hard code users
    private String sender = "sender";
    private String user = "user";
    private static DbHelper _dbHelper = null;

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

        _dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        setSender(intent.getStringExtra("sender"));

        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(sender);

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

        _msgArrayList = new ArrayList<>();

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

        // todo display the chatting history
    }

    public boolean sendChatMessage() {
        String msg = _enterText.getText().toString();
        if (!msg.equalsIgnoreCase("")) {
            _chatAdapter.add(new ChatMessage(_mine, msg, sender, user));
            _chatAdapter.notifyDataSetChanged();
            // reset the input box
            _enterText.setText("");
        }
        // save the sent message into database
        _dbHelper.insertDb(sender, user, msg);
        Log.d(TAG, _dbHelper.readDb());
        return true;
    }

    // receive message from FirebaseMessagingService
    private BroadcastReceiver _broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive broadcast");
            // display the received message on chatting page
            String[] message = intent.getStringArrayExtra("message");
            // sender, message, time
            _chatAdapter.add(new ChatMessage(!_mine, message[1], message[0], user));
            _chatAdapter.notifyDataSetChanged();

            // save the received message into the database
            _dbHelper.insertDb(message[0], message[1], message[2]);
        }
    };

    public void setSender(String s) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "menu inflating");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "Menu item clicked");
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_check_progress:
                goToProgressList();
                return true;
            case R.id.action_add_reminder:
                goToAddReminder();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToProgressList() {
        Intent intent = new Intent(this, ProgressListActivity.class);
        intent.putExtra("doctor", "koallen");
        startActivity(intent);
    }

    private void goToAddReminder() {
        Intent intent = new Intent(this, AddReminderActivity.class);
        intent.putExtra("doctor", "koallen");
        startActivity(intent);
    }
}

