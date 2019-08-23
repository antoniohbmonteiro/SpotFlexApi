package br.com.antoniomonteiro.services.category

import org.jetbrains.exposed.dao.LongIdTable

data class Category(
    var id: Long,
    val name: String
)

internal object CategoryTable : LongIdTable() {
    val name = varchar("category_name", 50)
}