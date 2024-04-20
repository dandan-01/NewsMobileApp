package com.example.news_finalproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news_finalproject.model.Article
import com.example.news_finalproject.model.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(news: List<Article>?)

    // instead of using id = :id, I had to use url because of limited JSON data (PK not available in Article model)
    // this is important to note because it's quite different from the regular method of using the id PK
    @Query("SELECT * FROM tbl_news WHERE url = :url")
    fun getNewsById(url: String) : Article
}