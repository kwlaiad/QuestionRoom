package comp3111.questionroom.ques;

import java.util.ArrayList;
import java.util.Date;

import org.parceler.Parcel;

/**
 * Created by User on 22/11/2015.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Question {

    /**
     * Must be synced with firebase JSON structure
     * Each must have getters
     */
    private String key;
    private String wholeMsg = "";
    private String head = "";
    private String headLastChar = "";
    private String desc = "";
    private String linkedDesc = "";
    private boolean completed;
    private long timestamp;
    private String tags = "";
    private int echo;
    private int order;
    private String dateString;
    private String trustedDesc;
    private int report;
    private String category = "";
    private String new_com = "";
    private ArrayList<Comment> comments;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Question() {
    }

    /**
     * Set question from a String message
     *
     * @param message string message
     */
    public Question(String message, String cate) {
        wholeMsg = message;
        echo = 0;
        report = 0;
        category = cate;
        head = getFirstSentence(message).trim();

        if (head.length() + 1 < message.length())
            desc = message.substring(head.length() + 1);

        // get the last char
        if (head.length() > 0)
            headLastChar = head.substring(head.length() - 1);
        else
            headLastChar = "";

        timestamp = new Date().getTime();
        comments = new ArrayList<>();
    }

    /**
     * Get first sentence from a message
     *
     * @param message
     * @return
     */
    public static String getFirstSentence(String message) {
        String[] tokens = {"\n"};
        int index = -1;

        for (String token : tokens) {
            int i = message.indexOf(token);
            if (i == -1) {
                continue;
            }

            if (index == -1) {
                index = i;
            } else {
                index = Math.min(i, index);
            }
        }
        if (index == -1)
            return message;
        return message.substring(0, index + 1);
    }

    public String getDateString() {
        return dateString;
    }

    public String getTrustedDesc() {
        return trustedDesc;
    }

    /* -------------------- Getters ------------------- */
    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public int getEcho() {
        return echo;
    }

    public String getWholeMsg() {
        return wholeMsg;
    }

    public String getHeadLastChar() {
        return headLastChar;
    }

    public String getLinkedDesc() {
        return linkedDesc;
    }

    public boolean isCompleted() {
        return completed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTags() {
        return tags;
    }

    public int getOrder() {
        return order;
    }

    public int getReport() {
        return report;
    }

    public String getCategory() {
        return category;
    }

    public String getNew_com() {
        return new_com;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Question)) {
            return false;
        }
        Question other = (Question) o;
        return key.equals(other.key) && echo == other.echo;
    }

}
