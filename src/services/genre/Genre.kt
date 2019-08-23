package br.com.antoniomonteiro.services.genre

import org.jetbrains.exposed.dao.LongIdTable

data class Genre (
    var id : Long,
    val name : String
)

internal object GenreTable : LongIdTable(){
    val name = varchar("genre_name", 50)
}