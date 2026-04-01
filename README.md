# ILM MCP Server

[Model Context Protocol](https://modelcontextprotocol.io/) (MCP) server for the [ILM](https://www.czertainly.com/) platform. Exposes certificate and key management operations as AI-friendly tools, enabling LLMs and AI agents to interact with ILM through natural language.

Built with [Spring AI MCP](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html) and Java 21.

> **Early Access** - This project is in active development and considered experimental. While fully functional, APIs and tool definitions may change between releases. We encourage you to try it out, explore what's possible, and [open an issue](../../issues) or submit a pull request if you find ways to improve it. Your feedback and contributions help shape the future of AI-driven certificate management.

## Features

- **21 read-only tools** for certificates, cryptographic keys, infrastructure, secrets, and vaults
- **Two transport modes**: HTTP (Streamable HTTP) and STDIO
- **Three authentication methods**: client certificate, OAuth pass-through, OAuth client credentials
- **AI-optimized responses**: formatted text output designed for LLM consumption
- **Lightweight Docker image**: multi-stage build with custom JRE (jlink)

## Available Tools

### Certificates

| Tool                      | Description                                                              |
|---------------------------|--------------------------------------------------------------------------|
| `get_statistics`          | Platform dashboard summary with certificate counts and breakdowns        |
| `search_certificates`     | Search certificates with filtering by CN, status, expiry, and pagination |
| `get_certificate`         | Detailed certificate information by UUID                                 |
| `validate_certificate`    | Validate a certificate and check results                                 |
| `get_certificate_chain`   | Full trust chain inspection                                              |
| `get_certificate_history` | Chronological event history for a certificate                            |

### Cryptographic Keys

| Tool          | Description                                                |
|---------------|------------------------------------------------------------|
| `search_keys` | Search keys with algorithm/status filtering and pagination |
| `get_key`     | Detailed key information by UUID                           |

### Infrastructure

| Tool                   | Description                                           |
|------------------------|-------------------------------------------------------|
| `list_authorities`     | Certificate authority instances                       |
| `list_ra_profiles`     | Registration Authority profiles                       |
| `list_connectors`      | Connector instances and their status                  |
| `list_groups`          | Groups for organizing certificates and keys           |
| `list_entities`        | Entity instances                                      |
| `list_credentials`     | Authentication credentials                            |
| `list_token_instances` | Cryptographic token instances (HSMs, software tokens) |
| `list_discoveries`     | Certificate discovery tasks and results               |

### Secrets (ILM platform 2.17+)

| Tool                  | Description                              |
|-----------------------|------------------------------------------|
| `search_secrets`      | Search secrets with filtering            |
| `get_secret`          | Detailed secret information by UUID      |
| `get_secret_versions` | Secret version history                   |

### Vaults (ILM platform 2.17+)

| Tool                   | Description                              |
|------------------------|------------------------------------------|
| `list_vault_instances` | Vault backend instances                  |
| `list_vault_profiles`  | Vault profiles                           |

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.9+
- Running ILM platform instance

### Build

```bash
mvn clean verify
```

### Run in STDIO Mode

STDIO mode is used for direct integration with MCP clients like Claude Desktop or Claude Code.

```bash
java -jar target/mcp-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=stdio
```

### Run in HTTP Mode

HTTP mode exposes a Streamable HTTP endpoint for network-based MCP clients.

```bash
java -jar target/mcp-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=http
```

The server starts on port 8080 by default.

## Configuration

### Environment Variables

#### ILM API Connection

| Variable          | Description                     | Default                 |
|-------------------|---------------------------------|-------------------------|
| `ILM_API_URL`     | ILM API base URL (up to `/api`) | `https://localhost/api` |
| `ILM_AUTH_METHOD` | Authentication method           | `certificate`           |

#### Authentication

**Client Certificate** (`ILM_AUTH_METHOD=certificate`):

| Variable         | Description            | Default      |
|------------------|------------------------|--------------|
| `ILM_SSL_BUNDLE` | Spring SSL bundle name | `ilm-client` |

Configure the SSL bundle in `application.yml` or via Spring Boot SSL bundle properties.

**OAuth Pass-through** (`ILM_AUTH_METHOD=oauth-passthrough`):

No additional configuration needed. The server forwards the Bearer token from incoming HTTP requests to the ILM API. Only works in HTTP mode.

**OAuth Client Credentials** (`ILM_AUTH_METHOD=oauth-client-credentials`):

| Variable                  | Description         | Required |
|---------------------------|---------------------|----------|
| `ILM_OAUTH_CLIENT_ID`     | OAuth client ID     | Yes      |
| `ILM_OAUTH_CLIENT_SECRET` | OAuth client secret | Yes      |
| `ILM_OAUTH_TOKEN_URL`     | Token endpoint URL  | Yes      |
| `ILM_OAUTH_SCOPE`         | OAuth scope         | No       |
| `ILM_OAUTH_AUDIENCE`      | OAuth audience      | No       |

#### Server

| Variable    | Description                       | Default |
|-------------|-----------------------------------|---------|
| `PORT`      | HTTP server port (HTTP mode only) | `8080`  |
| `JAVA_OPTS` | JVM options                       | (empty) |

## Docker

### Build

```bash
docker build -t ilm-mcp-server .
```

### Run

```bash
docker run \
  -e ILM_API_URL=https://my-instance.example.com/api \
  -e ILM_AUTH_METHOD=oauth-client-credentials \
  -e ILM_OAUTH_CLIENT_ID=my-client \
  -e ILM_OAUTH_CLIENT_SECRET=my-secret \
  -e ILM_OAUTH_TOKEN_URL=https://auth.example.com/token \
  -p 8080:8080 \
  ilm-mcp-server
```

The Docker image runs in HTTP mode by default.

## MCP Client Configuration

### Claude Desktop

Add to your `claude_desktop_config.json`:

```json
{
  "mcpServers": {
    "ilm": {
      "command": "java",
      "args": [
        "-jar",
        "/path/to/mcp-server-0.1.0-SNAPSHOT.jar",
        "--spring.profiles.active=stdio"
      ],
      "env": {
        "ILM_API_URL": "https://my-instance.example.com/api",
        "ILM_AUTH_METHOD": "certificate"
      }
    }
  }
}
```

### Claude Code

Add to your `.mcp.json`:

```json
{
  "mcpServers": {
    "ilm": {
      "command": "java",
      "args": [
        "-jar",
        "/path/to/mcp-server-0.1.0-SNAPSHOT.jar",
        "--spring.profiles.active=stdio"
      ],
      "env": {
        "ILM_API_URL": "https://my-instance.example.com/api",
        "ILM_AUTH_METHOD": "certificate"
      }
    }
  }
}
```

## Architecture

```
MCP Client (LLM) ──MCP──▶ MCP Tool (@Tool) ──▶ Service (formatting) ──▶ IlmApiClient (RestClient) ──▶ ILM API
```

- **Tools** (`com.otilm.mcp.tool`) — MCP tool definitions with `@Tool` annotations
- **Services** (`com.otilm.mcp.service`) — business logic and AI-friendly response formatting
- **Client** (`com.otilm.mcp.client`) — ILM API HTTP client using Spring `RestClient`
- **Config** (`com.otilm.mcp.config`) — Spring configuration and properties
- **Security** (`com.otilm.mcp.security`) — authentication token handling

## Development

### Run Tests

```bash
mvn test
```

Integration tests use [WireMock](https://wiremock.org/) to stub ILM API responses.

### Code Coverage

```bash
mvn verify
# Report at target/site/jacoco/index.html
```

### Tech Stack

- Java 21
- Spring Boot 3.5.13
- Spring AI 1.1.4
- CZERTAINLY Interfaces 2.17.0
- WireMock (testing)
- JaCoCo (coverage)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
