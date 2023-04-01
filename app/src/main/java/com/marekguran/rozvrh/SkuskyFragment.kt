package com.marekguran.rozvrh

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marekguran.rozvrh.databinding.FragmentSkuskyBinding
import kotlinx.coroutines.delay

class SkuskyFragment : Fragment() {
    private var binding: FragmentSkuskyBinding? = null
    private var database: FirebaseDatabase? = null
    private lateinit var obedyListView: ListView
    private lateinit var obedyAdapter: ArrayAdapter<String>
    private var obedyList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkuskyBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        obedyListView = binding!!.obedy
        checkConnectivity()
        // Create the adapter and bind it to the ListView
        obedyAdapter = CustomArrayAdapter(requireContext(), R.layout.obedy, obedyList) // Use the CustomArrayAdapter here
        obedyListView.adapter = obedyAdapter // Use the CustomArrayAdapter here
        // Retrieve "obed" data from Firebase and update the ListView with the retrieved data
        val obedyRef = database!!.getReference("obedy").child("obed")
        obedyRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                obedyList.clear()
                for (snapshot in dataSnapshot.children) {
                    val obed = snapshot.getValue(String::class.java)
                    obed?.let { obedyList.add(it) }
                }
                obedyAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that may occur while retrieving the data
                // For example, you could log the error message using Log.e()
            }
        })

        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                try {
                    // Check if the database variable is not null
                    if (database != null) {
                        // Execute the code that retrieves the data from Firebase when the network connection is available
                        val stavKontaRef = database!!.getReference("obedy").child("stavKonta")
                        stavKontaRef.get().addOnSuccessListener { dataSnapshot ->
                            val stavKonta = if (dataSnapshot.value != null) {
                                dataSnapshot.value as String
                            } else {
                                "Bez pripojenia na internet"
                            }
                            binding!!.stavKonta.text = "Stav Konta: $stavKonta"
                        }.addOnFailureListener {
                            // Handle any errors that may occur while retrieving the data
                            // For example, you could log the error message using Log.e()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error occurred while retrieving data from Firebase: ${e.message}")
                }
            }

            override fun onLost(network: Network) {
                // Handle the case when the network connection is lost
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)



        val connectivityButton = binding?.connectivityButton
        connectivityButton?.setOnClickListener {
            checkConnectivity()
        }

        return binding?.root
    }

    private fun checkConnectivity() {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            // No internet connection, hide listview and show the textview and imageview
            binding?.obedy?.visibility = View.GONE
            binding?.wifiOffImage?.visibility = View.VISIBLE
            binding?.wifiOffText?.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Bez pripojenia na internet", Toast.LENGTH_LONG).show()
        } else {
            // Internet connection is present, show listview and hide the textview and imageview
            binding?.obedy?.visibility = View.VISIBLE
            binding?.wifiOffImage?.visibility = View.GONE
            binding?.wifiOffText?.visibility = View.GONE
            Toast.makeText(requireContext(), "Obnovené", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        database = null
    }
    class CustomArrayAdapter(context: Context, resource: Int, objects: List<String>) :
        ArrayAdapter<String>(context, resource, objects) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.obedy, parent, false)
            }
            val textViewItem: TextView = view!!.findViewById(R.id.obedy_list)

            // Split the string into two parts, separated by ":"
            val parts = getItem(position)?.split(":") ?: listOf("")
            val firstPart = parts.getOrNull(0)?.trim() ?: ""
            val secondPart = parts.getOrNull(1)?.trim() ?: ""


            // Set the first part in normal text, and the second part in bold and green
            val text = "<b>$firstPart:</b> $secondPart</font>"
            textViewItem.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

            return view
    }


}
}
