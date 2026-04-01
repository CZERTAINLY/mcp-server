package com.otilm.mcp.service;

public interface KeyService {

    String searchKeys(Integer itemsPerPage, Integer pageNumber);

    String getKey(String uuid);
}
