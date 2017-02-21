package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Posting;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncComment;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncLike;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;

/**
 * Created by sugitatakuto on 2017/02/10.
 */
public class PostingAdapter extends ArrayAdapter<Posting> {
    private String TAG = CommonConst.ActivityName.TAG_POSTING_ACTIVITY;
    private int resourceId;
    private final List<Posting> postings;
    private LayoutInflater inflater;
    private SharedPreferences sharedPreferences;
    private TextView likeNumber;

    public PostingAdapter(Context context,int resourceId, List<Posting> postings) {
        super(context, resourceId, postings);
        this.resourceId = resourceId;
        this.postings = postings;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sharedPreferences = context.getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView != null) {
            view = convertView;
        } else {
            view = this.inflater.inflate(this.resourceId, null);
        }
        Posting posting = this.postings.get(position);

        TextView onePostingLineComment = (TextView)view.findViewById(R.id.time_line_posting_comment);
        onePostingLineComment.setText(posting.getContent());

        TextView onePostingLineUserInfo = (TextView) view.findViewById(R.id.time_line_posting_user_id);
        onePostingLineUserInfo.setText(posting.getUser().getUserId());

        byte[] decodedString = Base64.decode(posting.getImage(), Base64.DEFAULT);
        ImageView onePostingLineImage = (ImageView) view.findViewById(R.id.time_line_posting_image);
        onePostingLineImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        likeNumber = (TextView) view.findViewById(R.id.like_number);
        likeNumber.setText(posting.getLikeCounts());

        ImageView likeImage = (ImageView) view.findViewById(R.id.like_button);
        likeImage.setOnClickListener(new CustomLikeClickListener(sharedPreferences.getString("auth_token", ""),
                posting.getId(), position, likeImage, posting.isCanLike(), posting.getLikeId()));
        if(!posting.isCanLike()) {
            likeImage.setBackgroundResource(android.R.drawable.ic_delete);
        }

        TextView commentNumber = (TextView) view.findViewById(R.id.comment_number);
        commentNumber.setText(posting.getCommentCounts());

        final LinearLayout commentInputComponent = (LinearLayout) view.findViewById(R.id.comment_input_component);
        commentInputComponent.setVisibility(View.GONE);

        EditText commentInput = (EditText) view.findViewById(R.id.comment_input);
        String comment = commentInput.getText().toString();

        TextView commentInputButton = (TextView) view.findViewById(R.id.comment_input_button);
        commentInputButton.setOnClickListener(new CustomCommentClickListener(sharedPreferences.getString("auth_token", ""),
                posting.getId(), position, comment, commentInputComponent, true));

        ImageView commentImage = (ImageView) view.findViewById(R.id.comment_button);
        commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentInputComponent.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private class CustomLikeClickListener implements View.OnClickListener, AsyncResponseJsonObject {
        private String authToken;
        private String postingId;
        private AsyncLike mAsyncLike;
        private ImageView likeImage;
        private int position;
        private boolean isLike;
        private String likeId;

        public CustomLikeClickListener(String authToken, String postingId, int position,
                                   ImageView likeImage, boolean isLike, String likeId) {
            this.postingId = postingId;
            this.authToken = authToken;
            this.position = position;
            this.likeImage = likeImage;
            this.isLike = isLike;
            this.likeId = likeId;
        }

        @Override
        public void onClick(View v) {
            mAsyncLike = new AsyncLike(this, authToken, postingId, isLike, likeId);
            mAsyncLike.execute();
        }

        @Override
        public void processFinish(JSONObject output) {
            if(output != null) {
                try {
                    if(CommonConst.ApiAction.LIKE_A_POST.equals(output.getString("action"))) { // when a user likes a post
                        this.likeImage.setBackgroundResource(android.R.drawable.ic_delete);
                    } else { // when deleting a like from a post
                        this.likeImage.setBackgroundResource(android.R.drawable.ic_input_add);
                    }
                    String likeCounts = output.getJSONObject("posting").getString("like_counts");
                    postings.get(position).setLikeCounts(likeCounts);
                    postings.get(position).setCanLike(output.getJSONObject("posting").getBoolean("can_like"));
                    postings.get(position).setLikeId(output.getJSONObject("posting").getString("like_id"));
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private class CustomCommentClickListener implements View.OnClickListener, AsyncResponseJsonObject {
        private String authToken;
        private String postingId;
        private String commentIntput;
        private AsyncComment mAsyncComment;
        private LinearLayout commentInputComponent;
        private int position;
        private boolean createCommentFlg;

        public CustomCommentClickListener(String authToken, String postingId, int position, String commentInput,
                                          LinearLayout commentInputComponent, boolean createCommentFlg) {
            this.postingId = postingId;
            this.authToken = authToken;
            this.position = position;
            this.commentIntput = commentInput;
            this.commentInputComponent = commentInputComponent;
            this.createCommentFlg = createCommentFlg;
        }

        @Override
        public void onClick(View v) {
            mAsyncComment = new AsyncComment(this, authToken, postingId, commentIntput, createCommentFlg);
            mAsyncComment.execute();
        }

        @Override
        public void processFinish(JSONObject output) {
            if(output != null) {
                try {
                    commentInputComponent.setVisibility(View.GONE);
                    String commentCounts = output.getJSONObject("posting").getString("comment_counts");
                    postings.get(position).setCommentCounts(commentCounts);
                    //
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
