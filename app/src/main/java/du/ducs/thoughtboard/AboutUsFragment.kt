package du.ducs.thoughtboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import android.net.Uri


class AboutUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val websiteLink = view.findViewById<TextView>(R.id.visit_website)
        websiteLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://cs.du.ac.in"))
            startActivity(i)
        }
    }


}