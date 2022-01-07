package du.ducs.thoughtboard

import android.R.attr
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import du.ducs.thoughtboard.data.User
import du.ducs.thoughtboard.data.UserDao
import du.ducs.thoughtboard.databinding.ActivityMessageScreenBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import android.content.Intent

import android.R.attr.button
import android.widget.ImageButton


class MessageScreenActivity : AppCompatActivity() {

    val dummy_title =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Neque sed egestas aliquam"
    val dummy_message =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Porta quis mauris metus, vel ut amet sed nunc. Facilisis est sit volutpat hac platea purus elementum, id. Aliquam morbi lobortis augue viverra. Eget velit venenatis tortor purus rhoncus metus.\n" +
                "Eu suspendisse libero potenti nunc aliquam. Ac fermentum et viverra tellus risus, nunc. Ultrices sed venenatis eget dui mauris nulla quis fermentum. Mattis ac arcu tristique praesent cum feugiat gravida et ut. Egestas ullamcorper mauris fringilla et interdum feugiat pretium erat volutpat. Tristique purus morbi non pharetra orci. Vitae, risus mauris pretium tortor in consectetur. Tellus sem lectus dui orci facilisis amet."
    val dummy_name = "Rishi Sharma"
    val dummy_email = "rishi.sharma115@gmail.com"
    private lateinit var userDao: UserDao


    private lateinit var binding: ActivityMessageScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //for email_button to open gmail
        val emailButton = findViewById<ImageButton>(R.id.email_button)
        emailButton.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            // set email to dummy_email and title to reply on dummy_title
            emailIntent.putExtra(Intent.EXTRA_EMAIL, dummy_email)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, dummy_title);
            startActivity(emailIntent)
        })

        binding.messageTitle.text = R.string.dummy_title.toString()
        binding.messageAuthor.text = R.string.dummy_author.toString()
        binding.messageContent.text = "${R.string.dummy_message} ${R.string.dummy_message}"


        val users = runBlocking {
            userDao.getUsers().first()
        }
        // change visibility if user.email == dummy.email
        if (users.isNotEmpty()) {
            val userEmail = users[1].email
            if (userEmail == dummy_email) {
                binding.emailButton.visibility = View.GONE
                binding.deleteButton.visibility = View.VISIBLE
            } else {
                binding.emailButton.visibility = View.VISIBLE
                binding.deleteButton.visibility = View.GONE
            }
        }
        // change visibility if post.email == user.email

    }
}