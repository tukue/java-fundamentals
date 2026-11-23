package se.lexicon.streams.people;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private String city;
    private boolean active;

    public Person(String name, int age, String city, boolean active) {
        this.name = Objects.requireNonNull(name, "name");
        this.age = age;
        this.city = Objects.requireNonNull(city, "city");
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = Objects.requireNonNull(city, "city");
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + name +
                ", age=" + age +
                ", city=" + city +
                ", active=" + active +
                '}';
    }
}
