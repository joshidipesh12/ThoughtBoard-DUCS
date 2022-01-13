package du.ducs.messageboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import du.ducs.messageboard.adapter.MessageTileAdapter
import du.ducs.messageboard.databinding.FragmentHomeScreenBinding
import du.ducs.messageboard.model.Message

class HomeScreenFragment : Fragment() {

    // The view binding to access views.
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageViewModel by viewModels()
    private lateinit var adapter: MessageTileAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initMessages(100)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        val recyclerView = binding.recyclerView

        adapter = MessageTileAdapter(messages)
        viewModel.newMessages.observe(viewLifecycleOwner) { newMessages ->
            processNewMessages(
                newMessages
            )
        }

        recyclerView.adapter = adapter

        binding.newMsgButton.setOnClickListener {
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToNewMessageFragment()
            findNavController().navigate(action)
        }
    }

    private fun processNewMessages(newMessages: MutableList<Message>) {
        val actuallyNewMessages = newMessages.minus(messages)
        messages.addAll(actuallyNewMessages)
        adapter.notifyItemRangeInserted(messages.size - 1, actuallyNewMessages.size)
        binding.recyclerView.scrollToPosition(messages.size - 1)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_home_screen_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_us -> {
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToAboutUsFragment()
                findNavController().navigate(action)
            }
            R.id.saved_messages -> {
                // TODO
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "HomeScreenFragment"
    }

}