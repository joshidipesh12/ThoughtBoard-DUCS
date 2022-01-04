package du.ducs.thoughtboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

const val TAG = "MessageViewModel"
const val COLLECTION = "messages"

data class Message(
    var id: String? = null,
    var title: String? = null,
    var message: String? = null,
    var timestamp: Timestamp = Timestamp.now(),
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

class MessageViewModel: ViewModel() {

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

    fun setDateFiler(date: Date) {
        db.collection(COLLECTION)
//            .whereGreaterThan("timestamp", getDayEndTimeStamp())
//            .whereLessThan("timestamp", getDayStartTimeStamp())
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
            }
    }

    private fun getDayEndTimeStamp(): Timestamp {
        return Timestamp.now()
    }

    private fun getDayStartTimeStamp(): Timestamp {
        return Timestamp.now()
    }

}