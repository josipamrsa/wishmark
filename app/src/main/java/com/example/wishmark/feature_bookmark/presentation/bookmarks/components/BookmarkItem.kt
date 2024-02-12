package com.example.wishmark.feature_bookmark.presentation.bookmarks.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wishmark.feature_bookmark.domain.model.Bookmark
import com.example.wishmark.feature_bookmark.domain.model.ItemCategory
import com.example.wishmark.feature_bookmark.presentation.bookmarks.BookmarkItemState
import com.example.wishmark.feature_bookmark.presentation.util.toItemCategory
import com.fresh.materiallinkpreview.models.OpenGraphMetaData
import com.fresh.materiallinkpreview.ui.CardLinkPreview
import com.fresh.materiallinkpreview.ui.CardLinkPreviewProperties

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkItem(
    bookmarkItem: BookmarkItemState,
    onDeleteBookmark: (Bookmark) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .combinedClickable (
                onClick = {},
                onLongClick = { onDeleteBookmark(bookmarkItem.bookmark) }
            )
    ) {
        Text(
            text = bookmarkItem.bookmark.title,
            style = TextStyle(
                fontSize = 20.sp,
            ),
            modifier = Modifier.padding(5.dp)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        BookmarkItemLinkMetadata(
            link = bookmarkItem.bookmark.link,
            linkMetaData = bookmarkItem.linkMetaData
        )

        Spacer(modifier = Modifier.padding(10.dp))

        BookmarkItemCategory(category = toItemCategory(bookmarkItem.bookmark.category) ?: ItemCategory.NONE)

    }
}

@Composable
fun BookmarkItemLinkMetadata(
    link: String,
    linkMetaData: OpenGraphMetaData?
) {
    linkMetaData?.let { metadata ->
        if (metadata.imageUrl.isNotEmpty()) {
            CardLinkPreview(
                openGraphMetaData = metadata,
                CardLinkPreviewProperties.Builder(
                    imagePainter = rememberAsyncImagePainter(model = metadata.imageUrl)
                ).build()
            )
        } else CardLinkPreview(metadata)
    } ?: Text(text = link)
}

@Composable
fun BookmarkItemCategory(
    category: ItemCategory
) {
    Box(
        modifier = Modifier
            .background(
                color = category.color,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 12.sp,
            ),
            modifier = Modifier.padding(12.dp)
        )
    }
}
