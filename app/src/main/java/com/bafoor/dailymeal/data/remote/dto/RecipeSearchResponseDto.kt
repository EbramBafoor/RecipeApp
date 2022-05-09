package com.bafoor.dailymeal.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponseDto(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeNetworkDto>,
)
