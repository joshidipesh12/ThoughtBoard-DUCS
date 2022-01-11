package du.ducs.messageboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import du.ducs.messageboard.adapter.MessageTileAdapter
import java.text.SimpleDateFormat
import java.util.*
import du.ducs.messageboard.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment(), DatePicker.OnDateChangedListener,
    DatePickerDialog.OnDateSetListener, RecyclerView.OnItemTouchListener {

    // The view binding to access views.
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageViewModel by viewModels()
    private lateinit var adapter: MessageTileAdapter

    // Date formatter for the action bar title.
    private val formatter = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.ENGLISH)

    // The calendar instance maintaining the current selected date.
    private val calendar = Calendar.getInstance()

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

        val sMyDate = formatter.format(calendar.time)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate

        val recyclerView = binding.recyclerView

        adapter = MessageTileAdapter()
        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        binding.newMsgButton.setOnClickListener {
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToNewMessageFragment()
            findNavController().navigate(action)
        }

        binding.aboutButton.setOnClickListener {
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToAboutUsFragment()
            findNavController().navigate(action)
        }

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            0, 0, 0,
        )
        Log.d(TAG, "Got time: ${calendar.timeInMillis}")

        // Pass the date to the view model to initiate a load request
        viewModel.setDateFilter(calendar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_home_screen_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showDatePickerDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            activity!!, this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(year, monthOfYear, dayOfMonth)

        val sMyDate = formatter.format(calendar.time)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)

        val sMyDate = formatter.format(calendar.time)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate

        viewModel.setDateFilter(calendar)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        // try avoiding this implementation
        return true
    }

    // item click listener
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // try avoiding this implementation
    }

    companion object {
        const val TAG = "HomeScreenFragment"
    }

}