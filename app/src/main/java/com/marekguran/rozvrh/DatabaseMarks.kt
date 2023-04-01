package com.marekguran.rozvrh

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.firebase.database.*

private val tag = "Database"

class DatabaseMarks(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "data_table"
        private const val COL_ID = "id"
        private const val COL_SUBJECT = "subject_name"
        private const val COL_GRADE_LETTER = "grade_letter"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SUBJECT TEXT, $COL_GRADE_LETTER TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(subject: String, gradeLetter: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_SUBJECT, subject)
        contentValues.put(COL_GRADE_LETTER, gradeLetter)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != (-1).toLong()
    }

    fun getAllData(): List<String> {
        val data = ArrayList<String>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_ID", null)

        if (cursor.moveToFirst()) {
            do {
                val subject = cursor.getString(1)
                val gradeLetter = cursor.getString(2)
                data.add("$subject: $gradeLetter")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }

    fun addHardcodedData() {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_SUBJECT, "Hardcoded Subject")
        contentValues.put(COL_GRADE_LETTER, "A")
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getIdFromData(data: String): Int {
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT $COL_ID FROM $TABLE_NAME WHERE $COL_SUBJECT = '$data'", null)

        var id = -1
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
        }

        cursor.close()
        Log.d(tag, "Got ID")
        return id
    }

    fun deleteData(id: Int): Boolean {
        val db = this.writableDatabase
        Log.d(tag, "Before delete: " + getAllData().toString())
        val result = db.delete(TABLE_NAME, "$COL_ID = $id", null)
        Log.d(tag, "After delete: " + getAllData().toString())
        Log.d(tag, "database data deletion, result: $result")
        return result > 0
    }

    fun deleteDatabase(context: Context): Boolean {
        return context.deleteDatabase(DATABASE_NAME)
    }

    fun listenForDataChanges() {
        Log.d(tag, "listenForDataChanges()")
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("data")

        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                insertDataFromSnapshot(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                updateDataFromSnapshot(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                deleteDataFromSnapshot(dataSnapshot)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // not implemented
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(tag, "Firebase data download failed: $databaseError")
            }
        })
    }

    fun insertDataFromSnapshot(dataSnapshot: DataSnapshot) {
        Log.d(tag, "insertDataFromSnapshot()")
        val subject = dataSnapshot.child("subject").value as String
        val gradeLetter = dataSnapshot.child("gradeLetter").value as String
        val contentValues = ContentValues()
        contentValues.put(COL_SUBJECT, subject)
        contentValues.put(COL_GRADE_LETTER, gradeLetter)

        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COL_SUBJECT = ? AND $COL_GRADE_LETTER = ?", arrayOf(subject, gradeLetter), null, null, null)
        val dataExists = cursor.count > 0
        cursor.close()

        if (!dataExists) {
            db.insert(TABLE_NAME, null, contentValues)
        }
    }


    fun updateDataFromSnapshot(dataSnapshot: DataSnapshot) {
        Log.d(tag, "updateDataFromSnapshot()")
        cleanLocalDatabase()
        listenForDataChanges()
    }



    fun deleteDataFromSnapshot(dataSnapshot: DataSnapshot) {
        Log.d(tag, "deleteDataFromSnapshot()")
        val subject = dataSnapshot.child("subject").value as String
        cleanLocalDatabase()
        this.writableDatabase.delete(TABLE_NAME, "$COL_SUBJECT = ?", arrayOf(subject))
    }

    fun cleanLocalDatabase() {
        Log.d(tag, "cleanLocalDatabase()")
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }



}