# Granas API

> This project requires `Docker`

## 🚨 Important, don't forget to grant execution permission to the scripts and create .env:
```
chmod -R +x .docker/scripts
```
```
cp .docker/.env.example .docker/.env
```
### Running project
- run development mode
  ```bash
  .docker/scripts/develop up -d --force-recreate --build
  ```

- run `production` mode
  ```bash
  .docker/scripts/build up -d --force-recreate --build
  ```

- delete all containers `🚨 this remove ALL running containers not only related to project`
  ```bash
  docker rm -f $(docker ps -a -q)
  ```

### Running tests
- run tests
  ```bash
  .docker/scripts/mvn test
  ```

- run `integration` tests
  ```bash
  .docker/scripts/mvn verify -P integration-tests
  ```
  
#### to run specific test using use the parameter `-Dtest=CLASS_NAME#METHOD_NAME`

- for example the integration test. authorization header:
  ```bash
  .docker/scripts/mvn test -Dtest=JWTIntegrationTest#should_not_accept_requests_with_token_expired -DfailIfNoTests=false -P integration-tests
  ```

### Migrations
- Java based migrations
  ```bash
  .docker/scripts/mvn migration:generate -Dname=my-migration-name
  ```

- SQL based migrations
  ```bash
  .docker/scripts/mvn migration:generate -Dname=my-migration-name -Dsql
  ```