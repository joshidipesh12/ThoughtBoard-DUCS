package du.ducs.thoughtboard;

import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AuthDecoder {
    public static boolean decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            JSONObject jsonObj = new JSONObject(getJson(split[1]));

            String name = jsonObj.getString("name");
            String email = jsonObj.getString("email");
            return email.contains("cs.du.ac.in");

        } catch (UnsupportedEncodingException e) {
            //Error
        }

        return false;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
