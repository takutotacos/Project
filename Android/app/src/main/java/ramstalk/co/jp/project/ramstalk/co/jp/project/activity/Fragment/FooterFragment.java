package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener.TouchListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FooterFragment.OnFragmentInteractionListener} Interface
 * to handle interaction events.
 * Use the {@link FooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FooterFragment extends Fragment {
    private TextView mWallTextView, mNotificationTextView, mHomeTextView, mMapTextView, mMakingAPostingTextView;
//    private OnFragmentInteractionListener mListener;

    public FooterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_footer, null);
        mWallTextView = (TextView) view.findViewById(R.id.footer_wall);
        mNotificationTextView = (TextView) view.findViewById(R.id.footer_notification);
        mHomeTextView = (TextView) view.findViewById(R.id.footer_home);
        mMapTextView = (TextView) view.findViewById(R.id.footer_map);
        mMakingAPostingTextView = (TextView) view.findViewById(R.id.footer_making_a_posting);

        mWallTextView.setOnTouchListener(new TouchListener(this.getActivity(), CommonConst.ActivityName.TAG_TIME_LINE_ACTIVITY));
        mNotificationTextView.setOnTouchListener(new TouchListener(this.getActivity(), CommonConst.ActivityName.TAG_NOTIFICATION_ACTIVITY));
        mHomeTextView.setOnTouchListener(new TouchListener(this.getActivity(), CommonConst.ActivityName.TAG_HOME_ACTIVITY));
        mMapTextView.setOnTouchListener(new TouchListener(this.getActivity(), CommonConst.ActivityName.TAG_MAPS_ACTIVITY));
        mMakingAPostingTextView.setOnTouchListener(new TouchListener(this.getActivity(), CommonConst.ActivityName.TAG_POSTING_ACTIVITY));
        return view;
    }
}