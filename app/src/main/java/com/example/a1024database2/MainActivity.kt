package com.example.a1024database2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{ view ->
            addRecord()
        }
    }

    private fun addRecord() {
        val name = etName.text.toString()

        val databaseHandler: DataBaseHandler = DataBaseHandler(this)
        if (!name.isEmpty() ) {
            val status =
                databaseHandler.addSong(SongModelClass(0, name))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etEmailId.text.clear()
                setupListofDataIntoRecyclerView()
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Name or Email cannot be blank",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getItemsList(): ArrayList<SongModelClass>{
        val databaseHandler: DataBaseHandler = DataBaseHandler(this)

        val songList: ArrayList<SongModelClass> = databaseHandler.viewSongList()

        return songList

    }

    private fun setupListofDataIntoRecyclerView() {
        if ( getItemsList().size >0 ){
            rvItemsList.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE

            rvItemsList.layoutManager = LinearLayoutManager(this)

            val itemAdapter = ItemAdapter(this, getItemsList())
            rvItemsList.adapter = itemAdapter
        } else {

            rvItemsList.visibility = View.GONE
            tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }


}