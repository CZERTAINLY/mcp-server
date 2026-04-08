package com.otilm.mcp.service;

public interface KeyService {

    String searchKeys(String filters, Integer itemsPerPage, Integer pageNumber);

    String getKey(String uuid);
}
