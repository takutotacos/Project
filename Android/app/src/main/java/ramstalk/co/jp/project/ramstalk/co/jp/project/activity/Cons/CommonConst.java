package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Cons;

/**
 * Created by sugitatakuto on 2016/12/01.
 */
public class CommonConst {

    public class FileName {

        public static final String SHARED_PREFERENCES = "Project";

    }

    public class ActivityName {

        public static final String TAG_LOGIN_ACTIVITY = "LoginActivity";

        public static final String TAG_MAPS_ACTIVITY = "MapsActivity";

        public static final String TAG_POSTING_ACTIVITY = "PostingActivity";

        public static final String TAG_MAIN_ACTIVITY = "MainActivtity";

        public static final String TAG_SHOW_IMAGE_ACTIVITY = "ShowImageActivity";

        public static final String TAG_HOME_ACTIVITY = "HomeActivity";

        public static final String TAG_TIME_LINE_ACTIVITY = "TimeLineActivity";

        public static final String TAG_NOTIFICATION_ACTIVITY = "NotificationActivity";

        public static final String TAG_ADD_FOLLOWING_ACTIVITY = "AddFollowingActivity";

        public static final String TAG_LIST_FOLLOWINGS_ACTIVITY = "ListFollowingsActivity";

        public static final String TAG_LIST_FOLLOWERS_ACTIVITY = "ListFollowersActivity";
    }

    public static class Api {
        public static final String LOCAL_SERVER_FOR_RX = "http://10.0.2.2:3000/api/";
    }

    public class ApiAction {
        public static final String SHOW = "1";

        public static final String CREATE = "CREATE";

        public static final String INDEX = "INDEX";

        public static final String GET_FOLLOWINGS = "4";

        public static final String GET_FOLLOWERS = "5";

        public static final String LIKE_USER_QUERY = "6";

        public static final String ADD_FOLLOWING = "7";

        public static final String LIKE_A_POST = "8";

        public static final String DELETE_A_LIKE = "9";
    }

    public class StatusOfUser {
        public static final String IS_USER_REMEMBERED = "isUserRemembered";
    }

    public class ApiResponse {
        public static final String NOT_EXISTS = "-1";

        public static final String EXISTS = "1";

        public static final String REGISTER_SUCCESSFUL = "2";

        public static final String REGISTER_FAILED = "3";

        public static final String NULL = "null";

        public static final String ADD_SUCCESS = "4";

        public static final String ADD_FAILED = "5";
    }

    public class ApiKey {
        public static final String GOOLGLE_API_KEY = "AIzaSyAPQgLrviezS8Phw0oKzPQ47tu6G-7Z4FM\t";
    }

}
