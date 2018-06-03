package com.sadwyn.institute.birthdayreminder.data;

public enum PersonEnum {
    POPKOV_DENIS_NIKOLAEVICH("Денис", "Николаевич", "Попков", "15.06.1987"),
    SELIVANOVA_ALLA_VITALIEVNA("Алла", "Витальевна", "Селиванова", "14.11.1987");

    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthDate;

    PersonEnum(String firstName, String patronymic, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
