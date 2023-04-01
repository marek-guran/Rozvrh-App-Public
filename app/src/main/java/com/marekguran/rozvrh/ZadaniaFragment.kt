package com.marekguran.rozvrh

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.marekguran.rozvrh.databinding.FragmentHodnoteniaBinding
import com.marekguran.rozvrh.databinding.FragmentZadaniaBinding

class ZadaniaFragment : Fragment() {
    private var binding: FragmentZadaniaBinding? = null
    private lateinit var databaseZadania: DatabaseZadania
    private lateinit var listViewAdapter: CustomArrayAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentZadaniaBinding.inflate(inflater, container, false)

        val connectivityButton = binding?.connectivityButton
        connectivityButton?.setOnClickListener {
            checkConnectivity()
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize databaseMarks
        databaseZadania = DatabaseZadania(requireContext())

        // use the custom adapter and pass the custom layout resource ID
        listViewAdapter = CustomArrayAdapter(
                requireContext(),
                R.layout.hodnotenie,
                databaseZadania.getAllData()
        )

        databaseZadania.listenForDataChanges()
        handler.postDelayed(refreshRunnable, 5000) // Start the runnable to refresh the list view every 5 seconds
        binding?.listZadania?.adapter = listViewAdapter
    }

    private val handler = Handler(Looper.getMainLooper())
    private val refreshRunnable = object : Runnable {
        override fun run() {
            listViewAdapter.clear()
            databaseZadania.listenForDataChanges()
            listViewAdapter.addAll(databaseZadania.getAllData())
            listViewAdapter.notifyDataSetChanged()

            handler.postDelayed(this, 5000) // Run this runnable again after 5 seconds
        }
    }

    override fun onResume() {
        super.onResume()

        // Update the list view adapter with the latest data from the database
        listViewAdapter.clear()
        listViewAdapter.addAll(databaseZadania.getAllData())
        listViewAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(refreshRunnable)
        binding = null
    }
    private fun checkConnectivity() {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            // No internet connection, show toast
            Toast.makeText(requireContext(), "Bez pripojenia na internet", Toast.LENGTH_LONG).show()
        } else {
            // Internet connection is present, show updated listview
            databaseZadania.cleanLocalDatabase()
            databaseZadania.listenForDataChanges()
            Toast.makeText(requireContext(), "Obnovené", Toast.LENGTH_LONG).show()
        }
    }

    // Custom adapter that inflates a custom layout for each item in the ListView
    class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
            ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.hodnotenie, parent, false)
            }
            val textViewItem: TextView = view!!.findViewById(R.id.hodnotenie_list)

            // Split the string into two parts, separated by ":"
            val predmet = getItem(position)!!.split(":")
            val firstPart = predmet[0].trim()
            val secondPartItems = predmet[1].trim().split(", ")

            // Set the first part in size 24sp and bold
            val firstPartText = "<b><big>$firstPart</big></b>"

            // Set the second part with different text sizes for different items
            val secondPartText = secondPartItems.joinToString("<br>") {
                "<font size='${when (it) {
                    secondPartItems[0] -> 18
                    else -> 14
                }}sp'>$it</font>"
            }

            // Combine the first and second parts
            val predmet_text = "$firstPartText<br>$secondPartText"
            textViewItem.text = HtmlCompat.fromHtml(predmet_text, HtmlCompat.FROM_HTML_MODE_LEGACY)

            return view
        }

    }

}