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

import java.util.HashMap;
import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Comment;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Posting;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Interface.ApiService;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Manager.ApiManager;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sugitatakuto on 2017/02/10.
 */
public class PostingAdapter extends ArrayAdapter<Posting> {
    private String TAG = PostingAdapter.class.getSimpleName();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = (convertView != null)? convertView : this.inflater.inflate(this.resourceId, null);
        final Posting posting = this.postings.get(position);

        TextView onePostingLineComment = (TextView)view.findViewById(R.id.time_line_posting_comment);
        onePostingLineComment.setText(posting.getContent());

        TextView onePostingLineUserInfo = (TextView) view.findViewById(R.id.time_line_posting_user_id);
        onePostingLineUserInfo.setText(posting.getUser().getUserId());

        ImageView onePostingLineImage = (ImageView) view.findViewById(R.id.time_line_posting_image);
        byte[] decodedString = Base64.decode(posting.getImage(), Base64.DEFAULT);
        onePostingLineImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        likeNumber = (TextView) view.findViewById(R.id.like_number);
        likeNumber.setText(posting.getLikeCounts());
        final ApiService apiService = ApiManager.getApiService();
        final ImageView likeImage = (ImageView) view.findViewById(R.id.like_button);
        if(!posting.isCanLike()) {
            likeImage.setBackgroundResource(android.R.drawable.ic_delete);
        }
        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Posting> postingTarget = posting.isCanLike()?
                        apiService.addLike(sharedPreferences.getString("auth_token", ""), posting.getId()) :
                        apiService.deleteLike(sharedPreferences.getString("auth_token", ""), posting.getId(), posting.getLikeId());
                postingTarget.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Posting>() {
                            @Override
                            public void onCompleted() {
                                int drawableId =
                                        posting.isCanLike()? android.R.drawable.ic_input_add : android.R.drawable.ic_input_delete;
                                likeImage.setBackgroundResource(drawableId);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "ERROR : " + e.toString());
                            }

                            @Override
                            public void onNext(Posting postingTarget) {
                                postings.get(position).setLikeCounts(postingTarget.getLikeCounts());
                                postings.get(position).setCanLike(postingTarget.isCanLike());
                                postings.get(position).setLikeId(postingTarget.getLikeId());
                            }
                        });
                }
        });

        TextView commentNumber = (TextView) view.findViewById(R.id.comment_number);
        commentNumber.setText(posting.getCommentCounts());
        final LinearLayout commentInputComponent = (LinearLayout) view.findViewById(R.id.comment_input_component);
        commentInputComponent.setVisibility(View.GONE);
        ImageView commentImage = (ImageView) view.findViewById(R.id.comment_button);
        commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentInputComponent.setVisibility(View.VISIBLE);
            }
        });

        EditText commentInput = (EditText) view.findViewById(R.id.comment_input);
        final String comment = commentInput.getText().toString();
        TextView commentInputButton = (TextView) view.findViewById(R.id.comment_input_button);
        commentInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(comment)) {
                    Comment commentToAdd = new Comment(comment);
                    HashMap<String, Comment> commentHashMap = new HashMap<String, Comment>();
                    commentHashMap.put("comment", commentToAdd);
                    Observable<Posting> postingTarget = apiService.addComment(
                            sharedPreferences.getString("auth_token", ""), posting.getId(), commentHashMap);
                    postingTarget.subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Posting>() {
                                @Override
                                public void onCompleted() {
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "ERROR : " + e.toString());
                                }

                                @Override
                                public void onNext(Posting postingTarget) {
                                    postings.get(position).setCommentCounts(postingTarget.getCommentCounts());
                                }
                            });
                }
            }
        });
        return view;
    }
}
