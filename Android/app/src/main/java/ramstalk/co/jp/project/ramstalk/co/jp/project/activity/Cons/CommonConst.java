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

        public static final String TAG_LOGIN_FB_FRAGMENT = "LoginFBFragment";

    }

    public class UrlForPhp {

        private static final String APACHE_SERVER_IP = "http://10.0.2.2/";

        public static final String LOGIN_NORMAL_PHP = APACHE_SERVER_IP + "login_normal_way.php";

        public static final String GET_IMAGE_DATA = APACHE_SERVER_IP + "get_image_data.php";

    }

    public class StatusOfUser {
        public static final String IS_USER_REMEMBERED = "isUserRemembered";
    }
}
