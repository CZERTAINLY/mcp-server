package com.otilm.mcp.service;

public interface SecretService {

    String searchSecrets(String name, String type, String state, Integer itemsPerPage, Integer pageNumber);

    String getSecret(String uuid);

    String getSecretVersions(String uuid);
}
