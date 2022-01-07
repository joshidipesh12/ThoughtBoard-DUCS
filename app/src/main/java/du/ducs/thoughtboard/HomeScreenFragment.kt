package du.ducs.thoughtboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import du.ducs.thoughtboard.adapter.MessageTileAdapter
import java.text.SimpleDateFormat
import java.util.*


class HomeScreenFragment : Fragment(), DatePicker.OnDateChangedListener,
    DatePickerDialog.OnDateSetListener, RecyclerView.OnItemTouchListener {

    private val viewModel: MessageViewModel by viewModels()
    private lateinit var adapter: MessageTileAdapter

    // Date formatter for the action bar title.
    private val formatter = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.ENGLISH)

    // The calendar instance maintaining the current selected date.
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        val sMyDate = formatter.format(calendar.time)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = object : GridLayoutManager((activity as AppCompatActivity?)!!, 2){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams) : Boolean {
                lp.height = (view.width / 2.3).toInt()
                return true
            }
        }

        adapter = MessageTileAdapter()
        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_home_screen_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showDatePickerDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog()  {
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

}