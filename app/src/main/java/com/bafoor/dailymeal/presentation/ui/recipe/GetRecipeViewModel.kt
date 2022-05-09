package com.bafoor.dailymeal.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bafoor.dailymeal.domain.model.Recipe
import com.bafoor.dailymeal.repository.RecipeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class GetRecipeViewModel @Inject constructor(
    private val repository: RecipeRepositoryImpl,
    @Named("auth_token") private val token: String
) : ViewModel() {


    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val id = mutableStateOf(-1)

    init {
        onTriggerEvent(GetRecipeEvent.GetRecipe(id.value))
    }


    private fun onTriggerEvent(event: GetRecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GetRecipeEvent.GetRecipe -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("TAG", "onTrigger: $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {

        loading.value = true
        delay(1000)

        val result = repository.get(
            token = token,
            id = id
        )
        recipe.value = result
        loading.value = false
    }

}















