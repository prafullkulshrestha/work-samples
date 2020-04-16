# Global

# For broker only
consul kv put config/employer-api/spring/datasource/driver-class-name org.postgresql.Driver
consul kv put config/employer-api/spring/datasource/url jdbc:postgresql://postgres:5432/employer_api?currentSchema=employer
consul kv put config/employer-api/spring/jpa/hibernate/show-sql true
