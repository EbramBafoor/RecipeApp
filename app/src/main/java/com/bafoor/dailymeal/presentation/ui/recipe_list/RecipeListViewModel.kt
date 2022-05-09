package com.bafoor.dailymeal.presentation.ui.recipe_list


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bafoor.dailymeal.domain.model.Recipe
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.FoodCategory
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.getFoodCategory
import com.bafoor.dailymeal.repository.RecipeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

const val PAGE_MAX_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepositoryImpl,
    @Named("auth_token") private val token: String
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("")
    val loading = mutableStateOf(false)
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0
    var categoryScrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {

        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                }

            } catch (e: Exception) {
                Log.d("TAG", "onTriggerEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun newSearch() {


        loading.value = true
        resetSearchState()

        delay(1000)

        val result = recipeRepository.search(
            token = token,
            page = 1,
            query = searchQuery.value
        )
        recipes.value = result
        loading.value = false

    }

    /**
     * next page fun for prevent duplicate events due to quickly scroll
     */
    private suspend fun nextPage() {


        if ((recipeListScrollPosition + 1) >= page.value * PAGE_MAX_SIZE) {

            loading.value = true
            incrementPage()
            delay(500L)

            if (page.value > 1) {
                val result = recipeRepository.search(
                    token = token,
                    page = page.value,
                    query = searchQuery.value
                )
                appendNewRecipe(result)
            }
            loading.value = false
        }

    }


    /**
     * APPEND NEW RECIPES TO THE CURRENT LIST OF RECIPES
     */
    // new recipes page
    private fun appendNewRecipe(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes) //<--- The Old and New recipes
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != searchQuery.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onQueryChanged(value: String) {
        this.searchQuery.value = value
    }

    fun onChangeCategoryScrollPosition(scrollPosition: Int) {
        this.categoryScrollPosition = scrollPosition
    }
}