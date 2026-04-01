package com.otilm.mcp.service;

public interface CertificateService {

    String getStatistics();

    String searchCertificates(Integer itemsPerPage, Integer pageNumber);

    String getCertificate(String uuid);

    String validateCertificate(String uuid);

    String getCertificateChain(String uuid);

    String getCertificateHistory(String uuid);
}
