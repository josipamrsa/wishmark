package com.example.wishmark.feature_bookmark.domain.use_case

import com.example.wishmark.feature_bookmark.data.repository.FakeBookmarkRepository
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory
import com.example.wishmark.feature_bookmark.presentation.util.toCategory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBookmarksTest(
) {
    private lateinit var getBookmarks: GetBookmarks
    private lateinit var repository: FakeBookmarkRepository

    @Before
    fun setUp() {
        repository = FakeBookmarkRepository()
        getBookmarks = GetBookmarks(repository)

        val b1 = Bookmark().apply {
            this.title = "Flipper Zero"
            this.link = "https://flipperzero.one/"
            this.category = toCategory(ItemCategory.TECHNOLOGY)
        }

        val b2 = Bookmark().apply {
            this.title = "Apple iPad 9 64GB"
            this.link =
                "https://www.sancta-domenica.hr/komunikacije/tableti/tablet-apple-10-2-inch-ipad-9-wi-fi-64gb-space-grey.html"
            this.category = toCategory(ItemCategory.TECHNOLOGY)
        }

        val bookmarksToInsert = listOf<Bookmark>(
            b1, b2
        )

        runBlocking {
            bookmarksToInsert.forEach { repository.insertBookmark(it) }
        }
    }

    @Test
    fun `Bookmarks list is at least not empty`() = runBlocking {
        val bookmarks = getBookmarks().first()

        //JUnit
        //assertEquals(true, bookmarks.isEmpty().not())

        // Google Truth
        assertThat(bookmarks).isNotEmpty()
    }

    @Test
    fun `Bookmarks list contains exactly two items`() = runBlocking {
        val bookmarks = getBookmarks().first()

        //JUnit
        //assertEquals(2, bookmarks.size)

        //Google Truth
        //If using ContainsExactly or ContainsAtLeast, explicitly say that function is Unit
        assertThat(bookmarks).isInOrder()
    }

    @Test
    fun `First bookmark is titled Flipper Zero`() = runBlocking {
            val bookmarks = getBookmarks().first().map { it -> it.title }
            val orderedBookmarks = listOf("Flipper Zero", "Apple iPad 9 64GB")

            //JUnit
            //assertEquals("Flipper Zero", bookmarks.first())

            //Google Truth
            //assertThat(bookmarks.first()).isEqualTo("Flipper Zero")
            //assertThat(bookmarks).contains("Flipper Zero")
            assertThat(bookmarks).containsExactly("Flipper Zero", "Apple iPad 9 64GB")//.inOrder()
        }

}