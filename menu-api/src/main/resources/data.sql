-- Users table
INSERT INTO users (id, username, password, first, last, phone, email, image_url, pan, expiry_month, expiry_year, roles) VALUES
    (1, 'admin', '$2a$12$sPUi2kLGlu.YcCV9cfnE.uzJiPOR/G1sRHZcwV3agV9ScZReaY3VO', 'Administrator', 'User', '(555) 943-2230', 'admin@daam.com', 'https://minimaltoolkit.com/images/randomdata/female/70.jpg', '', 6, 2025, 'ROLE_ADMIN');
INSERT INTO users (id, username, password, first, last, phone, email, image_url, pan, expiry_month, expiry_year, roles) VALUES
    (2, 'cmac', '$2a$12$sPUi2kLGlu.YcCV9cfnE.uzJiPOR/G1sRHZcwV3agV9ScZReaY3VO', 'Caitlin', 'McIntyre', '(555) 442-2093', 'cmac@daam.com', 'https://minimaltoolkit.com/images/randomdata/female/71.jpg', '4332-1234-1234-1234', 6, 2025, 'ROLE_ADMIN');
INSERT INTO users (id, username, password, first, last, phone, email, image_url, pan, expiry_month, expiry_year, roles) VALUES
    (3, 'me', '$2a$12$sPUi2kLGlu.YcCV9cfnE.uzJiPOR/G1sRHZcwV3agV9ScZReaY3VO', 'Test', 'User', '(555) 853-1039', 'testUser@daam.com', 'https://minimaltoolkit.com/images/randomdata/female/7.jpg', '4332-1234-1234-1234', 6, 2025, 'ROLE_USER');
INSERT INTO users (id, username, password, first, last, phone, email, image_url, roles) VALUES
    (4, 'server1', '$2a$12$sPUi2kLGlu.YcCV9cfnE.uzJiPOR/G1sRHZcwV3agV9ScZReaY3VO', 'Jo', 'Server', '(555) 443-4567', 'server1@daam.com', 'https://minimaltoolkit.com/images/randomdata/female/9.jpg', 'ROLE_SERVER');
INSERT INTO users (id, username, password, first, last, phone, email, image_url, roles) VALUES
    (5, 'server2', '$2a$12$sPUi2kLGlu.YcCV9cfnE.uzJiPOR/G1sRHZcwV3agV9ScZReaY3VO', 'Lee', 'Server', '(555) 123-4954', 'server2@daam.com', 'https://minimaltoolkit.com/images/randomdata/male/9.jpg', 'ROLE_SERVER');

-- Menu items table (fixed boolean values and quotes)
INSERT INTO menuitems (id, name, description, category, price, imageurl, available) VALUES
    (1, 'Bison Burger', 'Packed with protein and a touch of sweetness, it''s topped with your choice of cheese and classic burger fixings.', 'entrees', 11.54, '/images/food/burger_1.jpg', true);
INSERT INTO menuitems (id, name, description, category, price, imageurl, available) VALUES
    (2, 'Southwest Burger', 'Inspired by the spices of Tex-Mex, this one will light you up! Juicy patty seasoned with chili, cumin, and coriander.', 'entrees', 10.70, '/images/food/burger_2.jpg', true);
INSERT INTO menuitems (id, name, description, category, price, imageurl, available) VALUES
    (3, 'Philly Burger', 'Philly cheesesteak flavor on a juicy burger! Shaved ribeye joins melty provolone, grilled onions, and peppers.', 'entrees', 15.54, '/images/food/burger_3.jpg', true);
INSERT INTO menuitems (id, name, description, category, price, imageurl, available) VALUES
    (4, 'Frisco Burger', 'Craving a classic with a twist? Our Frisco Burger features a juicy patty nestled between toasted sourdough bread.', 'entrees', 13.50, '/images/food/burger_4.jpg', true);
INSERT INTO menuitems (id, name, description, category, price, imageurl, available) VALUES
    (5, 'Chicago Burger', 'Chicago classic alert! Our Chicago Burger piles a juicy griddled patty high with Vienna beef hot dog relish.', 'entrees', 15.00, '/images/food/burger_5.jpg', true);

-- Orders table (fixed date format)
INSERT INTO orders (id, userid, ordertime, pickuptime, area, location, tax, tip, pan, expiry_month, expiry_year, status) VALUES
    (1001, 3, '2024-08-01T11:42:25', '2024-08-01T11:51:47', 'Theater 1', 'Table 37', 5.33, 12.93, '4026664388908977', 9, 2028, 'completed');
INSERT INTO orders (id, userid, area, location, tax, tip, pan, expiry_month, expiry_year, status) VALUES
    (1002, 3, 'Theater 1', 'Table 32', 1.01, 2.46, '5182958648591491', 5, 2024, 'problem');
INSERT INTO orders (id, userid, area, location, tax, tip, pan, expiry_month, expiry_year, status) VALUES
    (1003, 3, 'Theater 1', 'Table 33', 2.17, 5.26, '201447805801057', 9, 2026, 'readyForGuest');

-- Items table
INSERT INTO items (id, orderid, itemid, price, firstname, notes) VALUES
    (1, 1001, 1, 5.10, 'Nora', '');
INSERT INTO items (id, orderid, itemid, price, firstname, notes) VALUES
    (2, 1001, 2, 10.74, 'Carlos', 'Special instructions for Carlos');
INSERT INTO items (id, orderid, itemid, price, firstname, notes) VALUES
    (3, 1002, 3, 12.29, 'Ethel', 'No onions please');

-- Films table
INSERT INTO films (id, title, homepage, overview, posterpath, runtime, tagline, popularity, imdbid, voteaverage, votecount) VALUES
    (1, 'Chunnel', 'http://chunnelmovie.com', 'Illuminating the darkest depths of international intrigue and personal sacrifice.', '/images/posters/1.jpg', 0, 'There''s a war 100 meters below the English Channel', 7.1, 'tt0137523', 6.2, 52);
INSERT INTO films (id, title, homepage, overview, posterpath, runtime, tagline, popularity, imdbid, voteaverage, votecount) VALUES
    (2, 'Prognosis Negative', 'http://prognosisnegative.com', 'In a world on the brink of medical revolution, Prognosis Negative delves into the moral complexities.', '/images/posters/2.jpg', 0, 'How far will a man go to find a cure?', 8.3, 'tt0137523', 9.3, 822);
