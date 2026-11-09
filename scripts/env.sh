#!/usr/bin/env bash
# Read configuration as data, never as executable shell code.
load_database_env() {
  [[ -f .env ]] || return 0
  if [[ -L .env || ! -O .env ]]; then
    echo '.env doit etre un fichier ordinaire appartenant a votre utilisateur.' >&2
    return 1
  fi
  chmod 600 .env || return 1
  local line key value quote
  while IFS= read -r line || [[ -n "$line" ]]; do
    line="${line%$'\r'}"
    [[ "$line" =~ ^[[:space:]]*(#|$) ]] && continue
    if [[ ! "$line" =~ ^(DB_URL|DB_USER|DB_PASSWORD|DB_ROOT_PASSWORD)=(.*)$ ]]; then
      echo 'Ligne .env invalide : seules les affectations DB_* documentees sont acceptees.' >&2
      return 1
    fi
    key="${BASH_REMATCH[1]}"
    value="${BASH_REMATCH[2]}"
    quote="${value:0:1}"
    if [[ "$quote" == "'" || "$quote" == '"' ]]; then
      if [[ ${#value} -lt 2 || "${value: -1}" != "$quote" ]]; then
        echo 'Valeur .env : guillemets non fermes.' >&2
        return 1
      fi
      value="${value:1:${#value}-2}"
    fi
    # Existing environment takes precedence, as with Docker Compose.
    if [[ ! -v "$key" ]]; then
      printf -v "$key" '%s' "$value"
      export "$key"
    fi
  done < .env
}
