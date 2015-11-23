package comp3111.questionroom;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by User on 21/11/2015.
 */
public class JoinActivityFragment extends Fragment {
    public JoinActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);
        final EditText mEditText = (EditText) view.findViewById(R.id.textView);

        Button bjoin = (Button) view.findViewById(R.id.join_button);
        bjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = mEditText.getText().toString();
                if(roomName.length() == 0) {
                    mEditText.setError(getString(R.string.error_field_required));
                    mEditText.setText("");
                }
                else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("roomName", roomName);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
