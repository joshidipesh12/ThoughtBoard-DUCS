package du.ducs.messageboard.model

import java.util.*
import kotlin.collections.HashMap

data class Message(
    var id: String? = null,
    var message: String? = null,
    var timestamp: Long = Calendar.getInstance().timeInMillis,
    var userId: String? = null,
    var emailId: String? = null
) {
    // Create hashMap of data with id removed
    // id is not to be stored in document
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "message" to (message ?: ""),
            "timestamp" to timestamp,
            "userId" to (userId ?: ""),
            "emailId" to (emailId ?: "")
        )
    }
}