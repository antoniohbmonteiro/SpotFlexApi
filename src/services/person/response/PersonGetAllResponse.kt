package br.com.antoniomonteiro.services.person.response

import br.com.antoniomonteiro.services.person.Person

data class PersonGetAllResponse(
    val persons: List<Person>
)