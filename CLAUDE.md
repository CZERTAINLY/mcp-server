# ILM MCP Server

## Project Overview

MCP (Model Context Protocol) server for the ILM platform (formerly CZERTAINLY). Exposes ILM certificate and key management operations as AI-friendly tools using Spring AI.

## Tech Stack

- Java 21
- Spring Boot 3.5.13
- Spring AI 1.1.4
- Maven
- CZERTAINLY Interfaces library (com.czertainly:interfaces)

## Build & Run

### Prerequisites

- Java 21+
- Maven 3.9+

### Build

```bash
mvn clean verify
```

### Run locally (STDIO mode)

```bash
java -jar target/mcp-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=stdio
```

### Run locally (HTTP mode)

```bash
java -jar target/mcp-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=http
```

### Run tests

```bash
mvn test
```

### Check code coverage

```bash
mvn verify
# Report at target/site/jacoco/index.html
```

## Configuration

### ILM API Connection

Set via environment variables or application.yml:

- `ILM_API_URL` - ILM API base URL (up to /api)
- `ILM_AUTH_METHOD` - Authentication method: `certificate`, `oauth-passthrough`, or `oauth-client-credentials`

### Authentication

**Client Certificate:**
- `ILM_SSL_BUNDLE` - SSL bundle name (default: `ilm-client`)
- Configure Spring SSL bundles in application.yml

**OAuth Pass-through (HTTP mode):**
- Client sends Bearer token, server forwards to ILM API

**OAuth Client Credentials:**
- `ILM_OAUTH_CLIENT_ID`, `ILM_OAUTH_CLIENT_SECRET`, `ILM_OAUTH_TOKEN_URL`
- `ILM_OAUTH_SCOPE`, `ILM_OAUTH_AUDIENCE` (optional)

## Architecture

```
MCP Tool (@Tool) → Service (formatting) → IlmApiClient (RestClient) → ILM API
```

- **Package**: `com.otilm.mcp`
- **Tools**: `com.otilm.mcp.tool` — MCP tool definitions
- **Services**: `com.otilm.mcp.service` — business logic + AI-friendly response formatting
- **Client**: `com.otilm.mcp.client` — ILM API HTTP client
- **Config**: `com.otilm.mcp.config` — Spring configuration
- **Security**: `com.otilm.mcp.security` — auth token handling

## Conventions

- Follow CZERTAINLY Java repository conventions
- Service interfaces with `*Impl` suffix for implementations
- WireMock for integration tests (no Mockito)
- 80%+ test coverage, <3% code duplication
- AI-friendly formatted text responses from all tools (not raw JSON)

## Docker

```bash
docker build -t ilm-mcp-server .
docker run -e ILM_API_URL=https://my-instance/api -e ILM_AUTH_METHOD=oauth-passthrough -p 8080:8080 ilm-mcp-server
```
