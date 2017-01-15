package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.ShowImage;

/**
 * Created by sugitatakuto on 2017/01/15.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    Activity activity = null;
    String userId = null;
    String createDate = null;

    public MarkerClickListener(Activity activity, String userId, String createDate) {
        this.activity = activity;
        this.userId = userId;
        this.createDate = createDate;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showImageActivity(userId, createDate);
        return true;
    }

    private void showImageActivity(String userId, String createDate) {
        Intent intent = new Intent(activity, ShowImage.class);
        intent.putExtra("userId", userId);
        intent.putExtra("createDate", createDate);
        activity.startActivity(intent);
    }

}
