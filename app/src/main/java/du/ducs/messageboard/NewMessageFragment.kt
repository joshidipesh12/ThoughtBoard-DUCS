package du.ducs.messageboard

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.viewModels
import du.ducs.messageboard.databinding.FragmentNewMessageBinding

import android.view.inputmethod.InputMethodManager
import android.widget.Toast


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

    override fun onStart() {
        super.onStart()
        binding.newMessageEditText.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_message, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            //onClick for send
            R.id.send -> {
                binding.newMessageEditText.clearFocus()
                val imm = (activity as AppCompatActivity?)!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                val message = binding.newMessageEditText.text.toString()

                if(message.isNotBlank()) {
                    if(viewModel.user?.email?.isNotBlank() == true){
                        viewModel.sendMessage(message)
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(
                            view?.context,
                            "Can't Send Messages in Audit Mode!\nPlease SignUp with DUCS Email.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        view?.context,
                        "Message Can't Be Empty!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}