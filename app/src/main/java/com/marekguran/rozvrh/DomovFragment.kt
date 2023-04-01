package com.marekguran.rozvrh

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.marekguran.rozvrh.databinding.FragmentDomovBinding
import org.jsoup.Jsoup

class DomovFragment : Fragment() {

    private var binding: FragmentDomovBinding? = null
    private val tweetBodies = ArrayList<String>()
    private var connectivityReceiver: BroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDomovBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnectivity()

        // Register connectivity receiver
        connectivityReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkConnectivity()
            }
        }
        context?.registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private var isFetchingData = false
    private fun checkConnectivity() {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            // No internet connection, hide listview and show the textview and imageview
            binding?.listView?.visibility = View.GONE
            binding?.wifiOffImage?.visibility = View.VISIBLE
            binding?.wifiOffText?.visibility = View.VISIBLE
        } else {
            // Internet connection is present, show listview and hide the textview and imageview
            binding?.listView?.visibility = View.VISIBLE
            binding?.wifiOffImage?.visibility = View.GONE
            binding?.wifiOffText?.visibility = View.GONE

            // Fetch data if it's not already being fetched
            if (!isFetchingData) {
                isFetchingData = true
                fetchData()
            }
        }
    }

    private fun fetchData() {
        val url = "https://twstalker.com/zssk_mimoriadne"
        Thread {
            try {
                val doc = Jsoup.connect(url).get()
                val tweetContainers = doc.select(".activity-descp")
                for (tweetContainer in tweetContainers) {
                    val tweetElement = tweetContainer.selectFirst("p")
                    tweetBodies.add(tweetElement?.text() ?: "")
                }
                activity?.runOnUiThread {
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, tweetBodies)
                    binding?.listView?.adapter = adapter
                }
                Log.d("DomovFragment", "Data fetched successfully")
                Log.d("DomovFragment", "Number of tweets found: ${tweetBodies.size}")
                Log.d("DomovFragment", "Tweets: $tweetBodies")
                for (tweet in tweetBodies) {
                    Log.d("DomovFragment", tweet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isFetchingData = false
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

        // Unregister connectivity receiver
        connectivityReceiver?.let { context?.unregisterReceiver(it) }
    }
}
