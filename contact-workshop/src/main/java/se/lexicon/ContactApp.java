package se.lexicon;

import java.util.Scanner;

public class ContactApp {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final ContactRepository repository = new ContactRepository();
    private final ContactService service = new ContactService(repository);
    private final ContactFileStore fileStore = new ContactFileStore();

    public static void main(String[] args) {
        new ContactApp().runMenu();
    }

    private void runMenu() {
        System.out.println("=================================");
        System.out.println("       Contact Manager");
        System.out.println("=================================");
        loadContacts();

        while (true) {
            printMenu();
            if (!SCANNER.hasNextLine()) {
                saveContacts();
                return;
            }
            var choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> viewContacts();
                case "2" -> addContact();
                case "3" -> searchContacts();
                case "4" -> updateContact();
                case "5" -> deleteContact();
                case "0" -> {
                    saveContacts();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    private void loadContacts() {
        var loaded = fileStore.load();
        repository.loadAll(loaded);
        if (!loaded.isEmpty()) {
            System.out.println("Loaded " + loaded.size() + " contact(s) from " + fileStore.getFile().getFileName());
        }
    }

    private void saveContacts() {
        fileStore.save(repository.findAll());
        System.out.println("Saved " + repository.findAll().size() + " contact(s) to "
                + fileStore.getFile().getFileName());
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. View contacts");
        System.out.println("2. Add contact");
        System.out.println("3. Search contacts");
        System.out.println("4. Update contact");
        System.out.println("5. Delete contact");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void viewContacts() {
        var contacts = service.list();
        if (contacts.isEmpty()) {
            System.out.println("No contacts on file.");
            return;
        }
        printHeader();
        contacts.forEach(c -> System.out.println(c));
        System.out.println(contacts.size() + " contact(s)");
    }

    private void addContact() {
        System.out.println("Add contact");
        while (true) {
            var input = promptContact();
            if (input == null) {
                return;
            }
            var result = service.add(input[0], input[1], input[2], input[3]);
            if (result.ok()) {
                printResult(result);
                return;
            }
            printInvalid(result);
        }
    }

    private void searchContacts() {
        System.out.print("Search name or phone: ");
        var query = SCANNER.nextLine().trim();
        var matches = service.search(query);
        if (matches.isEmpty()) {
            System.out.println("No contacts match \"" + query + "\".");
            return;
        }
        printHeader();
        matches.forEach(c -> System.out.println(c));
    }

    private void updateContact() {
        var id = promptId("Update contact - id: ");
        var existing = service.find(id);
        if (existing.isEmpty()) {
            System.out.println("No contact with id " + id);
            return;
        }
        var current = existing.get();
        System.out.println("Current: " + current);
        System.out.println("(leave a field empty to keep the current value)");

        while (true) {
            var firstName = promptValue("First name [" + current.getFirstName() + "]: ", current.getFirstName());
            var lastName = promptValue("Last name [" + current.getLastName() + "]: ", current.getLastName());
            var phone = promptValue("Phone [" + current.getPhone() + "]: ", current.getPhone());
            var email = promptValue("Email [" + current.getEmail() + "]: ", current.getEmail());

            var result = service.update(id, firstName, lastName, phone, email);
            if (result.ok()) {
                printResult(result);
                return;
            }
            printInvalid(result);
        }
    }

    private void deleteContact() {
        var id = promptId("Delete contact - id: ");
        if (service.find(id).isEmpty()) {
            System.out.println("No contact with id " + id);
            return;
        }
        System.out.print("Delete contact #" + id + "? (yes/no): ");
        var answer = SCANNER.nextLine().trim().toLowerCase();
        if (!answer.equals("yes")) {
            System.out.println("Delete cancelled.");
            return;
        }
        printResult(service.delete(id));
    }

    private String[] promptContact() {
        var firstName = readLine("First name: ");
        if (firstName == null) {
            return null;
        }
        var lastName = readLine("Last name: ");
        if (lastName == null) {
            return null;
        }
        var phone = readLine("Phone: ");
        if (phone == null) {
            return null;
        }
        var email = readLine("Email: ");
        if (email == null) {
            return null;
        }
        return new String[]{firstName, lastName, phone, email};
    }

    private int promptId(String label) {
        while (true) {
            System.out.print(label);
            var input = readLine("");
            if (input == null) {
                return -1;
            }
            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            }
            System.out.println("Please enter a number.");
        }
    }

    private String promptValue(String label, String current) {
        var value = readLine(label);
        return value == null || value.isEmpty() ? current : value;
    }

    private String readLine(String label) {
        System.out.print(label);
        if (!SCANNER.hasNextLine()) {
            return null;
        }
        return SCANNER.nextLine().trim();
    }

    private void printResult(ContactService.ValidationResult result) {
        System.out.println((result.ok() ? "OK: " : "Error: ") + result.message());
    }

    private void printInvalid(ContactService.ValidationResult result) {
        System.out.println("Error: " + result.message() + " Please try again.");
    }

    private void printHeader() {
        System.out.printf("%-5s %-20s %-15s %s%n", "ID", "NAME", "PHONE", "EMAIL");
        System.out.println("-".repeat(58));
    }
}
