package com.example.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity() : AppCompatActivity(), onImageItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private var request: MakeRequest = MakeRequest()
    private var repos: ItemDBView? = null

    fun isConnected() : Boolean{
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected != null
    }

    private fun updateView(){

        if(isConnected()) {
            val itemList = request.run().hits
            itemList.forEach { repos!!.addItem(it) }
            recyclerView.adapter = CustomRecyclerAdapter(itemList, this)

        }
        else {

            repos!!.readAllItems().observe(this, Observer {
                if(it.isNotEmpty()) {
                    recyclerView.adapter = CustomRecyclerAdapter(it, this)
                } else {
                    Toast.makeText(this, "Ooops! No internet connection and your cache is empty.", Toast.LENGTH_SHORT).show()
                }
            }  )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repos = ItemDBView(application)
        recyclerView = findViewById<RecyclerView>(R.id.recview)

        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        updateView()

    }

    override fun onItemClick(hits: Item, position: Int) {
        val intent = Intent(this, Info::class.java)
        intent.putExtra("TAGS", hits.tags)
        intent.putExtra("IMAGE", hits.previewURL)

        startActivity(intent)
    }
}