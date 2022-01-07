package du.ducs.thoughtboard.model

import androidx.annotation.StringRes

data class Message(
    @StringRes val titleResourceId: Int,
    @StringRes val messageResourceId: Int,
    @StringRes val authorResourceId: Int,
)