package com.bafoor.dailymeal.presentation.ui.recipe_list.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SearchAppBar(
    query : String,
    onQueryChanged : (String) -> Unit,
    newSearch : () -> Unit,
    categoryScrollPosition : Int,
    selectedCategory : FoodCategory?,
    onSelectedCategoryChanged : (String) -> Unit,
    onChangeCategoryScrollPosition : (Int) -> Unit,
    onToggleTheme : () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = { NewValue ->
                        onQueryChanged(NewValue)
                        newSearch()
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "search")
                    },
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(
                        onClick =  onToggleTheme ,
                        modifier = Modifier
                            .constrainAs(menu){
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Icon(Icons.Filled.Recycling,"AppTheme")
                    }
                }
            }

            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth(),
                selectedTabIndex = categoryScrollPosition,
                edgePadding = 10.dp,
            ) {


                for (category in getAllFoodCategory()) {
                    FoodCategoryChips(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryScrollPosition(
                                category.ordinal
                            )
                        },
                        onExecuteSearch = {
                            newSearch()
                        }
                    )
                }
            }
        }

    }
}