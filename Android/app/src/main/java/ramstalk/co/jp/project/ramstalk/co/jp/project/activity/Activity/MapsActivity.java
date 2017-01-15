package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Activity;

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
    private static String TAG = CommonConst.ActivityName.TAG_MAPS_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        AsyncGetInfoOnMap getInfoOnMap = new AsyncGetInfoOnMap(this, this, googleMap);
        getInfoOnMap.execute();
    }

    @Override
    public void processFinish(JSONArray result) {
        if (result != null) {
            for (int i = 0; i < result.length(); i++) {
                double latitude = 0.0;
                double longitude = 0.0;
                String comment = null;
                String userId = null;
                String createDate = null;
                try {
                    JSONObject data = result.getJSONObject(i);
                    latitude = data.getDouble("latitude");
                    longitude = data.getDouble("longitude");
                    comment = data.getString("comment");
                    userId = data.getString("user_id");
                    createDate = data.getString("create_date");
//                    imgInfo = data.getString("img_info");
//                    if (!"".equals(imgInfo)) {
//                        // @Todo Do some research what this does.
//                        byte[] decodedString = Base64.decode(imgInfo, Base64.DEFAULT);
//                        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                       mMap.addMarker(new MarkerOptions().position(data).title(comment).icon(BitmapDescriptorFactory.fromBitmap(decodedByte)));
//                    }
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception happens: " + e.getCause());
                }
                LatLng data = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(data).title(comment));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(data));
                mMap.setOnMarkerClickListener(new MarkerClickListener(this, userId, createDate));
            }
        }
    }
}
