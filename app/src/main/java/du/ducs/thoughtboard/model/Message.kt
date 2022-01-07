package du.ducs.thoughtboard.model

import com.google.firebase.Timestamp


//TODO(Use MessageViewModel Instead of this)
data class Message(
    var id: String? = null,
    var title: String? = null,
    var message: String? = null,
    var timestamp: Long = Timestamp.now().nanoseconds.toLong(),
    var userId: String? = null,
    var emailId: String? = null
) {
    // Create hashMap of data with id removed
    // id is not to be stored in document
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "title" to (title ?: ""),
            "message" to (message ?: ""),
            "timestamp" to timestamp,
            "userId" to (userId ?: ""),
            "emailId" to (emailId ?: "")
        )
    }
}