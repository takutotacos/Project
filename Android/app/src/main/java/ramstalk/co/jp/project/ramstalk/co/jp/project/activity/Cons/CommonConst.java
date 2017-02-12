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

        public static final String TAG_TIME_LINE_ACTIVITY = "TimeLineActivity";

        public static final String TAG_NOTIFICATION_ACTIVITY = "NotificationActivity";

        public static final String TAG_ADD_FOLLOWING_ACTIVITY = "AddFollowingActivity";

        public static final String TAG_LIST_FOLLOWINGS_ACTIVITY = "ListFollowingsActivity";

        public static final String TAG_LIST_FOLLOWERS_ACTIVITY = "ListFollowersActivity";
    }

    public class Api {
        private static final String LOCAL_SERVER = "http://10.0.2.2:3000/api";

        public static final String LOGIN = LOCAL_SERVER + "/authenticate";

        // APIs for users
        public static final String REGISTER_USER = LOCAL_SERVER + "/users";

        public static final String SEARCH_USER = LOCAL_SERVER + "/like_user_id_query";

        // APIs for postings
        public static final String MAKE_A_POST = LOCAL_SERVER + "/postings";

        public static final String GET_ALL_POSTINGS_ON_MAP = LOCAL_SERVER + "/postings";

        public static final String GET_A_DETAILED_POSTING = LOCAL_SERVER + "/postings";

        public static final String GET_POSTING_BY_CATEGORIES = LOCAL_SERVER + "/postings_by_category";

        // APIs for categoriesaa
        public static final String GET_ALL_CATEGORIES = LOCAL_SERVER + "/categories";

        //APIs for relationships
        public static final String GET_FOLLOWINGS_WITH_DETAIL = LOCAL_SERVER + "/followings";

        public static final String GET_FOLLOWERS_WITH_DETAIL = LOCAL_SERVER + "/followers";

        public static final String GET_FOLLOWING_NUMBERS = LOCAL_SERVER + "/following_numbers";

        public static final String GET_FOLLOWER_NUMBERS = LOCAL_SERVER + "/follower_numbers";

        public static final String ADD_TO_FOLLOWINGS = LOCAL_SERVER + "/relationships";

    }

    public class ApiAction {
        public static final String SHOW = "1";

        public static final String CREATE = "CREATE";

        public static final String INDEX = "INDEX";

        public static final String GET_FOLLOWINGS = "4";

        public static final String GET_FOLLOWERS = "5";

        public static final String LIKE_USER_QUERY = "6";

        public static final String ADD_FOLLOWING = "7";
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

}
