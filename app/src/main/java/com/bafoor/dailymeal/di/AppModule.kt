package com.bafoor.dailymeal.di

import android.content.Context
import com.bafoor.dailymeal.presentation.ui.BaseApplication
import com.bafoor.dailymeal.data.remote.RecipeApi
import com.bafoor.dailymeal.data.remote.RecipeApi.Companion.BASE_URL
import com.bafoor.dailymeal.data.remote.dto.RecipeDtoMapper
import com.bafoor.dailymeal.repository.RecipeRepository
import com.bafoor.dailymeal.repository.RecipeRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app : Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRecipeApi() : RecipeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMapper() : RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(
         api: RecipeApi,
         mapper: RecipeDtoMapper
    ) : RecipeRepository {
        return RecipeRepositoryImpl(api, mapper)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken() : String {
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }


}

