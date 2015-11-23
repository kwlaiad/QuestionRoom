package comp3111.questionroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import org.parceler.Parcels;
import java.util.ArrayList;

import comp3111.questionroom.db.DBHelper;
import comp3111.questionroom.db.DBUtil;
import comp3111.questionroom.ques.Question;
import comp3111.questionroom.Adapter.QuestionListAdapter;

/**
 * Created by User on 21/11/2015.
 */
public class MainActivityFragment extends Fragment {

    private final static String FIREBASE_URL = "https://incandescent-inferno-7023.firebaseio.com/";
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private String roomName;
    private Query mQuery;
    private QuestionListAdapter mAdapter;
    private ArrayList<Question> mList;
    private ArrayList<String> mKeys;
    private DBUtil dbutil;
    private View view;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        roomName = getActivity().getIntent().getStringExtra("roomName");
        setUpFirebase();
        createRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AskActivity.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
            }
        });

        dbutil = new DBUtil(new DBHelper(getActivity()));

        return view;
    }

    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mList = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mList = new ArrayList<Question>();
            mKeys = new ArrayList<String>();
        }
    }

    private void createRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_main);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new QuestionListAdapter(mQuery, Question.class, mList, mKeys);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpFirebase() {
        Firebase.setAndroidContext(getActivity());
        mQuery = new Firebase(FIREBASE_URL).child(roomName).child("questions");

    }

    // Saving the list of items and keys of the items on rotation
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
