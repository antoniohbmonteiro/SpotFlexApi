package br.com.antoniomonteiro.person

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

data class Person(
    var id: Long,
    val name: String,
    val credit: BigDecimal
)

internal object PersonTable : LongIdTable() {
    fun toModel(row: ResultRow): Person =
        Person(
            id = row[id].value,
            name = row[name],
            credit = row[credit]
        )

    val name = varchar("name", 50)
    val credit = decimal("credit", precision = 38, scale = 2)
}
