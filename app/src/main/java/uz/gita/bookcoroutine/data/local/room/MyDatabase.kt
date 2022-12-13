package uz.gita.bookcoroutine.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.bookcoroutine.app.App

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getBookDao(): BookDao
    companion object{
        private lateinit var myDatabase: MyDatabase

        fun getInstance(): MyDatabase {
            if(!::myDatabase.isInitialized)
                myDatabase = Room.databaseBuilder(App.instance, MyDatabase::class.java,"BookAppData").build()
            return myDatabase
        }
    }
}