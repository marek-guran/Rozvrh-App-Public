package com.marekguran.rozvrh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import com.marekguran.rozvrh.databinding.FragmentRozvrhBinding
import java.text.SimpleDateFormat
import java.util.*

class RozvrhFragment : Fragment() {
    private var binding: FragmentRozvrhBinding? = null
    private lateinit var btnMonday: Button
    private lateinit var btnTuesday: Button
    private lateinit var btnWednesday: Button
    private lateinit var btnThursday: Button
    private lateinit var btnFriday: Button
    private lateinit var rozvrhPondelok: ScrollView
    private lateinit var rozvrhUtorok: ScrollView
    private lateinit var rozvrhStreda: ScrollView
    private lateinit var rozvrhStvrtok: ScrollView
    private lateinit var rozvrhPiatok: ScrollView

    private val dateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRozvrhBinding.inflate(
            inflater,
            container,
            false
        )
        btnMonday = binding!!.root.findViewById(R.id.btnMonday)
        btnTuesday = binding!!.root.findViewById(R.id.btnTuesday)
        btnWednesday = binding!!.root.findViewById(R.id.btnWednesday)
        btnThursday = binding!!.root.findViewById(R.id.btnThursday)
        btnFriday = binding!!.root.findViewById(R.id.btnFriday)
        rozvrhPondelok = binding!!.root.findViewById(R.id.rozvrhPondelok)
        rozvrhUtorok = binding!!.root.findViewById(R.id.rozvrhUtorok)
        rozvrhStreda = binding!!.root.findViewById(R.id.rozvrhStreda)
        rozvrhStvrtok = binding!!.root.findViewById(R.id.rozvrhStvrtok)
        rozvrhPiatok = binding!!.root.findViewById(R.id.rozvrhPiatok)

        val calendar = Calendar.getInstance()
        val dayOfWeek = dateFormat.format(calendar.time)

        when (dayOfWeek) {
            "Monday" -> {
                rozvrhPondelok.visibility = View.VISIBLE
                rozvrhUtorok.visibility = View.GONE
                rozvrhStreda.visibility = View.GONE
                rozvrhStvrtok.visibility = View.GONE
                rozvrhPiatok.visibility = View.GONE

                btnMonday.isSelected = true
                btnTuesday.isSelected = false
                btnWednesday.isSelected = false
                btnThursday.isSelected = false
                btnFriday.isSelected = false

                btnMonday.setBackgroundColor(resources.getColor(R.color.active_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            }
            "Tuesday" -> {
                rozvrhPondelok.visibility = View.GONE
                rozvrhUtorok.visibility = View.VISIBLE
                rozvrhStreda.visibility = View.GONE
                rozvrhStvrtok.visibility = View.GONE
                rozvrhPiatok.visibility = View.GONE

                btnMonday.isSelected = false
                btnTuesday.isSelected = true
                btnWednesday.isSelected = false
                btnThursday.isSelected = false
                btnFriday.isSelected = false

                btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.active_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            }
            "Wednesday" -> {
                rozvrhPondelok.visibility = View.GONE
                rozvrhUtorok.visibility = View.GONE
                rozvrhStreda.visibility = View.VISIBLE
                rozvrhStvrtok.visibility = View.GONE
                rozvrhPiatok.visibility = View.GONE

                btnMonday.isSelected = false
                btnTuesday.isSelected = false
                btnWednesday.isSelected = true
                btnThursday.isSelected = false
                btnFriday.isSelected = false

                btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.active_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            }
            "Thursday" -> {
                rozvrhPondelok.visibility = View.GONE
                rozvrhUtorok.visibility = View.GONE
                rozvrhStreda.visibility = View.GONE
                rozvrhStvrtok.visibility = View.VISIBLE
                rozvrhPiatok.visibility = View.GONE

                btnMonday.isSelected = false
                btnTuesday.isSelected = false
                btnWednesday.isSelected = false
                btnThursday.isSelected = true
                btnFriday.isSelected = false

                btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.active_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            }
            "Friday" -> {
                rozvrhPondelok.visibility = View.GONE
                rozvrhUtorok.visibility = View.GONE
                rozvrhStreda.visibility = View.GONE
                rozvrhStvrtok.visibility = View.GONE
                rozvrhPiatok.visibility = View.VISIBLE

                btnMonday.isSelected = false
                btnTuesday.isSelected = false
                btnWednesday.isSelected = false
                btnThursday.isSelected = false
                btnFriday.isSelected = true

                btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.active_day))
            }
            else -> {
                rozvrhPondelok.visibility = View.VISIBLE
                rozvrhUtorok.visibility = View.GONE
                rozvrhStreda.visibility = View.GONE
                rozvrhStvrtok.visibility = View.GONE
                rozvrhPiatok.visibility = View.GONE

                btnMonday.isSelected = true
                btnTuesday.isSelected = false
                btnWednesday.isSelected = false
                btnThursday.isSelected = false
                btnFriday.isSelected = false

                btnMonday.setBackgroundColor(resources.getColor(R.color.active_day))
                btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
                btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            }
        }


        btnMonday.setOnClickListener {
            rozvrhPondelok.visibility = View.VISIBLE
            rozvrhUtorok.visibility = View.GONE
            rozvrhStreda.visibility = View.GONE
            rozvrhStvrtok.visibility = View.GONE
            rozvrhPiatok.visibility = View.GONE

            btnMonday.isSelected = true
            btnTuesday.isSelected = false
            btnWednesday.isSelected = false
            btnThursday.isSelected = false
            btnFriday.isSelected = false

            btnMonday.setBackgroundColor(resources.getColor(R.color.active_day))
            btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
        }
        btnTuesday.setOnClickListener {
            rozvrhPondelok.visibility = View.GONE
            rozvrhUtorok.visibility = View.VISIBLE
            rozvrhStreda.visibility = View.GONE
            rozvrhStvrtok.visibility = View.GONE
            rozvrhPiatok.visibility = View.GONE

            btnMonday.isSelected = false
            btnTuesday.isSelected = true
            btnWednesday.isSelected = false
            btnThursday.isSelected = false
            btnFriday.isSelected = false

            btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnTuesday.setBackgroundColor(resources.getColor(R.color.active_day))
            btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
        }
        btnWednesday.setOnClickListener {
            rozvrhPondelok.visibility = View.GONE
            rozvrhUtorok.visibility = View.GONE
            rozvrhStreda.visibility = View.VISIBLE
            rozvrhStvrtok.visibility = View.GONE
            rozvrhPiatok.visibility = View.GONE

            btnMonday.isSelected = false
            btnTuesday.isSelected = false
            btnWednesday.isSelected = true
            btnThursday.isSelected = false
            btnFriday.isSelected = false

            btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnWednesday.setBackgroundColor(resources.getColor(R.color.active_day))
            btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
        }
        btnThursday.setOnClickListener {
            rozvrhPondelok.visibility = View.GONE
            rozvrhUtorok.visibility = View.GONE
            rozvrhStreda.visibility = View.GONE
            rozvrhStvrtok.visibility = View.VISIBLE
            rozvrhPiatok.visibility = View.GONE

            btnMonday.isSelected = false
            btnTuesday.isSelected = false
            btnWednesday.isSelected = false
            btnThursday.isSelected = true
            btnFriday.isSelected = false

            btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnThursday.setBackgroundColor(resources.getColor(R.color.active_day))
            btnFriday.setBackgroundColor(resources.getColor(R.color.inactive_day))
        }
        btnFriday.setOnClickListener {
            rozvrhPondelok.visibility = View.GONE
            rozvrhUtorok.visibility = View.GONE
            rozvrhStreda.visibility = View.GONE
            rozvrhStvrtok.visibility = View.GONE
            rozvrhPiatok.visibility = View.VISIBLE

            btnMonday.isSelected = false
            btnTuesday.isSelected = false
            btnWednesday.isSelected = false
            btnThursday.isSelected = false
            btnFriday.isSelected = true

            btnMonday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnTuesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnWednesday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnThursday.setBackgroundColor(resources.getColor(R.color.inactive_day))
            btnFriday.setBackgroundColor(resources.getColor(R.color.active_day))
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}