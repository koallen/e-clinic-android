package sg.edu.ntu.cz3002.enigma.eclinic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.Value;
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
    private String _interlocutor;
    private String _user;
    private static DbHelper _dbHelper;
    private SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

        // set some variables
        setUserAndInterlocutor();
        _dbHelper = new DbHelper(this);

        // UI initialization
        initializeToolbar();
        initializeSendingArea();
        initializeMessages();
    }

    public boolean sendChatMessage() {
        String msg = _enterText.getText().toString();
        String currentTime = _simpleDateFormat.format(Calendar.getInstance().getTime());

        if (!msg.equalsIgnoreCase("")) {
            _chatAdapter.add(new ChatMessage(_mine, msg, _interlocutor, _user, currentTime));
            _chatAdapter.notifyDataSetChanged();
            // reset the input box
            _enterText.setText("");

            // save the sent message into database
            _dbHelper.insertDb(_interlocutor, _user, msg);
            Log.d(TAG, "sent message saved to db at " + currentTime);
        }
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
            _chatAdapter.add(new ChatMessage(!_mine, message[0], message[1], _user, _simpleDateFormat.format(new Date())));
            _chatAdapter.notifyDataSetChanged();
        }
    };

    public void loadHistory(){
        String[] selectionValues = new String[2];
        selectionValues[0] = selectionValues[1] = _interlocutor;
        String receiver, sender, message, datetime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        _dbHelper.setSelection("SENDER = ? OR RECEIVER = ?");
        _dbHelper.setSelectionValue(selectionValues);
        Cursor c = _dbHelper.readDb();

        if(c.getCount() == 0)
            return;

        _chatAdapter.clear();
        while(true){
            sender = c.getString(c.getColumnIndex("SENDER"));
            receiver = c.getString(c.getColumnIndex("RECEIVER"));
            message = c.getString(c.getColumnIndex("MESSAGE"));
            datetime = c.getString(c.getColumnIndex("TIME"));
            try {
                Date date = simpleDateFormat.parse(datetime);
                datetime = _simpleDateFormat.format(date);
            } catch (Exception exception) { }
            if (sender.equals(_interlocutor)){
                Log.d(TAG, "receive message");
                _chatAdapter.insert((new ChatMessage(!_mine, message, sender, receiver, datetime)), 0);
            }
            else if (receiver.equals(_interlocutor)){
                _chatAdapter.insert((new ChatMessage(_mine, message, sender, receiver, datetime)), 0);
            }
            if(c.isLast())
                return;
            c.moveToNext();
        }
    }

    @Override
    public void onPause(){
        Log.d(TAG, "onPause");
        super.onPause();
        Log.d(TAG, "---ON PAUSE---");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(_broadcastReceiver);
    }

    @Override
    public void onResume(){
        Log.d(TAG, "onResume");
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(_broadcastReceiver,
                new IntentFilter("new-message"));
        loadHistory();
    }

    @NonNull
    @Override
    public ChatPresenter createPresenter() {
        return new ChatPresenter();
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "Menu item clicked " + item.toString());
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

    private void initializeToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(_interlocutor);
    }

    private void initializeSendingArea() {
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
    }

    private void initializeMessages() {
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
    }

    private void setUserAndInterlocutor() {
        SharedPreferences preferences = this.getSharedPreferences(Value.preferenceFilename, Context.MODE_PRIVATE);
        _user = preferences.getString(Value.userNamePreferenceName, "no name");

        Intent intent = getIntent();
        _interlocutor = intent.getStringExtra("sender");
    }

    private void goToProgressList() {
        Intent intent = new Intent(this, ProgressListActivity.class);
        // TODO: remove hardcoded doctor name
        intent.putExtra("doctor", "koallen");
        startActivity(intent);
    }

    private void goToAddReminder() {
        Intent intent = new Intent(this, AddReminderActivity.class);
        // TODO: remove hardcoded doctor name
        intent.putExtra("doctor", "koallen");
        startActivity(intent);
    }
}

