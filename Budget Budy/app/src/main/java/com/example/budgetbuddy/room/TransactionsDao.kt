package com.example.budgetbuddy.room

import androidx.room.*
import com.example.budgetbuddy.room.tables.DateMonth
import com.example.budgetbuddy.room.tables.TransactionsTable
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions_table ORDER BY id")
    fun fetchTransactions(): Flow<List<TransactionsTable>>

    @Query("SELECT * FROM transactions_table ORDER BY categoryrowValue")
    fun fetchTransactionsGroupByCategory(): Flow<List<TransactionsTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileRecord(transactionsTable: TransactionsTable)

    @Delete
    suspend fun deleteTransaction(tran: TransactionsTable)

    @Update
    suspend fun updateTransaction(tran: TransactionsTable)

    @Query("SELECT * FROM transactions_table WHERE time_range_month = :month AND time_range_year = :year ORDER BY date DESC")
    fun fetchRecordByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>>

    // Month, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYear(month: Int, year: Int): Flow<List<TransactionsTable>>

    // Month, Year, Day
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_month=:month and time_range_year =:year and time_range_day=:day) as percentage FROM transactions_table WHERE time_range_month =:month AND time_range_year =:year AND time_range_day=:day GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByMonthAndYearAndDay(month: Int, year: Int, day: Int): Flow<List<TransactionsTable>>

    // Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_year =:year) as percentage FROM transactions_table WHERE time_range_year =:year GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByYear(year: Int): Flow<List<TransactionsTable>>

    // All
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table) as percentage FROM transactions_table GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingAll(): Flow<List<TransactionsTable>>

    // Month, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_week=:week and time_range_year =:year) as percentage FROM transactions_table WHERE time_range_week =:week AND time_range_year =:year GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByWeekAndYear(week: Int, year: Int): Flow<List<TransactionsTable>>

    // Quarter, Year
    @Query("SELECT *, sum(amount) AS catAmount, sum(amount) * 100.0 / (select sum(amount) from transactions_table where time_range_day=:quarter and time_range_year =:year) as percentage FROM transactions_table WHERE time_range_quarter =:quarter AND time_range_year =:year GROUP BY categoryrowValue ORDER BY sum(amount) DESC")
    fun fetchReportingByQuarterAndYear(quarter: Int, year: Int): Flow<List<TransactionsTable>>


}