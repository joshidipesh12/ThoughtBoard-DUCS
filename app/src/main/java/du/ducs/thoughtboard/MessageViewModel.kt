package du.ducs.thoughtboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.datetime.*
import kotlin.collections.HashMap

const val TAG = "MessageViewModel"
const val COLLECTION = "messages"

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

class MessageViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _messages = MutableLiveData<List<Message>>()

    // observe this variable to get new message updates
    val messages: LiveData<List<Message>>
        get() = _messages

    fun sendMessage(msg: Message) {
        db.collection(COLLECTION)
            .add(msg.toHashMap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e -> Log.d(TAG, "Error adding document", e) }
    }

    fun deleteMessage(msgId: String) {
        db.collection(COLLECTION).document(msgId).delete()
            .addOnSuccessListener { Log.d(TAG, "Document successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    // datetime object should have the hours, minutes, seconds set to zero
    fun setDateFiler(dateTime: LocalDateTime) {
        db.collection(COLLECTION)
            .whereGreaterThan("timestamp", getDayOneTimeStamp(dateTime))
            .whereLessThan("timestamp", getDayTwoTimeStamp(dateTime))
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.d(TAG, "listen failed", e)
                    return@addSnapshotListener
                }

                val newMessageList = mutableListOf<Message>()
                if (value != null) {
                    for (doc in value) {
                        val message = doc.toObject<Message>()
                        message.id = doc.id
                        newMessageList.add(message)
                    }
                }
                _messages.value = newMessageList
                Log.d(TAG, "Messages: ${_messages.value}")
            }
    }

    private fun getDayTwoTimeStamp(dateTime: LocalDateTime): Long {
        val timeInstant = dateTime.toInstant(TimeZone.currentSystemDefault())
        val timeInstantOneDayLater =
            timeInstant.plus(1, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
        Log.d(TAG, "Time 2: ${timeInstantOneDayLater.epochSeconds}")
        return timeInstantOneDayLater.epochSeconds
    }

    private fun getDayOneTimeStamp(dateTime: LocalDateTime): Long {
        val timeInstant = dateTime.toInstant(TimeZone.currentSystemDefault())
        Log.d(TAG, "Time 1: ${timeInstant.epochSeconds}")
        return timeInstant.epochSeconds
    }

}