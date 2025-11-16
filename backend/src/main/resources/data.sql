INSERT INTO role (role_name) VALUES ('public'),('analyst'),('admin');

INSERT INTO province (province_name) VALUES ('Province A'),('Province B');

INSERT INTO poverty_county (county_name, province_id, delisting_year) VALUES ('County 1',1,NULL),('County 2',1,2020),('County 3',2,NULL);

-- BCrypt hash for 'admin123' (may differ by bcrypt version)
INSERT INTO sys_user (username, password_hash, role_id, fullname, email) VALUES ('admin','$2a$10$u1bG0YqQ1pY7xqvH3lD1Ze9o7aWbYIh9CwS2e4u1aSht7b6m8VrmK',3,'Administrator','admin@example.com');

INSERT INTO economic_indicator (year, county_id, gdp, gdp_yoy, gdp_per_capita, rural_disposable_income, rural_income_yoy, fiscal_revenue, fiscal_revenue_yoy)
VALUES
(2019, 1, 100.0, 0.05, 20000, 7000, 0.03, 10.0, 0.02),
(2020, 1, 98.0, -0.02, 19800, 6800, -0.03, 9.5, -0.05),
(2021, 1, 102.0, 0.041, 20500, 7200, 0.058, 11.0, 0.157);