package damnvuln.exercise.lab.vulntestlab.VolleyCookieFix;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonObjectRequestCookie extends JsonObjectRequest {

    // Since we're extending a Request class
    // we just use its constructor
    public JsonObjectRequestCookie(int method, String url, JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    private Map<String, String> headers = new HashMap<>();


    public void setCookies(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            sb.append(cookie).append("; ");
        }
        headers.put("Cookie", sb.toString());
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

}
