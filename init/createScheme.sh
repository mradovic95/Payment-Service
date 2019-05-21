set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER webshop_payment PASSWORD 'password';
    CREATE DATABASE webshop_payment_db;
    GRANT ALL PRIVILEGES ON DATABASE webshop_payment_db TO webshop_payment;
EOSQL