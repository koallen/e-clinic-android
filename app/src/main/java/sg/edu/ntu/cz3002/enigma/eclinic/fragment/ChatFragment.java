package sg.edu.ntu.cz3002.enigma.eclinic.fragment;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.ntu.cz3002.enigma.eclinic.R;
import sg.edu.ntu.cz3002.enigma.eclinic.presenter.ChatPresenter;
import sg.edu.ntu.cz3002.enigma.eclinic.view.ChatView;

/**
 * chat fragement.
 */
public class ChatFragment extends MvpFragment<ChatView, ChatPresenter> implements ChatView {

    private List<ChatListElement> _chatList;
    private ChatListAdapter _chatListAdapter;
    @BindView(R.id.ChatList) ListView _chatListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        _chatList = new ArrayList<ChatListElement>();
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
        return view;
    }

    public void getChatList(){

        String text1 = "From", text2 = "Message";
        _chatListAdapter.add(new ChatListElement(text1, text2));
    }

    public void showError(String s){

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


