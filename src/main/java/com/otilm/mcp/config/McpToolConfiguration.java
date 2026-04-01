package com.otilm.mcp.config;

import com.otilm.mcp.tool.CertificateTool;
import com.otilm.mcp.tool.InfrastructureTool;
import com.otilm.mcp.tool.KeyTool;
import com.otilm.mcp.tool.SecretTool;
import com.otilm.mcp.tool.VaultTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class McpToolConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(McpToolConfiguration.class);

    @Bean
    public ToolCallbackProvider toolCallbackProvider(CertificateTool certificateTool,
                                                     KeyTool keyTool,
                                                     InfrastructureTool infrastructureTool,
                                                     SecretTool secretTool,
                                                     VaultTool vaultTool,
                                                     PlatformInfo platformInfo) {
        List<Object> tools = new ArrayList<>(List.of(certificateTool, keyTool, infrastructureTool));

        if (platformInfo.isSecretsSupported()) {
            tools.add(secretTool);
            tools.add(vaultTool);
            logger.info("Secrets and vault tools enabled (platform version {})", platformInfo.getPlatformVersion());
        } else {
            logger.info("Secrets and vault tools disabled (platform version: {})", platformInfo.getPlatformVersion());
        }

        return MethodToolCallbackProvider.builder()
                .toolObjects(tools.toArray())
                .build();
    }
}
