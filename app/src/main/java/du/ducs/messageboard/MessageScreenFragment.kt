package du.ducs.messageboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import du.ducs.messageboard.data.UserDao


class MessageScreenFragment : Fragment() {

    private lateinit var userDao: UserDao
    private var isAuthor = false

    private val args: MessageScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: AppCompatActivity = (activity as AppCompatActivity?)!!

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_top)
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawable_back)
        activity.supportActionBar!!.title = ""

        val titleTextView = view.findViewById<TextView>(R.id.message_title)
        val authorTextView = view.findViewById<TextView>(R.id.message_author)
        val messageTextView = view.findViewById<TextView>(R.id.message_content)

        titleTextView.text =  args.title
        authorTextView.text = args.name
        messageTextView.text = args.message

//        val users =  runBlocking {
//            userDao.getUsers().first()
//        }
//        if(users.isNotEmpty()){
//            isAuthor = users[0].email === view.resources?.getString(R.string.dummy_email)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_message_screen, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send_mail_to_author -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(args.email))
                val emailTitle = if (args.title.isNotBlank()) args.title else args.message
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Re: $emailTitle")
                startActivity(emailIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


