package br.com.antoniomonteiro.person

import br.com.antoniomonteiro.SpotFlexDatabase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PersonDatabase {

    private val db by lazy {
        SpotFlexDatabase.createDb()
    }

    fun getAll(): List<Person> = transaction(db) {
        SchemaUtils.create(PersonTable)
        PersonTable
            .selectAll()
            .map { PersonTable.toModel(it) }
            .toList()
    }

    fun get(index: Long): Person? {
        return transaction(db) {
            SchemaUtils.create(PersonTable)
            PersonTable
                .select { PersonTable.id eq index }
                .singleOrNull()
                ?.toPerson()
        }
    }

    fun add(person: Person): Person {
        return transaction(db) {
            SchemaUtils.create(PersonTable)
            val id = PersonTable
                .insertAndGetId {
                    it[name] = person.name
                    it[credit] = person.credit
                }

            person.id = id.value

            person
        }
    }

    fun delete(index: Long) {
        transaction(db) {
            SchemaUtils.create(PersonTable)
            PersonTable
                .deleteWhere {
                    PersonTable.id eq index
                }
        }
    }

    fun update(person: Person) {
        transaction(db) {
            SchemaUtils.create(PersonTable)
            PersonTable
                .update({ PersonTable.id eq person.id }) {
                    it[name] = person.name
                    it[credit] = person.credit
                }
        }
    }

}

fun ResultRow.toPerson(): Person =
    Person(
        name = this[PersonTable.name],
        credit = this[PersonTable.credit],
        id = this[PersonTable.id].value
    )
