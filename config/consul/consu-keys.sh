# Global

consul kv put config/application/employer-api/instance/id employer-api
consul kv put config/application/employer-api/context-path /employer/api
consul kv put config/application/employer-api/departments/endpoint/uri /v1/departments

# For employer only
consul kv put config/employer-api/spring/datasource/driver-class-name org.postgresql.Driver
consul kv put config/employer-api/spring/datasource/url jdbc:postgresql://postgres:5432/employer_api?currentSchema=employer
consul kv put config/employer-api/spring/jpa/hibernate/show-sql true
