package se.lexicon;

public final class Contact {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;

    public Contact(int id, String firstName, String lastName, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return id == contact.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "#%-3d %-20s %-15s %s".formatted(
                id,
                (firstName + " " + lastName).trim(),
                phone,
                email);
    }
}
