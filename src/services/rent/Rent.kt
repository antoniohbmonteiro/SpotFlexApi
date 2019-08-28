package br.com.antoniomonteiro.services.rent

import org.jetbrains.exposed.dao.LongIdTable
import org.joda.time.DateTime

data class Rent(
    val id: Long,
    val personId: Long,
    val productId: Long,
    val date: DateTime,
    val status: RentStatus
)

internal object RentTable : LongIdTable() {
    val personId = long("person_id")
    val productId = long("product_id")
    val date = datetime("rent_date")
    val status = enumeration("rent_status", RentStatus::class)
}

enum class RentStatus {

}