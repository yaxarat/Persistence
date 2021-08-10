package dev.atajan.persistence

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val databaseQueries = database.appDatabaseQueries

    internal fun clearDatabase() {
        databaseQueries.removeAllPerson()
    }

    internal fun insertPerson(person: Person) {
        databaseQueries.insertPerson(
            name = person.name,
            email = person.email,
            phone = person.phone,
            ssn = person.ssn.toLong()
        )
    }

    internal fun getAllEntries(): List<Person> {
        return databaseQueries.selectAll().executeAsList()
            .map { personEntity ->
                personEntity.toPerson()
            }
    }

    private fun PersonEntity.toPerson(): Person {
        return Person(
            id = id.toInt(),
            name = name,
            email = email,
            phone = phone,
            ssn = ssn.toInt()
        )
    }
}