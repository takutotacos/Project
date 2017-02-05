package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.MainActivity;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.MapsActivity;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity.PostingActivity;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;

/**
 * Created by sugitatakuto on 2017/02/05.
 */
public class TouchListener implements View.OnTouchListener {
    private String TAG = getClass().getName();
    private Activity activity;
    private String selectedActivity;

    public TouchListener(Activity activity, String selectedActivity) {
        this.activity = activity;
        this.selectedActivity = selectedActivity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Class selectedClass = getActivityFromSelection(selectedActivity);
        if(!activity.getClass().equals(selectedClass)) {
            Intent intent = new Intent(activity, selectedClass);
            activity.startActivity(intent);
        }
        return true;
    }

    private Class getActivityFromSelection(String selectedActivity) {
        switch (selectedActivity) {
            case CommonConst.ActivityName.TAG_MAIN_ACTIVITY: // @todo Delete this: this is unnecessary
                return MainActivity.class;

//            case CommonConst.ActivityName.TAG_HOME_ACTIVITY: // @todo make this activity
//                return HomeActivity.class;
//
//            case CommonConst.ActivityName.TAG_WALL_ACTIVITY:
//                return WallActivity.class;
//
//            case CommonConst.ActivityName.TAG_NOTIFICATION:
//                return NotificationActivity.class;

            case CommonConst.ActivityName.TAG_MAPS_ACTIVITY:
                return MapsActivity.class;

            case CommonConst.ActivityName.TAG_POSTING_ACTIVITY:
                return PostingActivity.class;

            default:
                Log.e(TAG, "Unexpected screen transition.");
                return MainActivity.class;
        }
    }
}
