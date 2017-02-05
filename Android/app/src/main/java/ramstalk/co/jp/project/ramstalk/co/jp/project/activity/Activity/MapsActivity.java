package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ramstalk.co.jp.project.R;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons.CommonConst;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncGetInfoOnMap;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http.AsyncResponseMap;
import ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Listener.MarkerClickListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AsyncResponseMap {

    private GoogleMap mMap;
    private AsyncGetInfoOnMap getInfoOnMap = null;
    private SharedPreferences sharedPreferences;
    private String token = null;
    private static String TAG = CommonConst.ActivityName.TAG_MAPS_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sharedPreferences = getApplicationContext().getSharedPreferences(CommonConst.FileName.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("auth_token", "");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /**
         * @Todo
         * 1. get where you are
         * 2. set the distance away from where you are to get infos of
         * 3. get the infos
         * 4. display them
         * 5. when user changes his or her location, update the info
         */
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getInfoOnMap = new AsyncGetInfoOnMap(this, token);
        getInfoOnMap.execute();
    }

    @Override
    public void processFinish(JSONArray result) {
        if (result != null) {
            for (int i = 0; i < result.length(); i++) {
                String latitude = null;
                String longitude = null;
                String comment = null;
                String id = null;
                try {
                    JSONObject data = result.getJSONObject(i);
                    latitude = data.getString("latitude");
                    longitude = data.getString("longitude");
                    comment = data.getString("comment");
                    id = data.getString("id");
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception happens: " + e.getCause());
                }
                // A posting data has to have location data
                if(isDouble(latitude) && isDouble(longitude)) {
                    LatLng data = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    mMap.addMarker(new MarkerOptions().position(data).title(comment)).setTag(id);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(data));
                    mMap.setOnMarkerClickListener(new MarkerClickListener(this));
                }
            }
        }
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch(NumberFormatException e) {
            Log.e(TAG, "Number Format Exception. LatLng from DB could not be converted into number");
            Log.e(TAG, e.getMessage());
            return false;
        }
    }
}
