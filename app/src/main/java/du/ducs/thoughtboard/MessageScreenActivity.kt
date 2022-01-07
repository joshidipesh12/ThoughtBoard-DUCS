package du.ducs.thoughtboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import du.ducs.thoughtboard.databinding.ActivityMessageScreenBinding

class MessageScreenActivity : AppCompatActivity() {

    val dummy_email = "rishi.sharma115@gmail.com"

    private lateinit var binding: ActivityMessageScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.messageTitle.text = R.string.dummy_title.toString()
        binding.messageAuthor.text = R.string.dummy_author.toString()
        binding.messageContent.text = "${R.string.dummy_message} ${R.string.dummy_message}"

        // change visibility if post.email == user.email
        binding.emailButton.visibility = View.VISIBLE
        binding.deleteButton.visibility = View.GONE
    }
}