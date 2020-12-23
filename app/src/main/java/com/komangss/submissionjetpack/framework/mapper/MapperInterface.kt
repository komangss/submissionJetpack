package com.komangss.submissionjetpack.framework.mapper

interface MapperInterface<Domain,  DB, Remote> {
    fun responseToEntity(response : Remote) : DB

    fun entityToDomain(entity : DB ) : Domain

//    Note :  We Don't have insert to remote so we don;t need this
//    fun domainToResponse(d :  Domain) : Remote
//    Note :  We Don't have insert to db so we don;t need this
//    fun domainToEntity( d : Domain ) : DB

    fun entitiesToDomains(entities: List<DB>): List<Domain>

    fun responsesToEntities(responses: List<Remote>) : List<DB>
}