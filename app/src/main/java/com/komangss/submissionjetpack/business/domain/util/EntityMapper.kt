package com.komangss.submissionjetpack.business.domain.util

interface EntityMapper <Entity, DomainModel> {
    fun mapFromEntity(entity: Entity) : DomainModel

    fun mapToEntity(domainModel: DomainModel) : Entity
}