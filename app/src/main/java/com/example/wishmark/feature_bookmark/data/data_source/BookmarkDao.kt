package com.example.wishmark.feature_bookmark.data.data_source

import com.example.wishmark.feature_bookmark.domain.model.Bookmark
//import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.presentation.util.isNull
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

class BookmarkDao @Inject constructor(
    private val realm: Realm
) {
    fun getAllBookmarks() = realm.query<Bookmark>().asFlow().map { results ->
        results
        results.list.toList()
    }

    fun getBookmark(id: ObjectId) = realm.query<Bookmark>("_id == $0", id).find().first()

    fun getBookmarksByCategory(category: String) =
        realm.query<Bookmark>("category.name == $0", category).find()

    suspend fun insertBookmark(bookmark: Bookmark) = realm.write {
        /*findLatest(bookmark)?.let {
            bookmark.title = it.title
            bookmark.link = it.link
            bookmark.category = it.category
        } ?: */copyToRealm(bookmark)
    }

    fun deleteBookmark(bookmark: Bookmark): () -> Unit = {
        val frozenBookmark = realm.query<Bookmark>("_id == $0", bookmark._id).find().first()
        realm.writeBlocking {
            if (frozenBookmark.isNull().not())
                findLatest(frozenBookmark)?.also {
                    delete(it)
                }
        }
    }
}
