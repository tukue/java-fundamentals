# Design – Contact Management App (ContactApp)

Console app for managing contacts: add, view, search, update and delete.

## Requirements analysis

**Functionality (basic scope)**

| # | Operation | User expectation |
|---|-----------|------------------|
| 1 | View all contacts | See everything currently stored |
| 2 | Add a contact | Enter name, phone, email and get confirmation or a clear error |
| 3 | Search | Find contacts by (part of) a name or phone number |
| 4 | Update | Change an existing contact, found by its id |
| 5 | Delete | Remove a contact by id, after confirmation |
| 0 | Exit | Close the app gracefully |

**Data** – a contact holds: `id` (int, generated), `firstName`, `lastName`, `phone`, `email` (Strings).

**Data structure** – `LinkedHashMap<Integer, Contact>`

- Keyed by id → O(1) `get`/`remove` for update and delete
- The map key itself guarantees unique ids (a state that must never be violated)
- Keeps insertion order → predictable listing (a `TreeMap` or sorting step would cover Challenge 1)
- Trade-off: search by name is O(n). Acceptable – a contact list is small and linear scans keep the code simple (KISS)

**Constraints – invalid states that must never be allowed**

1. Ids are auto-generated, never reused, never duplicated
2. Email must be unique – enforced by the repository (storage invariant)
3. Update/delete require an id that exists

> Input validation (non-blank names, email/phone format) is disabled for now and can be
> re-introduced as an extension. The service only guards the rules above.

**Code organisation – separation of concerns**

| Class | Responsibility | Principle |
|-------|----------------|-----------|
| `Contact` | Data carrier only | SRP |
| `ContactRepository` | Storage, id generation, unique-email invariant | SRP, storage isolated |
| `ContactService` | Orchestration + business rules (unique email, id exists) | Business rules isolated |
| `ContactApp` | Menu loop and `Scanner` I/O, nothing else | UI isolated |

The UI never touches the map directly and the repository never prints – each operation has one clear home.

## Flowchart

```mermaid
flowchart TD
    Start([Start]) --> Menu[Show menu: 1 List 2 Add 3 Search 4 Update 5 Delete 0 Exit]
    Menu --> Choice{Choice?}

    Choice -->|1| List[Service.list - print contacts or 'No contacts']
    List --> Menu

    Choice -->|2| AddName[Prompt first name, last name, phone, email]
    AddName --> AddRes{Service.add ok?}
    AddRes -->|No| Err1[Print error - duplicate email] --> Menu
    AddRes -->|Yes| AddSave[Print confirmation with new id] --> Menu

    Choice -->|3| Search[Prompt search text]
    Search --> Found{Matches?}
    Found -->|No| Err3[Print 'not found'] --> Menu
    Found -->|Yes| PrintMatches[Print matching contacts] --> Menu

    Choice -->|4| UpdId[Prompt id]
    UpdId --> Exists1{Contact exists?}
    Exists1 -->|No| Err4[Print 'no contact with id'] --> Menu
    Exists1 -->|Yes| UpdFields[Show current values - prompt new values]
    UpdFields --> UpdRes{Service.update ok?}
    UpdRes -->|No| Err5[Print error - duplicate email] --> Menu
    UpdRes -->|Yes| UpdSave[Print confirmation] --> Menu

    Choice -->|5| DelId[Prompt id]
    DelId --> Exists2{Contact exists?}
    Exists2 -->|No| Err4
    Exists2 -->|Yes| Confirm{Confirm delete?}
    Confirm -->|No| Menu
    Confirm -->|Yes| Del[Service.delete - print confirmation] --> Menu

    Choice -->|0| Exit[Print goodbye] --> End([End])
    Choice -->|other| Err6[Print invalid option] --> Menu
```

## Class diagram

```mermaid
classDiagram
    class Contact {
        -int id
        -String firstName
        -String lastName
        -String phone
        -String email
        +getId() int
        +getFirstName() String
        +getLastName() String
        +getPhone() String
        +getEmail() String
        +toString() String
    }

    class ContactRepository {
        -Map~Integer,Contact~ contacts
        -int nextId
        +add(first, last, phone, email) Contact
        +findAll() List~Contact~
        +findById(id) Optional~Contact~
        +search(query) List~Contact~
        +update(id, first, last, phone, email) Optional~Contact~
        +delete(id) boolean
        +emailExists(email, excludeId) boolean
    }

    class ContactService {
        -ContactRepository repository
        +add(first, last, phone, email) ValidationResult
        +update(id, first, last, phone, email) ValidationResult
        +delete(id) ValidationResult
        +find(id) Optional~Contact~
        +list() List~Contact~
        +search(query) List~Contact~
        -validate(first, last, phone, email, excludeId) String
    }

    class ValidationResult {
        <<record>>
        -boolean ok
        -String message
        +isOk() boolean
        +getMessage() String
    }

    class ContactApp {
        -Scanner SCANNER
        +main(args) void
        -runMenu() void
        -promptContact() String[]
        -promptId() int
        -viewContacts() void
        -addContact() void
        -searchContacts() void
        -updateContact() void
        -deleteContact() void
    }

    ContactApp --> ContactService : uses
    ContactService --> ContactRepository : delegates
    ContactService --> ValidationResult : returns
    ContactRepository --> Contact : stores
```

## Test plan (Challenge 2)

`src/test/java/se/lexicon/` – JUnit 5, one positive and one negative case per data-access operation.

| Operation | Positive | Negative |
|-----------|----------|----------|
| `add` (repo) | stored with generated id, visible in `findAll` | duplicate email rejected |
| `search` (repo) | case-insensitive match on name/phone | no match → empty list |
| `update` (repo) | fields replaced, id kept | unknown id → empty |
| `delete` (repo) | removed, `findById` empty after | unknown id → `false`, list untouched |
| `add` (service) | valid input → ok | duplicate email → rejected |
| `update` (service) | valid → ok | unknown id, duplicate email |
| `delete` (service) | valid → ok | unknown id |

> Format validation (email/phone/blank) is out of scope for now, so no tests cover it.

## Out of scope (YAGNI – later challenges)

- Input validation (email/phone format, required fields) – deferred for now
- Challenge 1: sorted output
- Challenge 3: file export/import (plain text, CSV, Excel)
- GUI, database, networking
