package dev.atajan.persistence

class PersistenceSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    fun getAllPersons(): List<Person> {
        return database.getAllEntries()
    }

    fun insertPerson(person: Person) {
        database.insertPerson(person)
    }

    fun clearDatabase() {
        database.clearDatabase()
    }
}