## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/arndey/ubiquitous-adventure.git
   cd ubiquitous-adventure
   ```

2. Build the project:
   ```
   ./gradlew build
   ```

3. Run the application:
   ```
   ./gradlew run
   ```

The server will start on `http://localhost:8080`.

## API Endpoints

### Create Account

- **URL**: `/api/account/create`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "initBalance": 1000.00
  }
  ```
- **Response**: Account ID (UUID)

Example:
```bash
curl -X POST http://localhost:8080/api/account/create \
     -H "Content-Type: application/json" \
     -d '{"initBalance": 1000.00}'
```

### Get Account

- **URL**: `/api/account/{id}`
- **Method**: `GET`
- **Response**: Account details

Example:
```bash
curl http://localhost:8080/api/account/550e8400-e29b-41d4-a716-446655440000
```

### Transfer funds

- **URL**: `/api/account/transaction/transfer`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "from": "550e8400-e29b-41d4-a716-446655440000",
    "to": "550e8400-e29b-41d4-a716-446655440001",
    "amount": 100.00
  }
  ```
- **Response**: HTTP 200 OK on success

Example:
```bash
curl -X POST http://localhost:8080/api/account/transaction/transfer \
     -H "Content-Type: application/json" \
     -d '{"from": "550e8400-e29b-41d4-a716-446655440000", "to": "550e8400-e29b-41d4-a716-446655440001", "amount": 100.00}'
```

## Running Tests

To run the tests, execute:

```
./gradlew test
```

## Built With

- [Kotlin](https://kotlinlang.org/) - Programming language
- [Ktor](https://ktor.io/) - Web framework
- [Koin](https://insert-koin.io/) - Dependency injection framework
- [Gradle](https://gradle.org/) - Build tool
