package com.otilm.mcp.service;

public interface VaultService {

    String listVaultInstances(String filters, Integer itemsPerPage, Integer pageNumber);

    String listVaultProfiles(String filters, Integer itemsPerPage, Integer pageNumber);
}
