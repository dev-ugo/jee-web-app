#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
export CATALINA_HOME="$PWD/Servers/apache-tomcat-9.0.123"
export CATALINA_BASE="$PWD/.runtime/tomcat"
export CATALINA_PID="$CATALINA_BASE/tomcat.pid"
source scripts/env.sh
build() {
  command -v javac >/dev/null || { echo 'JDK 17 ou superieur requis.' >&2; exit 1; }
  mkdir -p "$CATALINA_BASE"/{conf,logs,temp,webapps,work}
  cp "$CATALINA_HOME"/conf/* "$CATALINA_BASE/conf/"
  # Bind native launches to loopback; Docker publishes its own loopback port.
  sed -i 's/<Connector port="8080"/<Connector address="'"${TOMCAT_ADDRESS:-127.0.0.1}"'" port="8080"/' "$CATALINA_BASE/conf/server.xml"
  local app="$CATALINA_BASE/webapps/student-web"
  mkdir -p "$app/WEB-INF/classes" "$app/WEB-INF/lib"
  find "$app/WEB-INF/classes" -type f -name '*.class' -delete
  cp -R student-web/src/main/webapp/. "$app/"
  cp mysql-connector-java-5.1.7-bin.jar "$app/WEB-INF/lib/"
  # Compile the core without Servlet dependencies, then compile the web adapter against it.
  find student-core/src/main/java -name '*.java' > .runtime/core-sources.txt
  find student-web/src/main/java -name '*.java' > .runtime/web-sources.txt
  cat .runtime/core-sources.txt .runtime/web-sources.txt > .runtime/sources.txt
  javac --release 17 -encoding UTF-8 -d "$app/WEB-INF/classes" @.runtime/core-sources.txt
  javac --release 17 -encoding UTF-8 -cp "$app/WEB-INF/classes:$CATALINA_HOME/lib/servlet-api.jar" -d "$app/WEB-INF/classes" @.runtime/web-sources.txt
  local demo="$CATALINA_BASE/webapps/servlet-demo"
  mkdir -p "$demo/WEB-INF/classes"
  find "$demo/WEB-INF/classes" -type f -name '*.class' -delete
  cp -R servlet-demo/src/main/webapp/. "$demo/"
  find servlet-demo/src/main/java -name '*.java' > .runtime/demo-sources.txt
  javac --release 17 -encoding UTF-8 -cp "$CATALINA_HOME/lib/servlet-api.jar" -d "$demo/WEB-INF/classes" @.runtime/demo-sources.txt
}
case "${1:-start}" in
  build) build ;;
  start) load_database_env; build; echo 'Application : http://localhost:8080/student-web/'; exec bash "$CATALINA_HOME/bin/catalina.sh" run ;;
  stop) exec bash "$CATALINA_HOME/bin/catalina.sh" stop ;;
  *) echo 'Usage : bash scripts/app.sh [build|start|stop]' >&2; exit 1 ;;
esac
