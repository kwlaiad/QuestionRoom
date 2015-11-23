package comp3111.questionroom.ques;

import java.util.Date;

/**
 * Created by User on 22/11/2015.
 */
public class Comment {
    private String key;
    private String wholeMsg;
    private long timeStamp;

    @SuppressWarnings("unused")
    private Comment() {
    }

    public Comment(String wholeMsg) {
        this.wholeMsg = wholeMsg;
        this.timeStamp = new Date().getTime();

    }

    public String getTimeStamp() {
        return new Date(timeStamp).toString();
    }

    public String getWholeMsg() {
        return wholeMsg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
