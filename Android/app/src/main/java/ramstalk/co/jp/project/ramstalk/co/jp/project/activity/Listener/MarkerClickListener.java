package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.ShowImageActivity;

/**
 * Created by sugitatakuto on 2017/01/15.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    Activity activity = null;

    public MarkerClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String postingId = (String) marker.getTag();
        if(postingId == null || "".equals(postingId)) {
            return false;
        }
        showImageActivity(postingId);
        return true;
    }

    private void showImageActivity(String postingId) {
        Intent intent = new Intent(activity, ShowImageActivity.class);
        intent.putExtra("postingId", postingId);
        activity.startActivity(intent);
    }

    public Activity getActivity() {
        return this.activity;
    }
}
