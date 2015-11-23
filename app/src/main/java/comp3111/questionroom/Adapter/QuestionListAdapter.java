package comp3111.questionroom.Adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Query;

import java.util.ArrayList;

import comp3111.questionroom.R;
import comp3111.questionroom.ques.Question;

/**
 * Created by User on 22/11/2015.
 */
public class QuestionListAdapter extends FirebaseRecyclerAdapter<Question, QuestionListAdapter.ViewHolder> {

    public QuestionListAdapter(Query query, Class<Question> itemClass, @Nullable ArrayList<Question> pData, @Nullable ArrayList<String> keys) {
        super(query, itemClass, pData, keys);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView QView;
        public TextView echoView;
        public TextView cateView;
        public ViewHolder(View v) {
            super(v);
            QView = (TextView) v.findViewById(R.id.head);
            echoView = (TextView) v.findViewById(R.id.echo);
            cateView = (TextView) v.findViewById(R.id.category);
        }
    }

    @Override
    public QuestionListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Question item = getItem(pos);
        String cat = "";
        switch (String.valueOf(item.getCategory())) {
            case "opt1":    cat = "Lecture";
                            break;
            case "opt2":    cat = "Lab";
                            break;
            case "opt3":    cat = "Tutorial";
                            break;
            case "opt4":    cat = "Others";
                            break;
            default:
        }
        holder.QView.setText(String.valueOf(item.getHead()));
        holder.echoView.setText(String.valueOf(item.getEcho()));
        holder.cateView.setText(cat);
    }

    @Override
    protected void itemAdded(Question item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Question oldItem, Question newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Question item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Question item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }
}
