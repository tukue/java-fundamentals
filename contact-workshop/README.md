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

## Data entry rules

Enforced by `ContactService` before anything is stored:

- First and last name – required, and must contain at least one letter (a name is text, not a number)
- Phone – required, digits only (`0`–`9`)
- E-mail – must be unique (the format itself is not checked)

Invalid input is reported with a message and the form is asked again.

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
    │   ├── ContactFileStore.java   # save/load the app state to contacts.txt
    │   └── ContactApp.java         # menu + console I/O
    └── test/java/se/lexicon/
        ├── ContactRepositoryTest.java
        ├── ContactServiceTest.java
        └── ContactFileStoreTest.java
```

## CI/CD

The `Contact workshop CI/CD` GitHub Actions workflow runs on pushes and pull requests
that change `contact-workshop/` or its workflow, and can also be run manually.
It uses Java 25 and Maven to run `clean verify`, then uploads the test reports
and, after a successful build, the executable application JAR.

Download the application artifact from the workflow run, unzip it, and run with Java 25:

```bash
java -jar contact-workshop-1.0-SNAPSHOT.jar
```

The app saves `contacts.txt` in the directory where it is run. Delivery is through
workflow artifacts; no hosted deployment or release publishing is configured.

Challenge 2 tests cover successful and rejected repository/service operations,
null required fields, numeric names, trimming, leading-zero phone numbers,
keeping a contact's own email during update, and avoiding ID reuse after deletion.
