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

        public static final String TAG_REGIST_USER_ACTIVITY = "RegistUserActivity";

        public static final String TAG_SHOW_IMAGE_ACTIVITY = "ShowImageActivity";

        public static final String TAG_LOGIN_FB_FRAGMENT = "LoginFBFragment";

        public static final String TAG_HOME_ACTIVITY = "HomeActivity";

        public static final String TAG_WALL_ACTIVITY = "WallActivity";

        public static final String TAG_NOTIFICATION_ACTIVITY = "NotificationActivity";

    }

    public class UrlForPhp {

        private static final String APACHE_SERVER_IP = "http://10.0.2.2/";

        public static final String LOGIN_NORMAL_PHP = APACHE_SERVER_IP + "login_normal_way.php";

        public static final String GET_IMAGE_DATA = APACHE_SERVER_IP + "get_image_data.php";

        public static final String POSTING_INFO = APACHE_SERVER_IP + "posting_image.php";

        public static final String POST_USER = APACHE_SERVER_IP + "posting_user.php";

        public static final String SEARCH_USER = APACHE_SERVER_IP + "search_user.php";

        public static final String GET_IMAGE_DETAILED_DATA = APACHE_SERVER_IP + "get_image_detailed_data.php";

    }

    public class Api {
        private static final String LOCAL_SERVER = "http://10.0.2.2:3000/api";

        public static final String LOGIN = LOCAL_SERVER + "/authenticate";

        // APIs for users
        public static final String REGISTER_USER = LOCAL_SERVER + "/users";

        public static final String SEARCH_USER = LOCAL_SERVER + "/users/";

        // APIs for postings
        public static final String MAKE_A_POST = LOCAL_SERVER + "/postings";

        public static final String GET_ALL_POSTINGS_ON_MAP = LOCAL_SERVER + "/postings";

        public static final String GET_A_DETAILED_POSTING = LOCAL_SERVER + "/postings";

        // APIs for categories
        public static final String GET_ALL_CATEGORIES = LOCAL_SERVER + "/categories";
    }

    public class ApiAction {
        public static final String SHOW = "SHOW";

        public static final String CREATE = "CREATE";

        public static final String INDEX = "INDEX";
    }

    public class StatusOfUser {
        public static final String IS_USER_REMEMBERED = "isUserRemembered";
    }

    public class ApiResponse {
        public static final String USER_NOT_EXISTS = "-1";

        public static final String USER_EXISTS = "1";

        public static final String REGISTER_SUCCESSFUL = "2";

        public static final String REGISTER_FAILED = "3";
    }
}
