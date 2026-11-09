# Gestion des élèves — Java EE

Application Java EE permettant d’ajouter, de rechercher et de lister des élèves, accompagnée d’une démonstration indépendante de Servlet.

Documentation de l’architecture : [ARCHITECTURE.md](ARCHITECTURE.md).

## Prérequis

Installer et démarrer **Docker avec Docker Compose** (par exemple Docker Desktop).
Les ports **8080** et **8889** doivent être disponibles. Docker fournit Java, Tomcat et la base de données.

## Démarrage

Depuis le dossier du projet, copier `.env.example` vers `.env` et renseigner les deux mots de passe avec vos propres valeurs locales. Le fichier `.env` est exclu de Git.

Puis exécuter :

```bash
docker compose up -d --build --wait
```

Le premier démarrage télécharge les images, compile les applications et initialise automatiquement la base de données.
Les mots de passe sont lus depuis votre fichier privé `.env`.

Ouvrir **http://localhost:8080/student-web/**.
La démonstration de Servlet est accessible sur http://localhost:8080/servlet-demo/.

## Arrêt

```bash
docker compose down
```

Les données sont conservées. Utiliser la même commande de démarrage pour relancer les applications.
En cas de problème, consulter les journaux avec `docker compose logs`.

## Organisation des sources

- `student-core/src/main/java` : objets représentant les élèves, accès aux données JDBC, résultats et codes d’état.
- `student-web/src/main/java` : `StudentController` et `StudentRequestHandler`.
- `student-web/src/main/webapp` : pages JSP et configuration web.
- `servlet-demo/src/main` : démonstration `HelloServlet` et son formulaire.
- `database/schema.sql` : initialisation de la base de données.
- `scripts` : commandes de compilation, de démarrage, d’arrêt et de tests d’intégration.
- `student-core/src/test/java/.../StudentDaoSmokeTest.java` : vérifications d’intégration avec la base, utilisant un numéro d’élève unique supprimé à la fin du test.

Les modules ont une dépendance à sens unique : `student-web` → `student-core`.
La démonstration de Servlet est indépendante. La compilation construit d’abord le module principal sans les bibliothèques Servlet, puis le module web avec le module principal et l’API Servlet.

Dans `com.s4m.jee.studentmanagement`, chaque package a un rôle précis :

- `domain` : l’objet `Student` représentant un élève.
- `persistence` : la connexion JDBC et `StudentDao`.
- `result` : les résultats des requêtes et les codes `OperationStatus`.
- `web.controller` et `web.handler` : le traitement des requêtes HTTP et des formulaires, uniquement dans `student-web`.

Le code applicatif se trouve dans `src/main/java`, les tests dans `src/test/java` et les ressources web dans `src/main/webapp`.
Les chemins de base des applications correspondent aux noms des modules : `/student-web` et `/servlet-demo`.
La configuration Eclipse, Docker et les scripts locaux utilisent ces mêmes chemins.

Les routes de gestion des élèves sont relatives à `/student-web` :

| Route | GET | POST |
| --- | --- | --- |
| `/` | Accueil | — |
| `/students` | Formulaire de demande de liste | Afficher tous les élèves |
| `/students/new` | Formulaire d’ajout | Ajouter un élève |
| `/students/search` | Formulaire de recherche | Rechercher par numéro d’élève |
| `/students/delete` | Page d’erreur | Action non implémentée |
| `/students/update` | Page d’erreur | Action non implémentée |

Les pages JSP sont internes, dans `WEB-INF/views`. Les formulaires et la navigation utilisent des routes relatives au chemin de base de l’application.
Le formulaire de démonstration envoie ses données à `/servlet-demo/hello`.
Les anciens chemins d’application, URL de contrôleur, paramètres `idaction` et URL publiques des JSP ont été remplacés : mettre à jour les favoris avec les routes ci-dessus.

Les classes et méthodes Java portent des noms anglais. Par exemple, `StudentDao.findByNumber` renvoie un `StudentResult`, tandis que `findAll` renvoie un `StudentListResult`.
Les codes d’état sont `1` (succès), `0` (aucun résultat), `-1` (erreur de base de données) et `-2` (erreur générale ou clé en double).

Les champs des formulaires, le schéma de la base et les messages affichés sont conservés.
Les actions web de suppression et de modification renvoient encore la page d’erreur ; les opérations correspondantes restent disponibles dans le DAO.

## Développement local et vérifications

Avec le JDK 17 ou une version ultérieure, compiler les deux applications à l’aide des bibliothèques Tomcat fournies :

```bash
bash scripts/app.sh build
```

Pour un lancement local, configurer `DB_URL`, `DB_USER` et `DB_PASSWORD` dans `.env` à partir de `.env.example`, puis exécuter `bash scripts/app.sh start`.
Arrêter avec `bash scripts/app.sh stop`.

Lancer les tests d’intégration du DAO sur la base configurée :

```bash
bash scripts/test.sh
```

Les anciens tests JUnit dans `student-core/src/test/java/com/s4m/jee/studentmanagement/persistence/StudentDaoTest.java` nécessitent JUnit 5 et contiennent des tests non implémentés. Ils sont exclus de la compilation de l’application.

Après compilation, générer la documentation de l’API, rédigée en anglais :

```bash
javadoc -quiet -Xdoclint:all -d .runtime/javadoc \
  -classpath Servers/apache-tomcat-9.0.123/lib/servlet-api.jar \
  @.runtime/sources.txt @.runtime/demo-sources.txt
```

Ouvrir `.runtime/javadoc/index.html` pour la consulter.
Les fichiers de compilation et la documentation générée sont stockés dans le dossier `.runtime`, exclu de Git.

## Protections pour la demonstration locale

Les ports Docker sont publies uniquement sur `127.0.0.1`. Le lancement Java direct
ecoute egalement sur `127.0.0.1`. L'application reste sans compte utilisateur :
elle est prevue pour une demonstration locale avec des donnees fictives.

Les formulaires utilisent un jeton de session contre les soumissions intersites.
Les messages affiches sont echappes, les reponses ne sont pas mises en cache et
le cookie de session utilise HttpOnly et SameSite=Strict. HTTP reste utilisable
sur localhost, sans configuration de certificat.

Les scripts lisent `.env` comme des donnees uniquement au lancement et pour les
tests JDBC, jamais pendant la compilation. Utiliser une affectation par ligne
avec les quatre cles de `.env.example`, sans `export`, substitution de variable
ni commande shell ; les valeurs peuvent etre entierement entre guillemets.
Les variables deja exportees dans le terminal sont prioritaires. Les scripts
restreignent les droits du fichier a son proprietaire (`chmod 600`).

Le pilote MySQL 5.1.7 est conserve pour ce rendu local. Son remplacement,
l'authentification, HTTPS et la pagination restent hors du perimetre de cette
demonstration. L'application ne doit pas etre exposee sur un reseau partage.
