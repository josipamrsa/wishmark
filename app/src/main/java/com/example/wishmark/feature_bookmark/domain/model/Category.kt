package com.example.wishmark.feature_bookmark.domain.model

import androidx.compose.ui.graphics.Color
import io.realm.kotlin.types.RealmObject

enum class ItemCategory {
    NONE(Color.Transparent),
    TECHNOLOGY(Color.hsl(250f, 0.69f, 0.45f)),
    FASHION(Color.hsl(278f, 0.69f, 0.45f)),
    BEAUTY(Color.hsl(297f, 0.69f, 0.45f)),
    HOME(Color.hsl(26f, 0.69f, 0.45f)),
    GARDENING(Color.hsl(145f, 0.69f, 0.45f)),
    AUTOMOTIVE(Color.hsl(360f, 0.69f, 0.45f)),
    OTHER(Color.Gray);

    var color: Color = Color.Gray

    constructor(color: Color) {
        this.color = color
    }
}

class Category : RealmObject {
    var name: String? = ItemCategory.NONE.name
}

