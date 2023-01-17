package hr.algebra.ark.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.ark.dao.ArkRepository
import hr.algebra.ark.factory.getArkRepository
import hr.algebra.ark.model.Creature

private const val AUTHORITY = "hr.algebra.ark.api.provider"
private const val PATH = "arkCreatures"
val ARK_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private const val CREATURES = 10
private const val CREATURE_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, CREATURES)
    addURI(AUTHORITY, "$PATH/#", CREATURE_ID)
    this
}

class ArkProvider : ContentProvider() {

    private lateinit var arkRepository: ArkRepository
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when (URI_MATCHER.match(uri)) {
            CREATURES -> return arkRepository.delete(selection, selectionArgs)
            CREATURE_ID ->
                uri.lastPathSegment?.let {
                    return arkRepository.delete("${Creature::_id.name}=?", arrayOf(it))
                }
        }
        throw  java.lang.IllegalArgumentException("No such uri")
    }

    override fun getType(uri: Uri): String? {
        return "getType is not implemented!"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = arkRepository.insert(values)
        return ContentUris.withAppendedId(ARK_PROVIDER_CONTENT_URI, id)
    }

    override fun onCreate(): Boolean {
        arkRepository = getArkRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = arkRepository.query(projection, selection, selectionArgs, sortOrder)

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when (URI_MATCHER.match(uri)) {
            CREATURES -> return arkRepository.update(values, selection, selectionArgs)
            CREATURE_ID ->
                uri.lastPathSegment?.let {
                    return arkRepository.update(values, "${Creature::_id.name}=?", arrayOf(it))
                }
        }
        throw  java.lang.IllegalArgumentException("No such uri")
    }
}