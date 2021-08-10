package dev.atajan.persistence.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.atajan.persistence.DatabaseDriverFactory
import dev.atajan.persistence.PersistenceSDK
import dev.atajan.persistence.Person

class MainActivity : AppCompatActivity() {

    private val persistenceSDK = PersistenceSDK(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var peopleList by remember { mutableStateOf(persistenceSDK.getAllPersons()) }

            MaterialTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    MessageList(peopleList, modifier = Modifier.fillMaxHeight(0.9f))
                    ButtonRow(
                        persistenceSDK = persistenceSDK,
                        updateList = { peopleList = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageList(
    personList: List<Person>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(personList) { person ->
            PersonCard(person, modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
    }
}

@Composable
fun PersonCard(
    person: Person,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_android),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    )
            )

            Column (
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "name: ${ person.name }",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "phone: ${ person.phone }",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "id number: ${ person.id }",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun ButtonRow(
    persistenceSDK: PersistenceSDK,
    updateList: (List<Person>) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
    ) {
        Button(
            onClick = {
                persistenceSDK.insertPerson(
                    Person(
                        name = "Android",
                        email = "larry@google.com",
                        phone = "111-111-1112",
                        ssn = 98933242
                    )
                )

                updateList(persistenceSDK.getAllPersons())
            }
        ) {
            Text("Add a person", style = MaterialTheme.typography.button)
        }

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            onClick = {
                persistenceSDK.clearDatabase()
                updateList(persistenceSDK.getAllPersons())
            }
        ) {
            Text("Clear database", style = MaterialTheme.typography.button)
        }
    }
}