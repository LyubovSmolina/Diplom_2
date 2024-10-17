package ru.praktikum;

public class CONST {
    //Константы кодов ответа от бэкенда
    public static int CREATED_201  = 201;
    public static int OK_200 = 200;
    public static int ACCEPT_202 = 202;
    public static int CONFLICT_409 = 409;
    public static int BAD_REQUEST_400 = 400;
    public static int NOT_FOUND_404 = 404;
    public static int FORBIDDEN_403 = 403;
    public static int UNAUTHORIZED_401 = 401;


    //Константы ручек

    //Клиент
    public static String CREATE_ACCOUNT = "/api/auth/register"; //POST - регистрация клиента
    public static String DELETE_ACCOUNT = "/api/auth/user"; //DELETE - удаление клиента
    public static String LOG_IN_ACCOUNT = "/api/auth/login"; //POST - авторизация
    public static String UPDATE_USER_DATA = "/api/auth/user"; //PATCH - обновление данных пользователя

    //Заказ
    public static String CREATE_ORDER_POST = "/api/orders"; //POST - создание заказа
    public static String GET_USER_ORDERS_GET = "/api/orders"; //GET - получение списка заказов пользователя

}
