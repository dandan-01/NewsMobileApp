package com.example.news_finalproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.news_finalproject.model.Article
import com.example.news_finalproject.utility.Converters

@Database(entities=[Article::class], version=3, exportSchema=false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    //reference DAO
    abstract fun newsDao() : NewsDao

    // companion object
    companion object {
        @Volatile

        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "NewsApi.org Database").addMigrations(MIGRATION_3_4)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Drop existing tbl_news table if it exists
                database.execSQL("DROP TABLE IF EXISTS tbl_news")

                // Create new tbl_news table with updated schema
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS tbl_news (
                        author TEXT,
                        content TEXT,
                        description TEXT,
                        publishedAt TEXT,
                        title TEXT,
                        url TEXT PRIMARY KEY,
                        urlToImage TEXT
                    )
                    """
                )
            }
        }
    }
}