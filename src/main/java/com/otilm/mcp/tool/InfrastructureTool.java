package com.otilm.mcp.tool;

import com.otilm.mcp.service.InfrastructureService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureTool {

    private final InfrastructureService infrastructureService;

    public InfrastructureTool(InfrastructureService infrastructureService) {
        this.infrastructureService = infrastructureService;
    }

    @Tool(name = "list_authorities", description = "List all authority instances configured in the ILM platform. Returns authority name, UUID, status, connector, and kind for each instance. Use this to see which certificate authorities are available and their current status.")
    public String listAuthorities() {
        return infrastructureService.listAuthorities();
    }

    @Tool(name = "list_ra_profiles", description = "List all RA (Registration Authority) profiles configured in the ILM platform. Returns profile name, UUID, enabled status, description, authority instance, and enabled protocols. Use this to see which RA profiles are available for certificate enrollment and management.")
    public String listRaProfiles() {
        return infrastructureService.listRaProfiles();
    }

    @Tool(name = "list_connectors", description = "List all connectors configured in the ILM platform. Returns connector name, UUID, status, URL, auth type, and function groups. Use this to see which connectors are available and their connection status.")
    public String listConnectors() {
        return infrastructureService.listConnectors();
    }

    @Tool(name = "list_groups", description = "List all groups configured in the ILM platform. Returns group name, UUID, description, and email. Use this to see available groups for organizing certificates and keys.")
    public String listGroups() {
        return infrastructureService.listGroups();
    }

    @Tool(name = "list_entities", description = "List all entity instances configured in the ILM platform. Returns entity name, UUID, status, connector, and kind. Use this to see which entities are registered for certificate management operations.")
    public String listEntities() {
        return infrastructureService.listEntities();
    }

    @Tool(name = "list_credentials", description = "List all credentials configured in the ILM platform. Returns credential name, UUID, enabled status, kind, and connector. Use this to see which credentials are available for authentication with external systems.")
    public String listCredentials() {
        return infrastructureService.listCredentials();
    }

    @Tool(name = "list_token_instances", description = "List all cryptographic token instances configured in the ILM platform. Returns token name, UUID, status, connector, kind, and number of token profiles. Use this to see which cryptographic tokens (e.g., HSMs, software tokens) are available for key management.")
    public String listTokenInstances() {
        return infrastructureService.listTokenInstances();
    }

    @Tool(name = "list_discoveries", description = "List all certificate discovery tasks in the ILM platform. Returns discovery name, UUID, status, kind, connector, start/end times, and total certificates discovered. Use this to see past and ongoing certificate discovery operations and their results.")
    public String listDiscoveries() {
        return infrastructureService.listDiscoveries();
    }
}
