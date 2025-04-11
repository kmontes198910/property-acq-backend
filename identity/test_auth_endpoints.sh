#!/bin/bash

# Configuración por defecto
BASE_URL="http://localhost:8080/identity"
EMAIL="test@example.com"
PASSWORD="Test123!"
OTP="123456"
TIMEOUT=10
LOG_FILE=""
SILENT=false

# Colores para la salida
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m'

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [-u URL] [-e EMAIL] [-p PASSWORD] [-o OTP] [-t TIMEOUT] [-l LOG_FILE] [-s]"
    echo "Opciones:"
    echo "  -u URL      Base URL del servicio (default: $BASE_URL)"
    echo "  -e EMAIL    Email para las pruebas (default: $EMAIL)"
    echo "  -p PASS     Password para las pruebas (default: $PASSWORD)"
    echo "  -o OTP      OTP para las pruebas (default: $OTP)"
    echo "  -t TIMEOUT  Timeout en segundos para las peticiones (default: $TIMEOUT)"
    echo "  -l FILE     Archivo de log para guardar resultados"
    echo "  -s          Modo silencioso"
    echo "  -h          Muestra esta ayuda"
    exit 1
}

# Procesar argumentos
while getopts "u:e:p:o:t:l:sh" opt; do
    case $opt in
        u) BASE_URL="$OPTARG" ;;
        e) EMAIL="$OPTARG" ;;
        p) PASSWORD="$OPTARG" ;;
        o) OTP="$OPTARG" ;;
        t) TIMEOUT="$OPTARG" ;;
        l) LOG_FILE="$OPTARG" ;;
        s) SILENT=true ;;
        h) show_help ;;
        \?) echo "Opción inválida -$OPTARG" >&2; show_help ;;
    esac
done

# Función para separador
log_separator() {
    if [[ "$SILENT" != "true" ]]; then
        echo "---------------------------------------"
    fi
    
    if [[ -n "$LOG_FILE" ]]; then
        echo "---------------------------------------" >> "$LOG_FILE"
    fi
}

# Función para logging
log_message() {
    local message=$1
    local level=${2:-INFO}
    local timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    
    if [[ "$SILENT" != "true" ]]; then
        case $level in
            "ERROR") echo -e "${RED}$message${NC}" ;;
            "SUCCESS") echo -e "${GREEN}$message${NC}" ;;
            *) echo -e "${YELLOW}$message${NC}" ;;
        esac
    fi
    
    if [[ -n "$LOG_FILE" ]]; then
        echo "[$timestamp] $level: $message" >> "$LOG_FILE"
    fi
}

# Crear archivo de log si se especificó
if [[ -n "$LOG_FILE" ]]; then
    touch "$LOG_FILE" 2>/dev/null || {
        echo -e "${RED}No se puede crear el archivo de log: $LOG_FILE${NC}" >&2
        exit 1
    }
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] INFO: Iniciando pruebas de AuthController" > "$LOG_FILE"
fi

# Función para mostrar el resultado
show_result() {
    local status_code=$1
    local endpoint=$2
    local response=$3
    
    log_message "Respuesta: $response"
    
    if [[ $status_code -ge 200 && $status_code -lt 300 ]]; then
        log_message "✓ $endpoint exitoso (${status_code})" "SUCCESS"
    else
        log_message "✗ $endpoint falló (${status_code})" "ERROR"
    fi
    log_separator
}

# Función para hacer las peticiones HTTP
make_request() {
    local method=$1
    local endpoint=$2
    local data=$3
    local headers=$4

    local curl_cmd="curl -s --connect-timeout $TIMEOUT -X $method \"${BASE_URL}${endpoint}\""
    
    if [[ -n "$data" ]]; then
        curl_cmd="$curl_cmd -H \"Content-Type: application/json\" -d '$data'"
    fi
    
    if [[ -n "$headers" ]]; then
        curl_cmd="$curl_cmd $headers"
    fi

    local temp_file=$(mktemp)
    curl_cmd="$curl_cmd -o $temp_file -w \"%{http_code}\""
    
    local response=""
    local body=""
    
    # Ejecutar curl con manejo de errores
    if ! response=$(eval $curl_cmd 2>/dev/null); then
        log_message "Error de red al hacer la petición" "ERROR"
        rm -f "$temp_file"
        return 1
    fi
    
    body=$(cat "$temp_file")
    rm -f "$temp_file"
    
    show_result $response "$method $endpoint" "$body"
    echo "$body"
}

# Validar que el servicio esté corriendo
log_message "Verificando si el servicio está disponible..."
if ! curl -s -f --connect-timeout $TIMEOUT "$BASE_URL/app-version" > /dev/null; then
    log_message "Error: El servicio no está disponible en $BASE_URL" "ERROR"
    log_message "Asegúrate de que el servicio esté corriendo y la URL sea correcta." "ERROR"
    exit 1
fi
log_message "Servicio disponible." "SUCCESS"

# Inicio de las pruebas
log_message "Probando endpoints del AuthController..."
log_separator

# 1. Probar GET /app-version
log_message "1. Probando GET /app-version"
make_request "GET" "/app-version"

# 2. Probar POST /authenticate
log_message "2. Probando POST /authenticate"
auth_response=$(make_request "POST" "/authenticate" "{\"username\":\"${EMAIL}\",\"password\":\"${PASSWORD}\"}")

# Extraer tokens
if [[ $auth_response == *"token"* ]]; then
    TOKEN=$(echo $auth_response | grep -o '"token":"[^"]*' | cut -d'"' -f4)
    REFRESH_TOKEN=$(echo $auth_response | grep -o '"refreshToken":"[^"]*' | cut -d'"' -f4)
    if [[ -n $TOKEN ]]; then
        log_message "Token obtenido: ${TOKEN:0:15}..." "SUCCESS"
    fi
    if [[ -n $REFRESH_TOKEN ]]; then
        log_message "Refresh Token obtenido: ${REFRESH_TOKEN:0:15}..." "SUCCESS"
    fi
    log_separator
fi

# 3. Probar POST /forgot-password
log_message "3. Probando POST /forgot-password"
make_request "POST" "/forgot-password?email=${EMAIL}"

# 4. Probar POST /change-password
log_message "4. Probando POST /change-password"
make_request "POST" "/change-password" "{\"email\":\"${EMAIL}\",\"newPassword\":\"${PASSWORD}\",\"otp\":\"${OTP}\"}"

# 5. Probar GET /exist-by-email
log_message "5. Probando GET /exist-by-email"
make_request "GET" "/exist-by-email/${EMAIL}"

# 6. Probar POST /refresh-token
log_message "6. Probando POST /refresh-token"
# Usar el refresh token obtenido de la autenticación si existe
if [[ -n $REFRESH_TOKEN ]]; then
    TOKEN_TO_USE=$REFRESH_TOKEN
else
    TOKEN_TO_USE="some-refresh-token"
fi
make_request "POST" "/refresh-token" "{\"refreshToken\":\"${TOKEN_TO_USE}\"}"

log_message "Pruebas completadas." "SUCCESS"

# Mostrar resumen
log_message "Resumen de la ejecución:"
log_message "Base URL: $BASE_URL"
log_message "Email usado: $EMAIL"
log_message "Timestamp: $(date)"

# Notificar donde se guardó el log si aplica
if [[ -n "$LOG_FILE" ]]; then
    if [[ "$SILENT" != "true" ]]; then
        echo -e "${GREEN}Los resultados se han guardado en: $LOG_FILE${NC}"
    fi
fi

