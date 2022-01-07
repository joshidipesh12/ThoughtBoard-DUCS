package du.ducs.thoughtboard.data

import du.ducs.thoughtboard.R
import du.ducs.thoughtboard.model.Message

class DummyDataSource() {

    fun loadDummyMessages(): List<Message> {
        return listOf<Message>(
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author),
            Message(R.string.dummy_title, R.string.dummy_message, R.string.dummy_author))
    }
}

