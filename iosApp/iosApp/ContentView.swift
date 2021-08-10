import SwiftUI
import shared

struct ContentView: View {
    
    let persistenceSdk: PersistenceSDK
    
    @State private var persons: [Person]
    
    init(persistenceSdk: PersistenceSDK) {
        self.persistenceSdk = persistenceSdk
        self.persons = persistenceSdk.getAllPersons()
    }

	var body: some View {
        List(persons, id: \.self) { person in
            HStack {
                Spacer()
                PersonCardView(person: person).background(Color.gray).cornerRadius(10)
                Spacer()
            }
        }.animation(/*@START_MENU_TOKEN@*/.easeIn/*@END_MENU_TOKEN@*/)
        
        ButtonRow(
            onAdd: {
                persistenceSdk.insertPerson(person: Person(name: "iOS", email: "time.apple@icloud.com", phone: "123-456-7890", ssn: 123123))
                persons = persistenceSdk.getAllPersons()
            }, onClear: {
                persistenceSdk.clearDatabase()
                persons = persistenceSdk.getAllPersons()
            }
        )
	}
}

struct PersonCardView: View {
    let person: Person
    
    var body: some View {
            HStack {
                ImagePlaceholderView().padding()
                
                VStack(alignment: .leading, spacing: 4) {
                    Text("name \(person.name)")
                        .foregroundColor(.white)
                        .font(.system(size: 18, weight: .semibold))
                        .padding()
                    Text("phone: \(person.phone)")
                        .foregroundColor(.white)
                        .font(.footnote)
                    Text("id: \(person.id!)")
                        .foregroundColor(.white)
                        .font(.footnote)
                }
            }.padding()
        }
}

struct ImagePlaceholderView: View {
    var body: some View {
        Image(systemName: "applelogo")
            .foregroundColor(.white)
            .background(Color.gray)
    }
}

struct ButtonRow: View {
    let onAdd: () -> Void
    let onClear: () -> Void
    
    var body: some View {
        HStack(alignment: .center, spacing: 40) {
            Button("Add a person") {
                onAdd()
            }
            .foregroundColor(.white)
                .padding()
                .background(Color.blue)
                .cornerRadius(8)
            
            Button("Clear database") {
                onClear()
            }
            .foregroundColor(.black)
                .padding()
                .background(Color.red)
                .cornerRadius(8)
        }
    }
}
