package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Common;

/**
 * Created by sugitatakuto on 2017/01/22.
 */
public class AppState {

    private static AppState singleInstance;
    private boolean isLoggedOut;

    private AppState(){}

    public static AppState getSingleInstance() {
        if(singleInstance == null) {
            singleInstance = new AppState();
        }
        return singleInstance;
    }

    public boolean isLoggedOut() {
        return this.isLoggedOut;
    }

    public void setLoggedOut(boolean isLoggedOut) {
        this.isLoggedOut = isLoggedOut;
    }
}
