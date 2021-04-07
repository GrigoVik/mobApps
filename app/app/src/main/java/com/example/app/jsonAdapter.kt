package com.example.app

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import java.net.URL

data class Query(val hits: List<Item>)
@Entity(tableName = "items_table")
data class Item(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val pageURL: String?, val tags: String?, val previewURL: String?, val imageURL: String?, val views: String?)

class MakeRequest
{
    private var url : String = "https://pixabay.com/api/?key=21037729-dcbccc28cb673f2495cbf2fc0&q=cartoons&image_type=photo"

    fun run() :  Query
    {
        lateinit var reposJsonStr : String
        val thread : Thread = Thread(Runnable { reposJsonStr = URL(url).readText() })
        thread.start()
        thread.join()
        return Gson().fromJson(reposJsonStr, Query::class.java)
    }
}