package com.example.siki.utils;

public class Constants {
    public final class ApiUrlLocal{

        private static final String BASE_URL = "http://192.168.1.55:";
        public static final String PRODUCT_SERVICE_BASE_URL = BASE_URL +  "8090/api/products/";
        public static final String USER_SERVICE_BASE_URL = BASE_URL + "8090/api/users/";

        public static final String CART_SERVICE_BASE_URL = BASE_URL + "8090/api/carts/";

        public static final String ORDER_SERVICE_BASE_URL = BASE_URL + "8090/api/orders/";
        public static final String LOGIN_BASE_URL = BASE_URL + "8880";
    }


    public final class AuthVariable{
        public static final String SIKI_CLIENT_ID = "siki-client";

        public static final String GRANT_TYPE = "password";

        public static final String CLIENT_SECRET = "BAdQw3ubmxeJeeGXLaX8nVjescrw6n80";

        public static final String SCOPE = "profile";

    }
}
