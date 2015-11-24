package comp3111.questionroom.ques;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 22/11/2015.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Comment implements Serializable {
    private String msg;
    private String dateString;

    @SuppressWarnings("unused")
    private Comment() {
    }

    public Comment(String wholeMsg) {
        msg = wholeMsg;
        dateString = new Date(new Date().getTime()).toString();

    }

    public String getDateString() {
        return dateString;
    }

    public String getMsg() {
        return msg;
    }


}
