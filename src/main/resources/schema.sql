DROP TABLE IF EXISTS app_game;
DROP TABLE IF EXISTS app_food;
DROP TABLE IF EXISTS app_accessory;
DROP TABLE IF EXISTS app_pet;
DROP TABLE IF EXISTS app_shop;
DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    weight DOUBLE,
    rol VARCHAR(50),
    diamonds INT DEFAULT 10,
    training_time DOUBLE,
    register_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE app_shop(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE app_pet(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    type VARCHAR(255) NOT NULL,
    weight DOUBLE,
    strength INT DEFAULT 10,
    happiness INT DEFAULT 50,
    health INT DEFAULT 90,
    hunger INT DEFAULT 0,
    exp INT DEFAULT 0,
    victories INT DEFAULT 0,
    defeats INT DEFAULT 0,
    user_id INT UNIQUE,
    CONSTRAINT fk_pet_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);

CREATE TABLE app_food(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    calories DOUBLE,
    healthChange INT NOT NULL DEFAULT 0,
    happinessChange INT NOT NULL DEFAULT 0,
    hungerChange INT NOT NULL DEFAULT 0,
    weightChange DOUBLE NOT NULL DEFAULT 0,
    price INT NOT NULL DEFAULT 0,
    description VARCHAR(500) NOT NULL,
    food_type VARCHAR(20) NOT NULL,
    app_shop_id INT,
    CONSTRAINT fk_food_app_shop FOREIGN KEY (app_shop_id) REFERENCES app_shop(id)
);

CREATE TABLE app_accessory(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    happinessChange INT NOT NULL DEFAULT 0,
    price INT NOT NULL DEFAULT 0,
    description VARCHAR(500) NOT NULL,
    app_shop_id INT,
    CONSTRAINT fk_accessory_app_shop FOREIGN KEY (app_shop_id) REFERENCES app_shop(id)
);

CREATE TABLE app_game(
    id INT AUTO_INCREMENT PRIMARY KEY,
    opponent_pet_id INT,
    challenger_pet_id INT,
    game_result VARCHAR(50),
    coins_awarded INT,
    CONSTRAINT fk_game_opponent_pet FOREIGN KEY (opponent_pet_id) REFERENCES app_pet(id),
    CONSTRAINT fk_game_challenger_pet FOREIGN KEY (challenger_pet_id) REFERENCES app_pet(id)
);