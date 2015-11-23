package comp3111.questionroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import comp3111.questionroom.ques.Question;

/**
 * Created by User on 21/11/2015.
 */
public class AskActivityFragment extends Fragment {

    private final static String FIREBASE_URL = "https://incandescent-inferno-7023.firebaseio.com/";
    private String roomName;
    private String category;
    private String convertCate;
    Firebase mFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);

        roomName = getActivity().getIntent().getStringExtra("roomName");
        mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions");

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final EditText mEditText = (EditText) view.findViewById(R.id.ask_text);
        Button ask = (Button) view.findViewById(R.id.ask_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertCate = parent.getItemAtPosition(position).toString();
                switch (convertCate) {
                    case "Lecture": category = "opt1";
                                    break;
                    case "Lab":    category = "opt2";
                                    break;
                    case "Tutorial":    category = "opt3";
                                    break;
                    case "Other":   category = "opt4";
                                    break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast toast = Toast.makeText(getContext(), "Please Select Category.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

        return view;
    }

    private void sendMsg() {
        View view = getView();
        EditText mEditText = (EditText) view.findViewById(R.id.ask_text);
        String input = mEditText.getText().toString();
        if (!input.equals("")) {
            Question question = new Question(input, category);
            mFirebaseRef.push().setValue(question);
            mEditText.setText("");
        }
    }

}
