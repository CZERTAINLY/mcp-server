package com.otilm.mcp.service;

public interface VaultService {

    String listVaultInstances(Integer itemsPerPage, Integer pageNumber);

    String listVaultProfiles(Integer itemsPerPage, Integer pageNumber);
}
