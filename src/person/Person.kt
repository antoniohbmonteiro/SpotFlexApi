package br.com.antoniomonteiro.person

import com.google.gson.annotations.SerializedName
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.DecimalColumnType
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal

data class Person(
    val id: Long,
    val name: String,
    val credit: BigDecimal
)

internal object PersonTable : LongIdTable() {
    fun toModel(row: ResultRow): Person=
            Person(
                name = row[PersonTable.name],
                credit = row[PersonTable.credit],
                id = row[PersonTable.personId]
            )

    val personId = long("person_id").uniqueIndex()
    val name = varchar("name", 50)
    val credit = decimal("credit", precision = 38, scale = 2)
}

//internal class PersonDao(id: EntityID<Long>) : LongEntity(id){
//    companion object : LongEntityClass<PersonDao>(PersonTable)
//
//    private val name by PersonTable.name
//    private val credit by PersonTable.credit
//
//    fun toModel(): Person {
//        return Person(id.value, name, credit)
//    }
//}
