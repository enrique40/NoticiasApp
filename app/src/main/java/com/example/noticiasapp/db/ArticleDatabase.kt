package com.example.noticiasapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noticiasapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao


    companion object {
        private var INSTANCE: ArticleDatabase? = null
        fun getDatabase(context: Context?): ArticleDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context!!.applicationContext,
                ArticleDatabase::class.java,
                "article_db"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        fun clearTables(context: Context) {
            getDatabase(context).clearAllTables()
        }
    }
}