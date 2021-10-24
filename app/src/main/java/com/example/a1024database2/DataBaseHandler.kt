package com.example.a1024database2

import  android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHandler( context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "KtvDataBase"

        private val TABLE_CONTACTS = "SongTable"

        private val KEY_ID = "_id"
        private val KEY_NAME = "name"

    }

    override fun onCreate(db: SQLiteDatabase?) {
    //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT"
            +")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addSong( song: SongModelClass): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, song.name)
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    fun viewSongList(): ArrayList<SongModelClass> {

        val songList: ArrayList<SongModelClass> = ArrayList<SongModelClass>()

        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))


                val song = SongModelClass(id = id, name = name)
                songList.add(song)
            } while(cursor.moveToNext())
        }

        return songList


    }

    fun updateSong(song: SongModelClass): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, song.name)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + song.id, null)
        db.close() // Closing database connection
        return success

    }

    fun deleteSong( song: SongModelClass): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ID, song.id)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + song.id, null)
        db.close() // Closing database connection
        return success
    }




}