package du.ducs.thoughtboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import du.ducs.thoughtboard.databinding.FragmentNewMessageBinding
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


class NewMessageFragment : Fragment() {
    // The view binding to access views.
    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity: AppCompatActivity = (activity as AppCompatActivity?)!!

        activity.setSupportActionBar(binding.toolbar)
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
            //onClick for send
            R.id.send -> {
                binding.titleEditText.clearFocus()
                binding.newMessageEditText.clearFocus()
                val title   = binding.titleEditText.text.toString()
                val message = binding.newMessageEditText.text.toString()

                if(title.isNotBlank() && message.isNotBlank()) {
                    MaterialAlertDialogBuilder((activity as AppCompatActivity?)!!)
                        .setTitle(R.string.confirm_send_dialog_msg)
                        .setCancelable(true)
                        .setPositiveButton("Yes") {
                                _, _ ->
                            run {
                                viewModel.sendMessage(title, message)
                                findNavController().navigateUp()
                            }
                        }
                        .show()
                } else {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Title & Message Can't Be Empty!!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}