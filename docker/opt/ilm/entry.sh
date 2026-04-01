#!/bin/sh

ilmHome="/opt/ilm"
source ${ilmHome}/static-functions

log "INFO" "Starting ILM MCP Server"
log "INFO" "ILM API URL: ${ILM_API_URL:-not set}"
log "INFO" "Auth Method: ${ILM_AUTH_METHOD:-not set}"

exec java \
    ${JAVA_OPTS} \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:+UseG1GC \
    -jar ${ilmHome}/app.jar \
    --spring.profiles.active=${SPRING_PROFILES_ACTIVE:-http}
