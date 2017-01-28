package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.ShowImage;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetImage;

/**
 * Created by sugitatakuto on 2017/01/15.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    Activity activity = null;
    AsyncGetImage mAsyncGetImage = null;

    public MarkerClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String tag = (String) marker.getTag();
        if(tag == null || "".equals(tag)) {
            return false;
        }
        String createDate = tag.substring(0, tag.indexOf("+"));
        String userId = tag.substring(tag.indexOf("+") + 1);
        showImageActivity(createDate, userId);
        return true;
    }

    private void showImageActivity(String createDate, String userId) {
        Intent intent = new Intent(activity, ShowImage.class);
        intent.putExtra("createDate", createDate);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }

}
