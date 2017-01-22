package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.ShowImage;

/**
 * Created by sugitatakuto on 2017/01/15.
 */
public class MarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private Activity activity = null;
    private HashMap<String, String> imgInfos = new HashMap<String, String>();

    public MarkerClickListener(Activity activity, HashMap<String, String> imgInfos) {
        this.activity = activity;
        this.imgInfos = imgInfos;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String tag = (String) marker.getTag();
        showImageActivity(imgInfos, tag);
        return true;
    }

    private void showImageActivity(HashMap<String, String> imgInfos, String tag) {
        Intent intent = new Intent(activity, ShowImage.class);
        String imgInfo = imgInfos.get(tag);
        intent.putExtra("imgInfo", imgInfo);
        activity.startActivity(intent);
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public HashMap<String, String> getImgInfos() {
        return this.imgInfos;
    }

    public void setImgInfos(HashMap<String, String> imgInfos) {
        this.imgInfos = imgInfos;
    }
}
