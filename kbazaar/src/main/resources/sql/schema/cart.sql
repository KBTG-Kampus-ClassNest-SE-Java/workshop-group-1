DROP TABLE IF EXISTS cart;

CREATE TABLE IF NOT EXISTS cart (
	id SERIAL PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	sku VARCHAR(255) NOT NULL,
	quantity INT NOT NULL
);