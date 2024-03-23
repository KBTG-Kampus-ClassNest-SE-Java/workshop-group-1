-- Smartphones
INSERT INTO cart (username, sku, quantity) VALUES ('GeekChic','MOBILE-APPLE-IPHONE-12-PRO', 1) ON CONFLICT DO NOTHING;

INSERT INTO cart (username, sku, quantity) VALUES ('GeekChic','BEV-COCA-COLA', 1) ON CONFLICT DO NOTHING;