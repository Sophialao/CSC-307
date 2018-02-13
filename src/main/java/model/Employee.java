package model;

public abstract class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private int sickDays;
    private int vacationDays;

    public Employee() {}
    public Employee(String id, String firstName, String lastName, int sickDays, int vacationDays) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sickDays = sickDays;
        this.vacationDays = vacationDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSickDays() {
        return sickDays;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }
}