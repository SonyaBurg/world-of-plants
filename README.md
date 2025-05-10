# World Of Plants E-Shop

## How to run
1. Set up the PostgresSQL database by running `docker-compose.yaml`:
    ```bash
    docker compose up -d
    ```
2. Run IntelliJ run-configuration `WorldOfPlantsApplication`
    The application will run on `localhost:8080`

## Endpoints:
1. `/`: home page
2. `/plants`: page listing available plants
3. `/login`: login page
4. `/register`: registration page
5. `/account`: account details page
6. `/basket`: basket page
7. `/order`: orders page

## Admin endpoints:
1. `/admin/dashboard`: admin dashboard with statistics, graphs, and navigation
2. `/admin/plants`: plants management
3. `/admin/orders`: orders managements

To access admin endpoints, you need to be logged in as admin. Credentials:
```
username: admin
password: admin
```