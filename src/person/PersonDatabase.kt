package br.com.antoniomonteiro.person

import br.com.antoniomonteiro.SpotFlexDatabase
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PersonDatabase {

    private val db by lazy {
        SpotFlexDatabase.createDb()
    }

    fun getAll(): List<Person> = transaction(db) {
        PersonTable
            .selectAll()
            .map { PersonTable.toModel(it) }
            .toList()
    }

    fun get(index: Long): Person? {
        return transaction(db) {
            PersonTable
                .select { PersonTable.personId eq index }
                .singleOrNull()
                ?.toPerson()
        }
    }

}

fun  ResultRow.toPerson():  Person=
    Person(
        name = this[PersonTable.name],
        credit = this[PersonTable.credit],
        id = this[PersonTable.personId]
    )
