Poverty Dashboard - Full MVP

Structure:
- backend: Spring Boot 3 application (Maven)
- frontend: Vue 3 + Vite

Instructions:
1. Create MySQL database `poverty_db` or use docker-compose which will create it.
2. Update backend/src/main/resources/application.yml if you run backend locally.
3. Build backend: mvn -f backend clean package
4. Run backend: java -jar backend/target/poverty-dashboard-backend-0.0.1-SNAPSHOT.jar
5. Frontend: cd frontend && npm install && npm run dev
6. Or use docker-compose up --build

Default admin credentials: admin / admin123 (created in data.sql)