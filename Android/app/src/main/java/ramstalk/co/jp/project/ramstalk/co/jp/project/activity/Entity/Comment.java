package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sugitatakuto on 2017/02/21.
 */
public class Comment {
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("posting_id")
    private String postingId;

    private String content;

    public Comment() {}

    public Comment(String content) {
        this.content = content;
    }

    public Comment(String id, String userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostingId() {
        return postingId;
    }

    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
