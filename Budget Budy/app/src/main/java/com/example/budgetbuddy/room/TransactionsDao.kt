package com.example.budgetbuddy.room

import androidx.room.*
import com.example.budgetbuddy.room.tables.TransactionsTable
import com.example.budgetbuddy.utils.getCurrentMonth
import com.example.budgetbuddy.utils.getCurrentYear
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table ORDER BY id")
    fun fetchTransactions(): Flow<List<TransactionsTable>>

    @Query("SELECT * FROM transactions_table ORDER BY category_rowValue")
    fun fetchTransactionsGroupByCategory(): Flow<List<TransactionsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileRecord(transactionsTable: TransactionsTable)

    @Delete
    suspend fun deleteTransaction(tran: TransactionsTable)

    @Update
    suspend fun updateTransaction(tran: TransactionsTable)

    //Transactions -Future
    @Query("SELECT * FROM transactions_table WHERE date>:date ORDER BY date ASC")
    fun fetchRecordByMonthAndYearFuture(date: Long): Flow<List<TransactionsTable>>

    //Transactions - Month
    @Query("SELECT * FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year AND date<=:date ORDER BY date DESC")
    fun fetchRecordByMonthAndYear(month: Int, year: Int, date: Long): Flow<List<TransactionsTable>>

    // Month, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and date<=:date and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year and date<=:date and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYear(
        month: Int,
        year: Int,
        date: Long = Date().time
    ): Flow<List<TransactionsTable>>

    // Month, Year, Day
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and time_range_day=:day and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year AND time_range_day=:day and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYearAndDay(
        month: Int,
        year: Int,
        day: Int
    ): Flow<List<TransactionsTable>>

    // Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_year =:year and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_year =:year and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByYear(year: Int): Flow<List<TransactionsTable>>

    // All
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table WHERE incomeInflow=0) as percentage FROM transactions_table WHERE incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingAll(): Flow<List<TransactionsTable>>

    // Week, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_week=:week and time_range_year =:year and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_week =:week AND time_range_year =:year and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByWeekAndYear(week: Int, year: Int): Flow<List<TransactionsTable>>

    // Quarter, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_quarter=:quarter and time_range_year =:year and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_quarter =:quarter AND time_range_year =:year and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByQuarterAndYear(quarter: Int, year: Int): Flow<List<TransactionsTable>>

    @Query("SELECT * FROM transactions_table WHERE date<=:date ORDER BY id DESC LIMIT 3")
    fun fetchRecentTransaction(date: Long = Date().time): Flow<List<TransactionsTable>>

    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and date<=:date and incomeInflow=0) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year and date<=:date and incomeInflow=0 GROUP BY category_rowValue ORDER BY sum(amount) DESC LIMIT 3")
    fun fetchTopSpending(
        month: Int = getCurrentMonth(),
        year: Int = getCurrentYear(),
        date: Long = Date().time
    ): Flow<List<TransactionsTable>>

    @Query("SELECT *, sum(amount) as catAmount, (select sum(amount) from transactions_table where time_range_month=:prevMonth and time_range_year=:prevYear and date<=:date and incomeInflow=0) as percentage from transactions_table where time_range_month=:currentMonth and time_range_year<=:currentYear and date<=:date and incomeInflow=0")
    fun fetchTopSpentThisMonthAndPreviousMonth(
        currentMonth: Int = getCurrentMonth(),
        currentYear: Int = getCurrentYear(),
        prevMonth: Int,
        prevYear: Int,
        date: Long = Date().time
    ): Flow<List<TransactionsTable>>

    //Search feature
    @Query("SELECT * FROM transactions_table WHERE category_rowValue LIKE :query OR amount like :query OR note LIKE :query ORDER by date DESC")
    fun searchFeature(query: String): Flow<List<TransactionsTable>>

    //Wallet
    @Query("select *,  sum(amount) as catAmount, (select  sum(amount) as test from transactions_table where incomeInflow = 1 and date<=:date) as percentage from transactions_table where incomeInflow = 0 and date<=:date")
    fun fetchMyWallet(date: Long = Date().time): Flow<List<TransactionsTable>>
}