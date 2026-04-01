package com.otilm.mcp.tool;

import com.otilm.mcp.service.VaultService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class VaultTool {

    private final VaultService vaultService;

    public VaultTool(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @Tool(name = "list_vault_instances", description = "List vault instances configured in the ILM platform. Returns vault name, UUID, description, and connector for each instance. Vault instances represent secret storage backends (e.g., HashiCorp Vault, AWS Secrets Manager). Available only when connected to ILM platform 2.17 or later.")
    public String listVaultInstances(
            @ToolParam(description = "Number of items per page (default: 10)", required = false) Integer itemsPerPage,
            @ToolParam(description = "Page number for pagination (default: 1)", required = false) Integer pageNumber) {
        return vaultService.listVaultInstances(itemsPerPage, pageNumber);
    }

    @Tool(name = "list_vault_profiles", description = "List vault profiles configured in the ILM platform. Returns profile name, UUID, description, vault instance, and enabled status. Vault profiles define how secrets are stored and managed in vault instances. Available only when connected to ILM platform 2.17 or later.")
    public String listVaultProfiles(
            @ToolParam(description = "Number of items per page (default: 10)", required = false) Integer itemsPerPage,
            @ToolParam(description = "Page number for pagination (default: 1)", required = false) Integer pageNumber) {
        return vaultService.listVaultProfiles(itemsPerPage, pageNumber);
    }
}
