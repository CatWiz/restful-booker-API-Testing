package org.example.properties;

import java.util.ResourceBundle;

public class RestfulBookerProperties {
    public static String getBaseUrl() {
        return getProperty("base_url");
    }
    public static String getUsername() {
        return getProperty("username");
    }
    public static String getPassword() {
        return getProperty("password");
    }
    public static String getAuthUrl() {
        return getBaseUrl() + getProperty("auth_url_postfix");
    }
    public static String getBookingUrl() {
        return getBaseUrl() + getProperty("booking_url_postfix");
    }
    public static String getBookingByIdUrl(String id) {
        return getBaseUrl() + getProperty("get_booking_url_postfix") + id;
    }
    public static String getCreateBookingUrl() {
        return getBaseUrl() + getProperty("create_booking_url_postfix");
    }

    public static String getUpdateBookingUrl(String id) {
        return getBaseUrl() + getProperty("update_booking_url_postfix") + id;
    }
    public static String getPartialUpdateBookingUrl(String id) {
        return getBaseUrl() + getProperty("partial_update_booking_url_postfix") + id;
    }
    public static String getDeleteBookingUrl(String id) {
        return getBaseUrl() + getProperty("delete_booking_url_postfix") + id;
    }
    public static String getProperty(String key) {
        return ResourceBundle.getBundle("restful-booker").getString(key);
    }
}
