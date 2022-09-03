package com.example.budgetbuddy.room.transactions_table

import androidx.room.*
import com.example.budgetbuddy.utils.getCurrentMonth
import com.example.budgetbuddy.utils.getCurrentYear
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table ORDER BY category_rowValue")
    fun fetchTransactionsGroupByCategory(): Flow<List<TransactionsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileRecord(transactionsTable: TransactionsTable)

    @Delete
    suspend fun deleteTransaction(tran: TransactionsTable)

    @Update
    suspend fun updateTransaction(tran: TransactionsTable)

    //Transactions -Future
    @Query("SELECT * FROM transactions_table WHERE date>:date AND walletID=:walletID ORDER BY date ASC")
    fun fetchRecordByMonthAndYearFuture(date: Long, walletID: Int): Flow<List<TransactionsTable>>

    //Transactions - Month
    @Query("SELECT * FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year AND date<=:date AND walletID=:walletID ORDER BY date DESC")
    fun fetchRecordByMonthAndYear(
        month: Int,
        year: Int,
        date: Long,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    // Month, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and date<=:date and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year and date<=:date and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYear(
        month: Int,
        year: Int,
        date: Long = Date().time,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    // Month, Year, Day
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and time_range_day=:day and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year AND time_range_day=:day and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYearAndDay(
        month: Int,
        year: Int,
        day: Int,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    // Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_year =:year and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_year =:year and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByYear(year: Int, walletID: Int): Flow<List<TransactionsTable>>

    // All
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table WHERE incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingAll(walletID: Int): Flow<List<TransactionsTable>>

    // Week, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_week=:week and time_range_year =:year and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_week =:week AND time_range_year =:year and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByWeekAndYear(
        week: Int,
        year: Int,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    // Quarter, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_quarter=:quarter and time_range_year =:year and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_quarter =:quarter AND time_range_year =:year and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByQuarterAndYear(
        quarter: Int,
        year: Int,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    //Recent Transaction
    @Query("SELECT * FROM transactions_table WHERE date<=:date and walletID=:walletID ORDER BY id DESC LIMIT 3")
    fun fetchRecentTransaction(
        date: Long = Date().time,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    //Top Spending
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and date<=:date and incomeInflow=0 AND walletID=:walletID) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year and date<=:date and incomeInflow=0 AND walletID=:walletID GROUP BY category_rowValue ORDER BY sum(amount) DESC LIMIT 3")
    fun fetchTopSpending(
        month: Int = getCurrentMonth(),
        year: Int = getCurrentYear(),
        date: Long = Date().time,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    //Graph Last Month and Current Month
    @Query("SELECT *, sum(amount) as catAmount, (select sum(amount) from transactions_table where time_range_month=:prevMonth and time_range_year=:prevYear and date<=:date and incomeInflow=0 and walletID=:walletID) as percentage from transactions_table where time_range_month=:currentMonth and time_range_year<=:currentYear and date<=:date and incomeInflow=0 and walletID=:walletID")
    fun fetchTopSpentThisMonthAndPreviousMonth(
        currentMonth: Int = getCurrentMonth(),
        currentYear: Int = getCurrentYear(),
        prevMonth: Int,
        prevYear: Int,
        date: Long = Date().time,
        walletID: Int
    ): Flow<List<TransactionsTable>>

    //Search feature
    @Query("SELECT * FROM transactions_table WHERE walletID=:walletID AND category_rowValue LIKE :query OR amount like :query OR note LIKE :query ORDER by date DESC")
    fun searchFeature(query: String, walletID: Int): Flow<List<TransactionsTable>>

    //Wallet
    @Query("select *,  sum(amount) as catAmount, (select  sum(amount) as test from transactions_table where incomeInflow = 1 and date<=:date and walletID=:walletID) as percentage from transactions_table where incomeInflow = 0 and date<=:date and walletID=:walletID")
    fun fetchMyWallet(date: Long = Date().time, walletID: Int): Flow<List<TransactionsTable>>
}