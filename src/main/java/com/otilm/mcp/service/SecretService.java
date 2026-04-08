package com.otilm.mcp.service;

public interface SecretService {

    String searchSecrets(String filters, Integer itemsPerPage, Integer pageNumber);

    String getSecret(String uuid);

    String getSecretVersions(String uuid);
}
