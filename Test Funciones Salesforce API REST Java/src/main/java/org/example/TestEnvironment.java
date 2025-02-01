package org.example;

import java.util.ResourceBundle;
import java.util.Set;

public class TestEnvironment {

    private static final String MISSING_PROPERTY_ERR = "La propiedad '%s' no esta definida en el archivo env.properties";
    private final String endpoint;
    private final String username;
    private final String password;
    private final String client_id;
    private final String client_secret;

    public TestEnvironment() {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
        Set<String> keys = resourceBundle.keySet();
        String urlKey = getEndpointKey();
        this.endpoint = keys.contains(urlKey) ? resourceBundle.getString(urlKey) : "";
        this.username = keys.contains(getUsernameKey()) ? resourceBundle.getString(getUsernameKey()) : "";
        this.password = keys.contains(getPasswordKey()) ? resourceBundle.getString(getPasswordKey()) : "";
        this.client_id = keys.contains(getClientIdKey()) ? resourceBundle.getString(getClientIdKey()) : "";
        this.client_secret = keys.contains(getClientSecretKey()) ? resourceBundle.getString(getClientSecretKey()) : "";

    }

    private String getEndpointKey() {
        return "endpoint";
    }

    public String getEndpoint() {
        if (endpoint.isEmpty()) {
            throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getEndpointKey()));
        }
        return endpoint;
    }

    private String getUsernameKey() {
        return "username";
    }

    public String getUsername() {
        if (username.isEmpty()) {
            throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getUsernameKey()));
        }
        return username;
    }

    private String getPasswordKey() {
        return "password";
    }

    public String getPassword() {
        if (password.isEmpty()) {
            throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getPasswordKey()));
        }
        return password;
    }

    private String getClientIdKey() {
        return "client_id";
    }

    public String getClientId() {
        if (client_id.isEmpty()) {
            throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getClientIdKey()));
        }
        return client_id;
    }

    private String getClientSecretKey() {
        return "client_secret";
    }

    public String getClientSecret() {
        if (client_secret.isEmpty()) {
            throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getClientSecretKey()));
        }
        return client_secret;
    }

}

