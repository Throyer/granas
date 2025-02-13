# granas api

> This project requires `Docker`

## 🚨 Important, don't forget to grant execution permission to the scripts:
```
chmod -R +x .docker/scripts
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

### Running tests
- run tests
  ```bash
  .docker/scripts/mvn test
  ```

- run `integration` tests
  ```bash
  .docker/scripts/mvn verify -P integration-tests
  ```