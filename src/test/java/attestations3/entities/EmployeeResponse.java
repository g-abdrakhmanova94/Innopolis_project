package attestations3.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeResponse {
    /**
     * Класс для представления ответа о сотруднике.
     * Поля:
     * - city: город сотрудника.
     * - name: имя сотрудника.
     * - position: должность сотрудника.
     * - surname: фамилия сотрудника.
     * - id: уникальный идентификатор сотрудника.
     * Методы:
     * - Конструкторы для инициализации объекта.
     * - Геттеры и сеттеры для доступа к полям.
     */

    private String city;
    private String name;
    private String position;
    private String surname;
    private int id;

    @JsonCreator
    public EmployeeResponse(
            @JsonProperty(value = "city", required = true) String city,
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "position", required = true) String position,
            @JsonProperty(value = "surname", required = true) String surname,
            @JsonProperty(value = "id", required = true) int id) {
        this.city = city;
        this.name = name;
        this.position = position;
        this.surname = surname;
        this.id = id;
    }

    public EmployeeResponse() {
        // Пустой конструктор
    }

    // Добавляем геттеры
    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    // Добавляем сеттеры
    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(int id) {
        this.id = id;
    }
}
