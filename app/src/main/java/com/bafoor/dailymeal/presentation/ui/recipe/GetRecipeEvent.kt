package com.bafoor.dailymeal.presentation.ui.recipe

sealed class GetRecipeEvent {

    data class GetRecipe(val id : Int) : GetRecipeEvent()
}