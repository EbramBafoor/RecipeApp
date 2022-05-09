package com.bafoor.dailymeal.data.remote

import com.bafoor.dailymeal.data.remote.dto.RecipeNetworkDto
import com.bafoor.dailymeal.data.remote.dto.RecipeSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeApi {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponseDto

    @GET("get")
    suspend fun getRecipes(
        @Header("Authorization") token: String,
        @Query("id") id : Int,
    ) : RecipeNetworkDto

    companion object {
        const val BASE_URL = "https://food2fork.ca/api/recipe/"
    }
}