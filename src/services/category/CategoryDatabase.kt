package br.com.antoniomonteiro.services.category

import br.com.antoniomonteiro.SpotFlexDatabase
import br.com.antoniomonteiro.services.person.PersonTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryDatabase {

    private val db by lazy {
        SpotFlexDatabase.createDb()
    }

    fun get(index: Long): Category? {
        return transaction(db) {
            SchemaUtils.create(CategoryTable)
            CategoryTable
                .select { CategoryTable.id eq index }
                .singleOrNull()
                ?.toCategory()
        }
    }

    fun getAll(): List<Category> {
        return transaction(db) {
            SchemaUtils.create(CategoryTable)
            CategoryTable
                .selectAll()
                .map { it.toCategory() }
        }
    }


    fun add(category: Category): Category {
        return transaction(db) {
            SchemaUtils.create(CategoryTable)
            val id = PersonTable
                .insertAndGetId {
                    it[name] = category.name
                }

            category.id = id.value

            category
        }
    }

    private fun ResultRow.toCategory(): Category =
        Category(
            id = this[CategoryTable.id].value,
            name = this[CategoryTable.name]
        )
}