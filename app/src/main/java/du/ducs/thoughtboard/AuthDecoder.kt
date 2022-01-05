package du.ducs.thoughtboard

import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets

object AuthDecoder {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String): Boolean {
        try {
            val split = JWTEncoded.split("\\.").toTypedArray()
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
            val jsonObj = JSONObject(getJson(split[1]))
            val name = jsonObj.getString("name")
            val email = jsonObj.getString("email")
            return email.contains("cs.du.ac.in")
        } catch (e: UnsupportedEncodingException) {
            //Error
        }
        return false
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, StandardCharsets.UTF_8)
    }
}