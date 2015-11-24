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
import java.util.Date;

import comp3111.questionroom.R;
import comp3111.questionroom.ques.Comment;

/**
 * Created by User on 24/11/2015.
 */
public class CommentListAdapter extends FirebaseRecyclerAdapter<Comment, CommentListAdapter.ViewHolder> {

    public CommentListAdapter(Query query, Class<Comment> itemClass, @Nullable ArrayList<Comment> pData, @Nullable ArrayList<String> keys) {
        super(query, itemClass, pData, keys);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView RView;
        public TextView TView;
        public ViewHolder(View v) {
            super(v);
            RView = (TextView) v.findViewById(R.id.reply_msg);
            TView = (TextView) v.findViewById(R.id.reply_time);
        }
    }

    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        Comment item = getItem(pos);
        holder.RView.setText(String.valueOf(item.getMsg()));
        holder.TView.setText(String.valueOf(item.getDateString()));
    }

    @Override
    protected void itemAdded(Comment item, String key, int position) {
        Log.d("MyAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Comment oldItem, Comment newItem, String key, int position) {
        Log.d("MyAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Comment item, String key, int position) {
        Log.d("MyAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Comment item, String key, int oldPosition, int newPosition) {
        Log.d("MyAdapter", "Moved an item.");
    }
}
