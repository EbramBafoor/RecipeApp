package com.bafoor.dailymeal.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bafoor.dailymeal.presentation.ui.BaseApplication
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.CircularIndeterminateProgressBar
import com.bafoor.dailymeal.presentation.ui.recipe_list.component.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MealRecipe : Fragment() {

    @Inject
    lateinit var app: BaseApplication

    private val viewModel: GetRecipeViewModel by viewModels()
    private var recipeId = viewModel.id.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { rId ->
            recipeId = rId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value
                val coroutineScope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()
                AppTheme(DarkTheme = app.isDark.value) {
                    Scaffold(
                        scaffoldState = scaffoldState
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (loading && recipe == null) {
                                Text(text = "Loading...")
                            } else {
                                recipe?.let {
                                    if (it.id == 1) {
                                        coroutineScope.launch {
                                            val snackBarResult =
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "This is your message",
                                                    actionLabel = "OK."
                                                )

                                        }
                                    } else {
                                        MealRecipeCard(recipe = it)
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }

                }
            }
        }
    }
}