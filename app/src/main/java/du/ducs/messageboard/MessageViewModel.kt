package du.ducs.messageboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import du.ducs.messageboard.model.Message
import java.util.*

class MessageViewModel : ViewModel() {
    val user = Firebase.auth.currentUser
    private val db = Firebase.firestore

    // Instance of the current logged-in user, if any.
    val currentUser: FirebaseUser?
        get() = user

    private val _newMessages = MutableLiveData<MutableList<Message>>()
    // observe this variable to get new message updates
    val newMessages: LiveData<MutableList<Message>>
        get() = _newMessages

    fun sendMessage(message: String) {
        // Create message object from user information and provided values.
        if(user?.email?.isNotBlank() == true){
            val msg = Message(
                message = message,
                userId = user.displayName, emailId = user.email
            )

            db.collection(COLLECTION)
                .add(msg.toHashMap())
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Document added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e -> Log.d(TAG, "Error adding document", e) }
        }
    }

    fun deleteMessage(msgId: String) {
        db.collection(COLLECTION).document(msgId).delete()
            .addOnSuccessListener { Log.d(TAG, "Document successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun initMessages(msgCount: Int) {
        db.collection(COLLECTION)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .limit(msgCount.toLong())
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.d(TAG, "listen failed", e)
                    return@addSnapshotListener
                }

                val newMessageList = mutableListOf<Message>()
                for (doc in snapshot!!.documentChanges) {
                    if (doc.type == DocumentChange.Type.ADDED) {
                        val message = doc.document.toObject<Message>()
                        message.id = doc.document.id
                        newMessageList.add(message)
                    }
                }

                _newMessages.value = newMessageList

                Log.d(TAG, "Messages: ${_newMessages.value}")
            }
    }

    private fun getDayTwoTimeStamp(calendar: Calendar): Long {
        val newCalendar = calendar.clone() as Calendar
        newCalendar.add(Calendar.DATE,1)
        Log.d(TAG, "Time 2: ${newCalendar.timeInMillis}")
        return newCalendar.timeInMillis
    }

    private fun getDayOneTimeStamp(calendar: Calendar): Long {
        Log.d(TAG, "Time 1: ${calendar.timeInMillis}")
        return calendar.timeInMillis
    }

    companion object {
        const val TAG = "MessageViewModel"
        const val COLLECTION = "messages"
    }

}