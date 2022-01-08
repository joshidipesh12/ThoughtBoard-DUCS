package du.ducs.thoughtboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import du.ducs.thoughtboard.data.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MessageScreenFragment : Fragment() {

    private lateinit var userDao: UserDao
    private var isAuthor = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_screen, container, false)
    }

    @SuppressLint("SetTextI18n")
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

        val message = view.resources.getString(R.string.dummy_message)
        titleTextView.text =  view.resources.getString(R.string.dummy_title)
        authorTextView.text = view.resources.getString(R.string.dummy_author)
        messageTextView.text = message + message

        val users =  runBlocking {
            userDao.getUsers().first()
        }
        if(users.isNotEmpty()){
            isAuthor = users[0].email === view.resources?.getString(R.string.dummy_email)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_message_screen, menu)
        val menuOption = menu.getItem(0)

        if(isAuthor){
            menuOption.setIcon(R.drawable.ic_delete)
        } else {
            menuOption.setIcon(R.drawable.alternate_email__2_)
        }
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(isAuthor){
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            // set email to dummy_email and title to reply on dummy_title
            emailIntent.putExtra(Intent.EXTRA_EMAIL, view?.resources?.getString(R.string.dummy_email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Re: " + view?.resources?.getString(R.string.dummy_title))
            startActivity(emailIntent)
        } else {
            TODO("Implement message delete action dispatch")
        }

        return super.onOptionsItemSelected(item)
    }

}


