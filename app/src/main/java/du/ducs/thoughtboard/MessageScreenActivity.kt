package du.ducs.thoughtboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import du.ducs.thoughtboard.databinding.ActivityMessageScreenBinding

class MessageScreenActivity : AppCompatActivity() {

    val dummy_title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Neque sed egestas aliquam"
    val dummy_message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Porta quis mauris metus, vel ut amet sed nunc. Facilisis est sit volutpat hac platea purus elementum, id. Aliquam morbi lobortis augue viverra. Eget velit venenatis tortor purus rhoncus metus.\n" +
            "Eu suspendisse libero potenti nunc aliquam. Ac fermentum et viverra tellus risus, nunc. Ultrices sed venenatis eget dui mauris nulla quis fermentum. Mattis ac arcu tristique praesent cum feugiat gravida et ut. Egestas ullamcorper mauris fringilla et interdum feugiat pretium erat volutpat. Tristique purus morbi non pharetra orci. Vitae, risus mauris pretium tortor in consectetur. Tellus sem lectus dui orci facilisis amet."
    val dummy_name = "Rishi Sharma"
    val dummy_email = "rishi.sharma115@gmail.com"

    private lateinit var binding: ActivityMessageScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.messageTitle.text = dummy_title
        binding.messageAuthor.text = dummy_name
        binding.messageContent.text = "$dummy_message $dummy_message"

        // change visibility if post.email == user.email
        binding.emailButton.visibility = View.VISIBLE
        binding.deleteButton.visibility = View.GONE
    }
}