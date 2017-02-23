package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Entity.Notification;

/**
 * Created by sugitatakuto on 2017/02/24.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {

    private String TAG = NotificationAdapter.class.getSimpleName();
    private int resourceId;
    private LayoutInflater inflater;
    private List<Notification> notifications;
    private String authToken;


    public NotificationAdapter(Context context, int resourceId, List<Notification> notifications, String authToken) {
        super(context, resourceId, notifications);
        this.resourceId = resourceId;
        this.notifications = notifications;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.authToken = authToken;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = (convertView != null)? convertView : this.inflater.inflate(this.resourceId, null);
        Notification notification = this.notifications.get(position);

        TextView notifiedByView = (TextView)view.findViewById(R.id.notification_notified_by);
        notifiedByView.setText(notification.getNotifiedBy().getUserId() + "さんが");

        TextView noticeTypeView = (TextView) view.findViewById(R.id.notification_notice_type);
        int noticeType = ("comment".equals(notification.getNoticeType()))?
                R.string.notification_notice_type_comment : R.string.notification_notice_type_like;
        noticeTypeView.setText(noticeType);

        TextView createdAtView = (TextView) view.findViewById(R.id.notification_created_at);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String createdAtString = sdf.format(notification.getCreatedAt());
        createdAtView.setText(createdAtString);
        return view;
    }
}
