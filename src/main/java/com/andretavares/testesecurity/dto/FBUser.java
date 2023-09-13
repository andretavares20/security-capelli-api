package com.andretavares.testesecurity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.List;

@Data
public class FBUser {
    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("app_id")
        private String appId;

        @JsonProperty("type")
        private String type;

        @JsonProperty("application")
        private String application;

        @JsonProperty("data_access_expires_at")
        private long dataAccessExpiresAt;

        @JsonProperty("expires_at")
        private long expiresAt;

        @JsonProperty("is_valid")
        private boolean isValid;

        @JsonProperty("scopes")
        private List<String> scopes;

        @JsonProperty("user_id")
        private String userId;

        // Getters e Setters para todas as propriedades

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApplication() {
            return application;
        }

        public void setApplication(String application) {
            this.application = application;
        }

        public long getDataAccessExpiresAt() {
            return dataAccessExpiresAt;
        }

        public void setDataAccessExpiresAt(long dataAccessExpiresAt) {
            this.dataAccessExpiresAt = dataAccessExpiresAt;
        }

        public long getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public List<String> getScopes() {
            return scopes;
        }

        public void setScopes(List<String> scopes) {
            this.scopes = scopes;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}

