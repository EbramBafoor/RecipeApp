package com.bafoor.dailymeal.data.remote.dto

import com.bafoor.dailymeal.domain.model.Recipe
import com.bafoor.dailymeal.domain.util.DomainMapper

class RecipeDtoMapper :
    DomainMapper<RecipeNetworkDto, Recipe> {
    override fun mapToDomainModel(model: RecipeNetworkDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            publisher = model.publisher,
            featuredImage = model.featuredImage,
            rating = model.rating,
            sourceUrl = model.sourceUrl,
            description = model.description,
            cookingInstruction = model.cookingInstructions,
            ingredients = model.ingredients ?: listOf(),
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeNetworkDto {
        return RecipeNetworkDto(
            pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstruction,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated
        )
    }

    fun toDomainList(initial : List<RecipeNetworkDto>) : List<Recipe> {
        return initial.map { mapToDomainModel(it) }
        // map method loop throw over all entity network and convert it to domain model entity
    }

    fun fromDomainList(initial : List<Recipe>) : List<RecipeNetworkDto> {
        return initial.map { mapFromDomainModel(it) }
        // map method loop throw over all domain model entity and convert it to entity network
    }
}