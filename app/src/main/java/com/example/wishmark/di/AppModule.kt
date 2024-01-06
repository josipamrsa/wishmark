package com.example.wishmark.di

import android.app.Application
import androidx.room.Room
import com.example.wishmark.feature_bookmark.data.data_source.BookmarkDatabase
import com.example.wishmark.feature_bookmark.data.repository.BookmarkRepositoryImpl
import com.example.wishmark.feature_bookmark.domain.repository.BookmarkRepository
import com.example.wishmark.feature_bookmark.domain.use_case.AddBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.BookmarkUseCases
import com.example.wishmark.feature_bookmark.domain.use_case.DeleteBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.GetBookmark
import com.example.wishmark.feature_bookmark.domain.use_case.GetBookmarks
import com.fresh.materiallinkpreview.models.OpenGraphMetaData
import com.fresh.materiallinkpreview.parsing.IOpenGraphMetaDataProvider
import com.fresh.materiallinkpreview.parsing.OpenGraphMetaDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWishmarkDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            app,
            BookmarkDatabase::class.java,
            BookmarkDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(db: BookmarkDatabase) : BookmarkRepository {
        return BookmarkRepositoryImpl(db.bookmarkDao)
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