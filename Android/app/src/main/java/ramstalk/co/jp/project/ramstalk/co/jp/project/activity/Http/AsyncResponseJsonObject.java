package ramstalk.co.jp.project.ramstalk.co.jp.project.activity.Http;

import org.json.JSONObject;

/**
 * Created by sugitatakuto on 2016/12/02.
 */
public interface AsyncResponseJsonObject {
    void processFinish(JSONObject output);
}
