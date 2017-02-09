package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetPostingsByCategories;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonArray;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener.MarkerClickListener;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model.Posting;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TimeLineFragment extends Fragment implements AsyncResponseJsonArray {
    private String TAG = getClass().getName();
    private final static String BACKGROUND_COLOR = "background_color";
    private final static String CATEGORY = "cateogry";
    private AsyncGetPostingsByCategories asyncGetPostingsByCategories = null;
    private SharedPreferences sharedPreferences;
    private ListView listView;


    public TimeLineFragment() {
    }

    public static TimeLineFragment newInstance(@ColorRes int IdRes, String category) {
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR, IdRes);
        bundle.putString(CATEGORY, category);
        timeLineFragment.setArguments(bundle);
        return timeLineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listView = (ListView) inflater.inflate(R.layout.fragment_timeline_list, null);
        String category = getArguments().getString(CATEGORY);
        sharedPreferences = getActivity().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");
        String userId = sharedPreferences.getString("user_id", "");
        // execute getting postings whose category is category given
        asyncGetPostingsByCategories = new AsyncGetPostingsByCategories(this, category, token, userId);
        return listView;
    }

    @Override
    public void processFinish(JSONArray output) {
        if (output != null) {
            List<Posting> postings = new ArrayList<Posting>();
            for (int i = 0; i < output.length(); i++) {
                try {
                    JSONObject data = output.getJSONObject(i);
                    Posting posting = new Posting();
                    posting.setId(data.getString("id"));
                    posting.setUserId(data.getString("user_id"));
                    posting.setCategoryId(data.getString("category_id"));
                    posting.setLatitude(data.getString("latitude"));
                    posting.setLongitude(data.getString("longitude"));
                    posting.setComment(data.getString("comment"));
                    posting.setLocation1(data.getString("location1"));
                    posting.setLocation2(data.getString("location2"));
                    posting.setCreatedAt(data.getString("created_at"));
                    posting.setUpdatedAt(data.getString("updated_at"));
                    // @todo add image field to Posting and here
                    postings.add(posting);
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception happens: " + e.getCause());
                }
            }
            ArrayAdapter<Posting> adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_list_item_1, postings);
            listView.setAdapter(adapter);
        } else {

        }
    }
}
