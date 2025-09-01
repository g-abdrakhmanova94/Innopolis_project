package attestations3.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValidationErrorResponse {
    /**
     * Класс для представления ответа об ошибках валидации.
     * Поля:
     * - message: сообщение об ошибке.
     * - error: тип ошибки.
     * - wrongTypeFields: список полей с неверным типом.
     * - emptyFields: список пустых полей.
     * Методы:
     * - Конструкторы для инициализации объекта.
     * - Геттеры и сеттеры для всех полей.
     */
    private String message;

    private String error;

    @JsonProperty(value = "wrong_type_fields")
    private List<String> wrongTypeFields;

    @JsonProperty(value = "empty_fields") // Добавлено для соответствия JSON
    private List<String> emptyFields; // Новое поле

    public ValidationErrorResponse(String message, String error, List<String> wrongTypeFields, List<String> emptyFields) {
        this.message = message;
        this.error = error;
        this.wrongTypeFields = wrongTypeFields;
        this.emptyFields = emptyFields; // Инициализация нового поля
    }

    public ValidationErrorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getWrongTypeFields() {
        return wrongTypeFields;
    }

    public void setWrongTypeFields(List<String> wrongTypeFields) {
        this.wrongTypeFields = wrongTypeFields;
    }

    public List<String> getEmptyFields() {
        return emptyFields; // Геттер для нового поля
    }

    public void setEmptyFields(List<String> emptyFields) {
        this.emptyFields = emptyFields; // Сеттер для нового поля
    }
}

