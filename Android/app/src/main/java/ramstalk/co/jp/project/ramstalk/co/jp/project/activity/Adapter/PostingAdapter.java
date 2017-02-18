package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model.Posting;

/**
 * Created by sugitatakuto on 2017/02/10.
 */
public class PostingAdapter extends ArrayAdapter<Posting> {

    private int resourceId;
    private List<Posting> postings;
    private LayoutInflater inflater;

    public PostingAdapter(Context context,int resourceId, List<Posting> postings) {
        super(context, resourceId, postings);
        this.resourceId = resourceId;
        this.postings = postings;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        onePostingLineComment.setText(posting.getComment());

        TextView onePostingLineUserInfo = (TextView) view.findViewById(R.id.time_line_posting_user_id);
        onePostingLineUserInfo.setText(posting.getUserDisplayId());

        ImageView onePostingLineImage = (ImageView) view.findViewById(R.id.time_line_posting_image);
        onePostingLineImage.setImageBitmap(posting.getImage());

        return view;
    }
}
