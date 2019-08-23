package br.com.antoniomonteiro.services.genre

import br.com.antoniomonteiro.SpotFlexDatabase
import br.com.antoniomonteiro.services.person.PersonTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class GenreDatabase {

    private val db by lazy {
        SpotFlexDatabase.createDb()
    }

    fun get(index: Long): Genre? {
        return transaction(db) {
            SchemaUtils.create(GenreTable)
            GenreTable
                .select { GenreTable.id eq index }
                .singleOrNull()
                ?.toGenre()
        }
    }

    fun getAll(): List<Genre> {
        return transaction(db) {
            SchemaUtils.create(GenreTable)
            GenreTable
                .selectAll()
                .map { it.toGenre() }
        }
    }


    fun add(genre: Genre): Genre {
        return transaction(db) {
            SchemaUtils.create(GenreTable)
            val id = PersonTable
                .insertAndGetId {
                    it[name] = genre.name
                }

            genre.id = id.value

            genre
        }
    }

    private fun ResultRow.toGenre(): Genre =
        Genre(
            id = this[GenreTable.id].value,
            name = this[GenreTable.name]
        )
}