package com.sadwyn.institute.birthdayreminder.data;

public enum PersonEnum {
    PLOTNIKOV_VALERIY_MIKHILOVICH("Валерий", "Михайлович", "Плотников", "26.01.1947", "0992846474"),
    ASLANOV_ALEKSEY_MIKHAILOVICH("Алексей", "Михайлович", "Асланов", "21.10.1981", "0677648067"),
    MAZUROK_IGOR_EVGENIEVICH("Игорь","Евгениевич", "Мазурок", "27.01.1960", "0677552063"),
    MAZUROK_TETIANA_LEONIDOVNA("Татьяна", "Леонидовна", "Мазурок", "13.07.1962", "0973188023"),
    MITROFANOVA_NATALIA_FEDOROVNA("Наталия", "Федоровна", "Митрофанова", "08.03.1985", "0938244254"),
    POPKOV_DENIS_NIKOLAEVICH("Денис", "Николаевич", "Попков", "03.06.1982", "0963324982"),
    VLADIMIROVA_VALENTINA_BORISOVNA("Валентина", "Борисовна", "Владимирова", "09.03.1971", "0974036885"),
    SMIRNOVA_EKATERINA_VASILIEVNA("Екатерина", "Васильевна", "Смирнова", "25.12.1983", "0936540274"),
    SELIVANOVA_ALLA_VITALIEVNA("Алла", "Витальевна", "Селиванова", "19.05.1980", "0672634283"),
    SNIGUR_TETIANA_SERGEEVNA("Татьяна", "Сергеевна", "Снигур", "11.04.1982", "0677460637"),
    CHUMACHENKO_DARIA_OLEKSANDROVNA("Дарья", "Олександровна", "Чумаченко", "20.02.1988", "0682544957"),
    SHVEC_NATALIA_VICTOTOVNA("Наталия", "Викторовна", "Швец", "06.01.1960", "0679476553"),
    ANTONOVA_ALFIA_RAISOVNA("Альфия", "Раисовна", "Антонова", "09.07.1962", "0679824034"),
    BODYUL_ELENA_STANISLAVOVNA("Елена","Станиславовна", "Бодюл", "23.07.1997", "0931371638"),
    ILYASHCHUK_YULIA_OLEGOVNA("Юлия", "Олеговна", "Ильящук", "24.07.1979", "0937065545"),
    BARKAR_STELLA_OLEGOVNA("Баркар", "Стелла", "Олеговна", "29.08.1966", "0632341202");

    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthDate;
    private String phoneNumber;

    PersonEnum(String firstName, String patronymic, String lastName, String birthDate, String phoneNumber) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
