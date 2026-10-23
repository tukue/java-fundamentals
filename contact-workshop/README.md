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
- E-mail – required, unique, and must have a basic `local@domain.suffix` shape with no whitespace
- Fields must not contain tabs or line breaks

Invalid input is reported with a message and the form is asked again.

## Menu

```
1. View contacts
2. Add contact
3. Search contacts
4. Update contact
5. Delete contact
6. Export contacts (CSV)
7. Import contacts (CSV)
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
    │   ├── Contact.java                # data carrier
    │   ├── ContactRepository.java      # storage + ids + unique email
    │   ├── ContactService.java         # validation + business rules
    │   ├── ContactFileStore.java       # save/load the app state to contacts.txt
    │   ├── ContactExporter.java        # export format interface
    │   ├── ContactImporter.java        # import format interface
    │   ├── CsvContactExporter.java     # the CSV dialect (RFC 4180)
    │   ├── CsvContactImporter.java     # CSV parser + validation of every row
    │   ├── ImportOutcome.java          # parsed contacts + warnings
    │   ├── ContactTransferService.java # reads/writes the CSV files
    │   └── ContactApp.java             # menu + console I/O
    └── test/java/se/lexicon/
        ├── ContactRepositoryTest.java
        ├── ContactServiceTest.java
        ├── ContactFileStoreTest.java
        ├── CsvContactExporterTest.java
        ├── CsvContactImporterTest.java
        ├── ContactTransferServiceTest.java
        └── ContactAppTest.java
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

Challenge 3 tests cover CSV export/import: header and RFC 4180 quoting,
column mapping by name, every validation rule applied to each imported row,
malformed quoting, missing columns, duplicate e-mails (in the app and inside
the file), free/taken id handling, missing or empty files, and export → import
round trips. Imported rows go through the same `ContactService` validation as
console entry, so a file cannot insert a contact the console would reject.
