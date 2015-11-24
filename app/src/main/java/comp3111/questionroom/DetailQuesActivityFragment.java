package comp3111.questionroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import comp3111.questionroom.Adapter.CommentListAdapter;
import comp3111.questionroom.ques.Comment;
import comp3111.questionroom.ques.Question;

/**
 * Created by User on 21/11/2015.
 */
public class DetailQuesActivityFragment extends Fragment {

    private final static String FIREBASE_URL = "https://incandescent-inferno-7023.firebaseio.com/";
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private String roomName;
    private Query mQuery;
    private String re_item;
    private String qmsg;
    private RecyclerView mRecyclerView;
    private View view;
    private CommentListAdapter mAdapter;
    private ArrayList<Comment> mList;
    private ArrayList<String> mKeys;
    private Firebase mFirebaseRef;
    private Boolean disableButtonForEcho = false;
    private Boolean disableButtonForRe = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

        roomName = getActivity().getIntent().getStringExtra("roomName");
        re_item = getActivity().getIntent().getStringExtra("QuestionItem");
        qmsg = getActivity().getIntent().getStringExtra("qmsg");
        setHasOptionsMenu(true);
        setUpFirebase();
        createRecyclerView();

        TextView wholemsg = (TextView) view.findViewById(R.id.wholemsg);
        wholemsg.setText(qmsg);

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) view.findViewById(R.id.reply);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailmenu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    private void sendMessage() {
        EditText inputText = (EditText) view.findViewById(R.id.reply);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Comment cm = new Comment(input);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.child("comments").push().setValue(cm);
            inputText.setText("");
        }
    }

    private void setUpFirebase() {
        Firebase.setAndroidContext(getActivity());
        mQuery = new Firebase(FIREBASE_URL).child(roomName).child("questions").child(re_item).child("comments");
        mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions").child(re_item);
    }

    private void createRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_reply);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CommentListAdapter(mQuery, Comment.class, mList, mKeys);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mList = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mList = new ArrayList<Comment>();
            mKeys = new ArrayList<String>();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_echo:
                if(!disableButtonForEcho) {
                    mFirebaseRef.child("echo").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long echoValue = (long) dataSnapshot.getValue();
                            mFirebaseRef.child("echo").setValue(echoValue + 1);
                            disableButtonForEcho = true;
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
                return true;

            case R.id.add_report:
                if(!disableButtonForRe) {
                    mFirebaseRef.child("report").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long reportVal = (long) dataSnapshot.getValue();
                            mFirebaseRef.child("report").setValue(reportVal + 1);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }
}
