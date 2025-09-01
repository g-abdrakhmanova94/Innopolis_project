package attestations3.helpers;

public class HttpCodeRestAssured {
    /**
     * Класс, содержащий статические константы HTTP-кодов.
     * Коды включают:
     * - OK (200) - успешный запрос.
     * - CREATED (201) - ресурс успешно создан.
     * - MOVEDPERMANENTLY (301) - ресурс перемещён.
     * - BADREQUEST (400) - неверный запрос.
     * - UNAUTHORIZED (401) - неавторизован.
     * - FORBIDDEN (403) - доступ запрещён.
     * - NOTFOUND (404) - ресурс не найден.
     * - METHODNOTALLOWED (405) - метод не разрешён.
     * - UNSUPPORTEDMEDIATYPE (415) - неподдерживаемый тип данных.
     * - INTERNALSERVERERROR (500) - внутренняя ошибка сервера.
     */
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int INTERNAL_SERVER_ERROR = 500;
}

