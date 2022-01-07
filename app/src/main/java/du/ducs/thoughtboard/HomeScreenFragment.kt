package du.ducs.thoughtboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import du.ducs.thoughtboard.adapter.ItemAdapter
import du.ducs.thoughtboard.data.DummyDataSource
import java.text.SimpleDateFormat
import java.util.*


class HomeScreenFragment : Fragment(), DatePicker.OnDateChangedListener,
    DatePickerDialog.OnDateSetListener, RecyclerView.OnItemTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        //This is just for now when we will work with data we'll set it to last date of data
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        sdf.applyPattern("EEEE, dd MMM yyyy")
        val sMyDate: String = sdf.format(Calendar.getInstance().time)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate

        //TODO(Use ViewModel Data Here)
        val myDataset = DummyDataSource().loadDummyMessages()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = object : GridLayoutManager((activity as AppCompatActivity?)!!, 2){
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams) : Boolean {
                lp.height = (view.width / 2.3).toInt()
                return true
            }
        }
        recyclerView.adapter = ItemAdapter(this, myDataset)
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
            (activity as AppCompatActivity?)!!,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) )

        datePickerDialog.show()
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year,monthOfYear,dayOfMonth)

        val sdf = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
        val sMyDate: String = sdf.format(calendar.time)

        (activity as AppCompatActivity?)!!.supportActionBar?.title = sMyDate
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


        val calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)

        val sdf = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH)
        val sMyDate: String = sdf.format(calendar.time)

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