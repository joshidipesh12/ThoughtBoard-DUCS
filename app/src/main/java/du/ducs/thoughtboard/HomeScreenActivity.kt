package du.ducs.thoughtboard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HomeScreenActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener{

    private lateinit var recyclerView: RecyclerView
    private var dataList = mutableListOf<DataModel>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //This is just for now when we will work with data we'll set it to last date of data
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        sdf.applyPattern("EEEE, dd MMM yy")
        val sMyDate: String = sdf.format(Calendar.getInstance().time)
        supportActionBar?.title = sMyDate

        val emptyImage: ImageView = findViewById(R.id.no_message_image)
        val emptyText1: TextView = findViewById(R.id.no_message_text_1)
        val emptyText2: TextView = findViewById(R.id.no_message_text_2)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(applicationContext,2)

        // TODO (fix recyclerView design with dummy lorem ipsum data)
        // here the array is of Object[email, username, title, message, date]
        // take example from affirmations app's DataSource
        // https://github.com/google-developer-training/android-basics-kotlin-affirmations-app-solution
        val email = " "
        val message = " "
        val username = " "
        val array = arrayOf(email, message, username, title, sMyDate)
        if (array.isEmpty())
        {
            emptyImage.visibility = View.VISIBLE
            emptyText1.visibility = View.VISIBLE
            emptyText2.visibility = View.VISIBLE
        }
        dataList.add(DataModel("title","message"))
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.layout_home_screen_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showDatePickerDialog()
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog()  {
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) )

        datePickerDialog.show()
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {


        val calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)

        val sdf = SimpleDateFormat("EEEE, dd MMM yy", Locale.ENGLISH)
        val sMyDate: String = sdf.format(calendar.time)

        supportActionBar?.title = sMyDate

    }



}