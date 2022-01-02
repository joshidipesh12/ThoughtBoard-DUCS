package du.ducs.thoughtboard

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.*

class HomeScreenActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //This is just for now when we will work with data we'll set it to last date of data
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        sdf.applyPattern("EEEE, dd MMM yy")
        val sMyDate: String = sdf.format(Calendar.getInstance().time)
        supportActionBar?.title = sMyDate


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