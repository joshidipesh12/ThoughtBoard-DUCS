package du.ducs.messageboard

import android.util.Base64
import android.util.Log
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets

object AuthDecoder {
    @Throws(Exception::class)
    fun decoded(JWTEncoded: String): Boolean {
        try {
            val payload = JWTEncoded.split(".")[1]
            val jsonObj = JSONObject(getJson(payload))
            val name = jsonObj.getString("name")
            val email = jsonObj.getString("email")
            Log.d("JWT_DECODER", "Name: $name, Email: $email")
            return email.endsWith("@cs.du.ac.in") && name.isNotEmpty()
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