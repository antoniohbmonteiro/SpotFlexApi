package br.com.antoniomonteiro

import org.jetbrains.exposed.sql.Database

class SpotFlexDatabase {

    companion object {
        fun createDb(): Database {
            return Database.connect("jdbc:mysql://localhost:3306/spotflex_api", driver = "com.mysql.jdbc.Driver",
                user = "root", password = "Ahbm101292*")
        }
    }

}