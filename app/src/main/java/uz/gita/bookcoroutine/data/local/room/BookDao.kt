package uz.gita.bookcoroutine.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Query("SELECT * FROM books WHERE synCol != 4")
    fun getAllExistsBooks(): List<BookEntity>

    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE synCol = :numberCol")
    fun getOfflineData(numberCol: Int): Flow<List<BookEntity>>
}