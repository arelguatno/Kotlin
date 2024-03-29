package eu.tutorials.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        model = ViewModelProvider(this).get(MainViewModel::class.java)
        //set onclicklistener to btnDatePicker
        binding.btnDatePicker.setOnClickListener {
             //call clickDatePicker when this button is clicked
            clickDatePicker()
        }

        model.getDate().observe(this, androidx.lifecycle.Observer {
            binding.tvSelectedDate.text = it.toString()

        })

        model.getMinutes().observe(this, androidx.lifecycle.Observer {
            binding.tvSelectedDateInMinutes.text = it.toString()
        })
    }

    //function to show the date picker
    fun clickDatePicker(){
        /**
         * This Gets a calendar using the default time zone and locale.
         * The calender returned is based on the current time
         * in the default time zone with the default.
         */
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        /**
         * Creates a new date picker dialog for the specified date using the parent
         * context's default date picker dialog theme.
         */
        val dpd = DatePickerDialog(this,{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
            /**
             * The listener used to indicate the user has finished selecting a date.
             */

            /**
             *Here the selected date is set into format i.e : day/Month/Year
             * And the month is counted in java is 0 to 11 so we need to add +1 so it can be as selected.
             * */
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            // Selected date it set to the TextView to make it visible to user.
            //binding.tvSelectedDate.text = selectedDate

            model.selectedDate.value = selectedDate
            /**
             * Here we have taken an instance of Date Formatter as it will format our
             * selected date in the format which we pass it as an parameter and Locale.
             * Here I have passed the format as dd/MM/yyyy.
             */
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            // The formatter will parse the selected date in to Date object
            // so we can simply get date in to milliseconds.
            val theDate = sdf.parse(selectedDate)
            //use the safe call operator with .let to avoid app crashing it theDate is null
            theDate?.let {
                /** Here we have get the time in milliSeconds from Date object
                 * And as we know the formula as milliseconds can be converted to second by dividing it by 1000.
                 * And the seconds can be converted to minutes by dividing it by 60.
                 * So now in the selected date into minutes.
                 */
                 val selectedDateInMinutes = theDate.time/60000
                // Here we have parsed the current date with the Date Formatter which is used above.
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                //use the safe call operator with .let to avoid app crashing it currentDate is null
                currentDate?.let {
                    // Current date in to minutes.
                    val currentDateToMinutes = currentDate.time/60000
                    /**
                     * Now to get the difference into minutes.
                     * We will subtract the selectedDateToMinutes from currentDateToMinutes.
                     * Which will provide the difference in minutes.
                     */
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                   // binding.tvSelectedDateInMinutes.text = differenceInMinutes.toString()
                    model.ageinMinutes.value = differenceInMinutes.toString()
                }
            }
        },
        year,month,day)

        /**
         * Sets the maximal date supported by this in
         * milliseconds since January 1, 1970 00:00:00 in time zone.
         *
         * @param maxDate The maximal supported date.
         */
        // 86400000 is milliseconds of 24 Hours. Which is used to restrict the user from selecting today and future day.
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}