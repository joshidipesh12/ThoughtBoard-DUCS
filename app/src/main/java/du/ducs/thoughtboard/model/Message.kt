package du.ducs.thoughtboard.model

import androidx.annotation.StringRes


//TODO(Use MessageViewModel Instead of this)
data class Message(
    @StringRes val titleResourceId: Int,
    @StringRes val messageResourceId: Int,
    @StringRes val authorResourceId: Int,
)