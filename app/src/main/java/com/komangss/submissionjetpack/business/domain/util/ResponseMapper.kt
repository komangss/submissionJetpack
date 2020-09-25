package com.komangss.submissionjetpack.business.domain.util

interface ResponseMapper <Response, DomainModel> {
    fun mapFromResponse(response: Response) : DomainModel

    fun mapToResponse(domainModel: DomainModel) : Response
}