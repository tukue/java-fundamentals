# Contact Management App – Workshop

Console contact manager. See [design.md](design.md) for the design, flowchart and test plan.

## Requirements

- Java 25
- Maven 3.9+

## Build and run

```bash
mvn -f contact-workshop/pom.xml compile
java -cp contact-workshop/target/classes se.lexicon.ContactApp
```

## Menu

```
1. View contacts
2. Add contact
3. Search contacts
4. Update contact
5. Delete contact
0. Exit
```

## Tests

```bash
mvn -f contact-workshop/pom.xml test
```

## Layout

```
contact-workshop/
├── design.md
├── pom.xml
└── src/
    ├── main/java/se/lexicon/
    │   ├── Contact.java            # data carrier
    │   ├── ContactRepository.java  # storage + ids + unique email
    │   ├── ContactService.java     # validation + business rules
    │   └── ContactApp.java         # menu + console I/O
    └── test/java/se/lexicon/
        ├── ContactRepositoryTest.java
        └── ContactServiceTest.java
```
