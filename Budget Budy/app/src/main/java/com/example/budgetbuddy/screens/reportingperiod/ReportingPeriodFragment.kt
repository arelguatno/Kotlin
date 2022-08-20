package com.example.budgetbuddy.screens.reportingperiod

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.DateRangeBottomSheetDialogBinding
import com.example.budgetbuddy.databinding.FragmentReportinPeriodBinding
import com.example.budgetbuddy.enums.TimeRange
import com.example.budgetbuddy.fragments.category.CategoryList
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.screens.transactions_screen.DateAndTimeRange
import com.example.budgetbuddy.screens.transactions_screen.TransactionFragmentAdapterChild
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.example.budgetbuddy.utils.getDateQuarter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportingPeriodFragment : MainFragment() {
    private lateinit var binding: FragmentReportinPeriodBinding
    private val viewModel: TransactionViewModel by activityViewModels()
    private lateinit var activity: ReportingPeriodActivity

    companion object {
        private lateinit var bottomSheetDialog: BottomSheetDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportinPeriodBinding.inflate(layoutInflater)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as ReportingPeriodActivity
    }

    override fun onStart() {
        super.onStart()

        inflateMenu()
        initViewModel()
        initOnCLick()
        initDefaultData()
    }

    private fun initDefaultData() {
        val date = activity.intent.getSerializableExtra(ReportingPeriodActivity.DATE_DATA)
        if (date != null) {
            if (viewModel.getDateAndTimeRange().value == null) {
                val item = DateAndTimeRange(date as Date, TimeRange.MONTH) // default Month view
                viewModel.setDateAndTimeRange(item)
            }
        }
    }

    private fun initViewModel() {
        viewModel.getDateAndTimeRange().observe(viewLifecycleOwner) {
            cal.time = it.date
            query(it.timeRange, cal)
            viewModel.getDateLabel(it.timeRange, it.date).observe(viewLifecycleOwner) { s ->
                binding.calendarSelect.txtDate.text = s
            }
        }
    }

    private fun initOnCLick() {
        //Left Image (-)
        binding.calendarSelect.leftImage.setOnClickListener {
            val newDate = viewModel.getIncreaseOrDecrease(
                false,
                viewModel.getDateAndTimeRange().value?.date!!,
                viewModel.getDateAndTimeRange().value?.timeRange!!
            )
            val item =
                DateAndTimeRange(newDate, viewModel.getDateAndTimeRange().value?.timeRange!!)
            viewModel.setDateAndTimeRange(item)
        }
        //Right Image (+)
        binding.calendarSelect.rightImage.setOnClickListener {
            val newDate = viewModel.getIncreaseOrDecrease(
                true,
                viewModel.getDateAndTimeRange().value?.date!!,
                viewModel.getDateAndTimeRange().value?.timeRange!!
            )
            val item =
                DateAndTimeRange(newDate, viewModel.getDateAndTimeRange().value?.timeRange!!)
            viewModel.setDateAndTimeRange(item)
        }
    }

    private fun queryData(
        month: Int = 0,
        year: Int = 0,
        day: Int = 0,
        week: Int = 0,
        quarter: Int = 0,
        timeRange: TimeRange
    ) {
        viewModel.fetchReporting(
            month,
            year,
            day,
            week,
            quarter,
            time_range = timeRange
        ).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val child = TransactionFragmentAdapterChild(it)
                binding.recycler.layoutManager = LinearLayoutManager(
                    binding.recycler.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                val pieChart = binding.pieChart
                initPieChart(pieChart)
                setDataToPieChart(pieChart, it)

                binding.recycler.adapter = child
                binding.txtNoRecordsFound.isVisible = false
                binding.nestedView.isVisible = true
            } else {
                binding.txtNoRecordsFound.isVisible = true
                binding.nestedView.isVisible = false
            }
        }
    }

    private fun inflateMenu() {
        binding.appBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_calendar -> selectTimeRange()
            }
            true
        }
        binding.appBar.setNavigationOnClickListener {
            activity?.finish()
        }
    }

    private fun selectTimeRange() {
        val b = DateRangeBottomSheetDialogBinding.inflate(layoutInflater)

        b.btnDay.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.DAY)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnMonth.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.MONTH)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnYear.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.YEAR)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnWeek.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.WEEK)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnQuarter.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.QUARTER)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnAll.setOnClickListener {
            val item = DateAndTimeRange(Date(), TimeRange.ALL)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }

        b.btnCustom.setOnClickListener {
            //TODO
            val item = DateAndTimeRange(Date(), TimeRange.CUSTOM)
            viewModel.setDateAndTimeRange(item)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.dismissWithAnimation
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(b.root)
        bottomSheetDialog.show()
    }

    private fun setDataToPieChart(pieChart: PieChart, list: List<TransactionsTable>) {
        val dataEntries = ArrayList<PieEntry>()
        val colors: ArrayList<Int> = ArrayList()
        val colorsArray =
            arrayListOf(
                "#edcf26",
                "#249EA0",
                "#4d8165",
                "#F78104",
                "#6193b3",
                "#dc8f38",
                "#a88c6b",
                "#8dc3d3",
                "#83b7dd",
                "#83ddbc"
            )
        var sum = 0.00

        for (i in list.indices) {

            val drawable: Drawable? =
                ResourcesCompat.getDrawable(
                    resources, list[i].category.imageID!!, null
                )
            val percentage = list[i].percentage

            if (percentage <= 2.0) {
                dataEntries.add(PieEntry(percentage.toFloat()))
            } else {
                dataEntries.add(PieEntry(percentage.toFloat(), drawable))
            }

            sum += list[i].catAmount

            colors.add(Color.parseColor(colorsArray[i]))
            binding.txtSum.text = String.format("-$ %.2f", sum)
        }

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(0f)
    }

    private fun query(v: TimeRange, cal: Calendar) {
        queryData(
            day = cal.get(Calendar.DAY_OF_MONTH),
            week = cal.get(Calendar.WEEK_OF_YEAR),
            month = cal.get(Calendar.MONTH),
            quarter = getDateQuarter(cal.time.toString()),
            year = cal.get(Calendar.YEAR),
            timeRange = v
        )
    }

    private fun initPieChart(pieChart: com.github.mikephil.charting.charts.PieChart) {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(500, Easing.EaseInOutQuad)
        pieChart.legend.isEnabled = false  // no legend

        //create hole in center
        pieChart.holeRadius = 30f
        pieChart.transparentCircleRadius = 35f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setUsePercentValues(true)
    }
}