package com.s4m.jee.studentmanagement.domain;

/**
 * Represents a student and the room, age and address stored in the school database.
 */
public class Student {

    private String number;
    private int roomNumber;
    private String name;
    private int age;
    private String address;

    /**
     * Creates an instance with the supplied values.
     *
     * @param number the unique student number
     * @param roomNumber the assigned room number
     * @param name the student name
     * @param age the age in years
     * @param address the postal address
     */
    public Student(String number, int roomNumber, String name, int age, String address) {
        super();
        this.number = number;
        this.roomNumber = roomNumber;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    /**
     * Creates an empty instance with default values.
     */
    public Student() {
        this("", 0, "", 0, "");
    }

    /**
     * Returns the number.
     *
     * @return the current number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the unique student number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns the room number.
     *
     * @return the current room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber the assigned room number
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Returns the name.
     *
     * @return the current name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the student name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age.
     *
     * @return the current age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age.
     *
     * @param age the age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the address.
     *
     * @return the current address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the postal address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the existing textual representation of this student.
     *
     * @return the current representation
     */
    @Override
    public String toString() {
        return "Eleve [num=" + number + ", no=" + roomNumber + ", nom=" + name + ", age=" + age + ", adresse=" + address + "]";
    }

    /**
     * Prints the student representation to standard output.
     */
    public void print() {
        System.out.println(this.toString());
    }
}
