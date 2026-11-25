package models;

// Person.java - Abstract class demonstrating OOP Abstraction and Inheritance
public abstract class Person {
    // Protected fields accessible to subclass Employee
    protected String name;
    protected int age;
    protected String gender;
    protected String phone;

    // Default constructor
    public Person() {
    }

    // Parameterized constructor using 'this' keyword
    public Person(String name, int age, String gender, String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    // Getters and Setters (Encapsulation)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Abstract method: must be implemented by subclasses (Polymorphism & Abstraction)
    public abstract void displayInfo();
}
