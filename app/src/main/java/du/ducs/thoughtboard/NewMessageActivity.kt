package du.ducs.thoughtboard

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import du.ducs.thoughtboard.databinding.ActivityNewMessageBinding


class NewMessageActivity : AppCompatActivity() {

    private val viewModel : NewMessageViewModel by viewModels()

    private lateinit var binding: ActivityNewMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //to set back button on left in Tool bar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_drawable_back)
            supportActionBar!!.title = ""
        }

    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_new_message, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            //onClick for back
            android.R.id.home ->{
                navigateBack()
            }

            //onClick for send
            R.id.send -> {
                if(binding.titleEditText.text.toString() != "" && binding.newMessageEditText.text.toString() != "") {
                    onClickSendDialog()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickSendDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Are you sure, you want to send message ?")
            .setCancelable(true)
            .setPositiveButton("Yes") {
                    _,_ -> sendMessage()
            }
            .show()
    }

    private fun  sendMessage() {
        val title = binding.titleEditText.text.toString()
        val message = binding.newMessageEditText.text.toString()
        viewModel.sendMessage(title,message)
        navigateBack()

    }

    private fun navigateBack() {
        // TODO: 04-01-2022 Implement explicit intent to go back to HomeScreen
    }


}