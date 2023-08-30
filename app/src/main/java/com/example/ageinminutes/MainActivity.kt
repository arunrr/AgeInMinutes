package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val datePickerBtn = findViewById<Button>(R.id.datePickerBtn)
        datePickerBtn.setOnClickListener { view ->
            datePicker(view)
        }
    }

    private fun displayTextView(textView: TextView, value: String) {
        textView.text = value
    }

    private fun datePicker(view: View) {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        val dayInMilli = 86400000

        val dateDisplay = findViewById<TextView>(R.id.dateDisplay)
        val timeInMinutesDisplay = findViewById<TextView>(R.id.resultDisplay)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"

                displayTextView(dateDisplay, selectedDate)

                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                // Total time passed from 1970 to selected date
                val selectedDateInMinutes = formattedDate.parse(selectedDate)!!.time / 60000
                // Total time passed from 1970 to current date
                val currentDate =
                    formattedDate.parse(formattedDate.format(System.currentTimeMillis()))
                // Current date in minutes
                val currentDateInMinutes = currentDate!!.time / 60000
                // Take the difference of the above two to get actual time passed in minutes from
                // selected date to current date
                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                displayTextView(timeInMinutesDisplay, differenceInMinutes.toString())


            }, year, month, day
        )

        // Limit the date picker to not select future dates
        datePicker.datePicker.maxDate = Date().time - dayInMilli
        datePicker.show()

    }


}