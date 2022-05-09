package com.bafoor.dailymeal.repository

import com.bafoor.dailymeal.data.remote.RecipeApi
import com.bafoor.dailymeal.data.remote.dto.RecipeDtoMapper
import com.bafoor.dailymeal.domain.model.Recipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(
        token: String,
        page: Int,
        query: String
    ): List<Recipe> {

        return mapper.toDomainList(
            api.search(
                token,
                page,
                query
            ).recipes
        )
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(
            api.getRecipes(token, id)
        )
    }
}