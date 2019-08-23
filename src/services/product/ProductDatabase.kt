package br.com.antoniomonteiro.services.product

import br.com.antoniomonteiro.SpotFlexDatabase
import br.com.antoniomonteiro.services.category.Category
import br.com.antoniomonteiro.services.category.CategoryTable
import br.com.antoniomonteiro.services.genre.Genre
import br.com.antoniomonteiro.services.genre.GenreTable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductDatabase {
    private val db by lazy {
        SpotFlexDatabase.createDb()
    }

    fun get(index: Long): Product? {
        return transaction(db) {
            SchemaUtils.create(ProductTable)
            ProductTable
                .select { ProductTable.id eq index }
                .singleOrNull()
                ?.toProduct()
        }
    }

    fun getAll(): List<Product> {
        return transaction(db) {
            SchemaUtils.create(ProductTable)
            ProductTable
                .join(CategoryTable, JoinType.LEFT, ProductTable.categoryId, CategoryTable.id)
                .join(GenreTable, JoinType.LEFT, ProductTable.genreId, GenreTable.id)
                .selectAll()
                .orderBy(ProductTable.id)
                .map { it.toProduct() }
        }
    }

    private fun ResultRow.toProduct(): Product {
        var category : Category? = null
        var genre : Genre? = null
        this.getOrNull(CategoryTable.id)?.let {
            category = Category(
                it.value,
                this[CategoryTable.name]
            )
        }
        this.getOrNull(GenreTable.id)?.let {
            genre = Genre(
                it.value,
                this[GenreTable.name]
            )
        }

        return Product(
            id = this[ProductTable.id].value,
            name = this[ProductTable.name],
            amount = this[ProductTable.amount],
            price = this[ProductTable.price],
            category = category,
            genre = genre
        )
    }


}