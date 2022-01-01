package com.komangss.submissionjetpack.core.utils.mapper

interface MapperInterface<Domain,  DB, Remote> {
    fun responseToEntity(response : Remote) : DB

    fun entityToDomain(entity : DB ) : Domain

//    Note :  We Don't have insert to remote so we don;t need this
//    fun domainToResponse(d :  Domain) : Remote

    fun domainToEntity( domain : Domain ) : DB

    fun entitiesToDomains(entities: List<DB>): List<Domain>

    fun responsesToEntities(responses: List<Remote>) : List<DB>

    fun responseToDomain(response : Remote) : Domain

    fun responsesToDomains(responses : List<Remote>) : List<Domain>
}