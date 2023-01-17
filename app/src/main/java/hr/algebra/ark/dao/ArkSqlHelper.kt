package hr.algebra.ark.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.ark.model.Creature

private const val DB_NAME = "ark.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "arkCreatures"
private val CREATE_TABLE = "create table $TABLE_NAME ( " +
        "${Creature::_id.name} integer primary key autoincrement, " +
        "${Creature::creatureName.name} text not null, " +
        "${Creature::aboutCreature.name} text not null, " +
        "${Creature::groupType.name} text not null, " +
        "${Creature::diet.name} text not null, " +
        "${Creature::temperament.name} text not null, " +
        "${Creature::urlIcon.name} text not null, " +
        "${Creature::urlImage.name} text not null, " +
        "${Creature::audio.name} text not null, " +
        "${Creature::favourite.name} integer not null " +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class ArkSqlHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    ArkRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(
            TABLE_NAME, selection, selectionArgs
        )

    override fun insert(values: ContentValues?) = writableDatabase.insert(TABLE_NAME, null, values)

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
    )

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)
}