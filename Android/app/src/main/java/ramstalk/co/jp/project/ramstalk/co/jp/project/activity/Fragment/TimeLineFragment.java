package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Adapter.PostingAdapter;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetPostingsByCategories;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonArray;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseJsonObject;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Model.Posting;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TimeLineFragment extends Fragment implements AsyncResponseJsonObject {
    private String TAG = getClass().getName();
    private final static String BACKGROUND_COLOR = "background_color";
    private final static String CATEGORY_ID = "cateogry";
    private AsyncGetPostingsByCategories asyncGetPostingsByCategories = null;
    private SharedPreferences sharedPreferences;
    private ListView listView;


    public TimeLineFragment() {
    }

    public static TimeLineFragment newInstance(String categoryId) {
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID, categoryId);
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
        String categoryId = getArguments().getString(CATEGORY_ID);
        sharedPreferences = getActivity().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", "");
        String userId = sharedPreferences.getString("user_id", "");
        // execute getting postings whose category is category given
        asyncGetPostingsByCategories = new AsyncGetPostingsByCategories(this, token, categoryId, userId);
        asyncGetPostingsByCategories.execute();
        return listView;
    }

    @Override
    public void processFinish(JSONObject output) {
        if (output != null) {
            List<Posting> postings = new ArrayList<Posting>();
            try {
                JSONArray postingsArray = output.getJSONArray("postings");
                for (int i = 0; i < postingsArray.length(); i++) {
                    JSONObject postingJsonObject = postingsArray.getJSONObject(i);
                    Posting posting = new Posting();
                    posting.setId(postingJsonObject.getString("id"));
                    posting.setUserId(postingJsonObject.getString("user_id"));
                    posting.setComment(postingJsonObject.getString("comment"));
                    posting.setLocation1(postingJsonObject.getString("location1"));
                    posting.setLocation2(postingJsonObject.getString("location2"));
                    posting.setCreatedAt(postingJsonObject.getString("created_at"));
                    posting.setUpdatedAt(postingJsonObject.getString("updated_at"));
                    // @todo add image field to Posting and here
                    postings.add(posting);
                }
            } catch(JSONException e) {
                Log.e(TAG, e.getMessage());
            }
            PostingAdapter adapter = new PostingAdapter(
                    getContext(), R.layout.fragment_timeline, postings);
            listView.setAdapter(adapter);
        } else {

        }
    }
}
