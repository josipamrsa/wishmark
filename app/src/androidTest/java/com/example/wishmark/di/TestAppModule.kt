package com.example.wishmark.di

import com.example.wishmark.feature_bookmark.data.data_source.BookmarkDao
import com.example.wishmark.feature_bookmark.data.repository.BookmarkRepositoryImpl
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.Category
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import com.example.wishmark.feature_bookmark.domain.use_case.AddBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.domain.use_case.DeleteBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.GetBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.GetBookmarks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideTestRealmDatabase(): Realm = Realm.open(
        RealmConfiguration.Builder(
            schema = setOf(
                Bookmark::class,
                Category::class
            )
        )
            .inMemory()
            .name("realm-test")
            .build()
    )


    @Provides
    @Singleton
    fun provideBookmarkRepository(dao: BookmarkDao): BookmarkRepository {
        return BookmarkRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideBookmarkUseCases(repository: BookmarkRepository): BookmarkUseCases {
        return BookmarkUseCases(
            getBookmark = GetBookmark(repository),
            deleteBookmark = DeleteBookmark(repository),
            addBookmark = AddBookmark(repository),
            getBookmarks = GetBookmarks(repository)
        )
    }

}