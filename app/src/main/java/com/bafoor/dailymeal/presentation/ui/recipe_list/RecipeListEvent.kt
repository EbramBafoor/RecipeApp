package com.bafoor.dailymeal.presentation.ui.recipe_list

sealed class RecipeListEvent {
    object NewSearchEvent : RecipeListEvent()
    object NextPageEvent : RecipeListEvent()
}
