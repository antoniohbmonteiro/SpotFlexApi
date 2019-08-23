package br.com.antoniomonteiro.services.product

import br.com.antoniomonteiro.services.category.Category
import br.com.antoniomonteiro.services.genre.Genre
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

data class Product(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val amount: Int,
    val category: Category?,
    val genre: Genre?
)

internal object ProductTable : LongIdTable() {
    val categoryId = long("category_id").nullable()
    val genreId = long("genre_id").nullable()
    val name = varchar("product_name", 50)
    val amount = integer("product_amount").default(10)
    val price = decimal("product_price", 30, 2)

    fun toModel(row: ResultRow): Product =
        Product(
            id = row[id].value,
            name = row[name],
            amount = row[amount],
            price = row[price],
            category = null,
            genre = null
        )
}