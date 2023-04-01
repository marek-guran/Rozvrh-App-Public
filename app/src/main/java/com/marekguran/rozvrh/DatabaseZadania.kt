package com.marekguran.rozvrh

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.firebase.database.*

private val tag = "Database"

class DatabaseZadania(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "zadania.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "data_table"
        private const val COL_ID = "id"
        private const val COL_SUBJECT = "subject_name"
        private const val COL_GRADE_DETAILS = "grade_details"
        private const val COL_DATE = "date"
        private const val COL_TEACHER = "teacher"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
                "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SUBJECT TEXT, $COL_GRADE_DETAILS TEXT, $COL_DATE TEXT, $COL_TEACHER TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllData(): List<String> {
        val data = ArrayList<String>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_ID", null)

        if (cursor.moveToFirst()) {
            do {
                val subject = cursor.getString(1)
                val gradeDetails = cursor.getString(2)
                val date = cursor.getString(3)
                val teacher = cursor.getString(4)
                data.add("$subject: $gradeDetails, $date, $teacher")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }

    fun listenForDataChanges() {
        Log.d(tag, "listenForDataChanges()")
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("zadania")

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
        val subject = dataSnapshot.child("subject_name").value as String
        val podrobnosti = dataSnapshot.child("grade_details").value as String
        val date = dataSnapshot.child("date").value as String
        val teacher = dataSnapshot.child("teacher").value as String
        val contentValues = ContentValues()
        contentValues.put(COL_SUBJECT, subject)
        contentValues.put("grade_details", podrobnosti)
        contentValues.put("date", date)
        contentValues.put("teacher", teacher)

        val db = this.writableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COL_SUBJECT = ? AND grade_details = ?", arrayOf(subject, podrobnosti), null, null, null)
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