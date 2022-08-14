package com.example.budgetbuddy.screens.reportingperiod

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.example.budgetbuddy.MainFragment
import com.example.budgetbuddy.R
import com.example.budgetbuddy.databinding.DateRangeBottomSheetDialogBinding
import com.example.budgetbuddy.databinding.FragmentReportinPeriodBinding
import com.example.budgetbuddy.screens.transactions_screen.TransactionViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReportingPeriodFragment : MainFragment() {
    private lateinit var binding: FragmentReportinPeriodBinding
    private val viewModel: TransactionViewModel by activityViewModels()
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

    override fun onStart() {
        super.onStart()

        inflateMenu()
        initViewModel()
        initOnCLick()

        val pieChart = binding.pieChart
        initPieChart(pieChart)

        setDataToPieChart(pieChart)
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

    }

    private fun setDataToPieChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()
        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_category_billing, null)
        dataEntries.add(PieEntry(20f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))
        dataEntries.add(PieEntry(5f, drawable))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FF8A65"))
        colors.add(Color.parseColor("#4DD0E1"))
        colors.add(Color.parseColor("#FFF176"))
        colors.add(Color.parseColor("#FFF176"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 2f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(0f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(500, Easing.EaseInOutQuad)

//        pieChart.legend.textSize = 0f
//        pieChart.legend.textColor = Color.WHITE
//        pieChart.legend.direction = Legend.LegendDirection.LEFT_TO_RIGHT
//        pieChart.legend.horizontalAlignment= Legend.LegendHorizontalAlignment.RIGHT
        pieChart.legend.isEnabled = false

//        //create hole in center
        pieChart.holeRadius = 20f
        pieChart.transparentCircleRadius = 25f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)



        //add text in center
//        pieChart.setDrawCenterText(true);
//        pieChart.centerText = "Expenses"
//        pieChart.invalidate()

    }

    private fun initViewModel() {
        viewModel.getDate().observe(viewLifecycleOwner) {
            cal.time = it
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val ff = viewModel.transformTextLayout(month, year)
            binding.calendarSelect.txtDate.text = ff
        }
    }

    private fun initOnCLick() {
        binding.calendarSelect.leftImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, -1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }

        binding.calendarSelect.rightImage.setOnClickListener {
            cal.time = viewModel.getDate().value
            cal.add(Calendar.MONTH, +1)
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            viewModel.setDate(cal.time)
        }
    }

    private fun inflateMenu() {
        binding.appBar.inflateMenu(R.menu.reporting_menu)
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

        }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setContentView(b.root)
        bottomSheetDialog.show()
    }
}