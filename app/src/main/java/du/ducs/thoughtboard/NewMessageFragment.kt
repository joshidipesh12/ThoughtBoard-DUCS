package du.ducs.thoughtboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NewMessageFragment : Fragment() {

    private val viewModel : NewMessageViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: AppCompatActivity = (activity as AppCompatActivity?)!!

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawable_back)
        activity.supportActionBar!!.title = ""

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_message, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //onClick for back
            android.R.id.home ->{
                navigateBack()
            }

            //onClick for send
            R.id.send -> {
                val titleEditText = view?.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.titleEditText)
                val newMessageEditText = view?.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.newMessageEditText)

                if(titleEditText?.text.toString().isNotBlank() && newMessageEditText?.text.toString().isNotBlank()) {
                    onClickSendDialog()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun onClickSendDialog() {
        MaterialAlertDialogBuilder((activity as AppCompatActivity?)!!)
            .setTitle("Are you sure, you want to send message ?")
            .setCancelable(true)
            .setPositiveButton("Yes") {
                    _,_ -> sendMessage()
            }
            .show()
    }

    private fun  sendMessage() {
        val titleEditText = view?.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.titleEditText)
        val newMessageEditText = view?.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.newMessageEditText)

        viewModel.sendMessage(titleEditText.toString(), newMessageEditText.toString())
       navigateBack()

    }
private fun navigateBack(){
    findNavController().navigate(R.id.action_newMessageFragment_to_homeScreenFragment)
}
}