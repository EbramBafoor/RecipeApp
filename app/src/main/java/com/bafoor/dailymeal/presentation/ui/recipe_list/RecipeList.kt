package com.bafoor.dailymeal.presentation.ui.recipe_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.bafoor.dailymeal.R
import androidx.navigation.fragment.findNavController
import com.bafoor.dailymeal.presentation.ui.BaseApplication
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.CircularIndeterminateProgressBar
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.RecipeCard
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.SearchAppBar
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.theme.AppTheme


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecipeList : Fragment() {
    @Inject
    lateinit var application : BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()
    private val navController : NavController = findNavController()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
               AppTheme(
                   DarkTheme = application.isDark.value
               ) {
                   val recipes = viewModel.recipes.value
                   val query = viewModel.searchQuery.value
                   val selectedCategory = viewModel.selectedCategory.value
                   val isLoading = viewModel.loading.value
                   val page = viewModel.page.value

                   Scaffold(
                       topBar = {
                           SearchAppBar(
                               query = query,
                               onQueryChanged = viewModel::onQueryChanged,
                               newSearch = { viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent) },
                               categoryScrollPosition = viewModel.categoryScrollPosition,
                               selectedCategory = selectedCategory,
                               onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                               onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                               onToggleTheme = {
                                   application.onToggleTheme()
                               }
                           )
                       }
                   ) {
                       Box(
                           modifier = Modifier
                               .background(MaterialTheme.colors.surface)
                       ) {
                           LazyColumn {
                               itemsIndexed(
                                   items = recipes
                               ) { index, recipe ->
                                   viewModel
                                       .onChangeRecipeScrollPosition(index)
                                   if (
                                       (index + 1) >= (page * PAGE_MAX_SIZE)
                                       && !isLoading){
                                       viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                                   }
                                   RecipeCard(
                                       recipe = recipe,
                                       onClick = {
                                           if (recipe.id != null){
                                               val bundle = Bundle().apply {
                                                   putInt("recipeId",recipe.id)
                                               }
                                               navController
                                                   .navigate(
                                                       R.id.action_recipeList_to_mealRecipe,
                                                       bundle
                                                   )
                                           }
                                       }
                                   )
                               }
                           }
                           CircularIndeterminateProgressBar(isDisplayed = isLoading)
                       }
                   }
               }
            }
        }
    }
}