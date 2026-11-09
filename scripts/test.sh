#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
if [[ -f .env ]]; then set -a; source .env; set +a; fi
bash scripts/app.sh build
mkdir -p .runtime/tests
classpath=".runtime/tomcat/webapps/student-web/WEB-INF/classes:mysql-connector-java-5.1.7-bin.jar"
javac --release 17 -encoding UTF-8 -cp "$classpath" -d .runtime/tests student-core/src/test/java/com/s4m/jee/studentmanagement/persistence/StudentDaoSmokeTest.java
java -cp ".runtime/tests:$classpath" com.s4m.jee.studentmanagement.persistence.StudentDaoSmokeTest
