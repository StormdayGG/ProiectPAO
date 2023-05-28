DROP TABLE IF EXISTS `orderedProducts`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `shoppingCarts`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `companies`;
DROP TABLE IF EXISTS `admins`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(45) NOT NULL,
    `last_name` varchar(45) NOT NULL,
    `email` varchar(100) NOT NULL,
    `password` varchar(50) NOT NULL,
    `phone_number` varchar(20),
    `birthday` date NOT NULL,
    PRIMARY KEY (`id`)
);
ALTER TABLE `users` ADD UNIQUE INDEX `UNIQUE` (`first_name` ASC, `last_name` ASC) VISIBLE;

INSERT INTO `users`(`first_name`, `last_name`, `email`, `password`, `phone_number`, `birthday`) VALUES ('admin', '1', 'admin1@email.com', 'admin', '0123456789', STR_TO_DATE('1 January 2000', '%d %M %Y'));

CREATE TABLE `customers` (
	`user_id` int NOT NULL UNIQUE,
    `shipping_address` varchar(100) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users(id)
);

CREATE TABLE `admins` (
	`user_id` int NOT NULL UNIQUE,
    `salary` int NOT NULL,
    `employment_date` DATE NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES users(id)
);

INSERT INTO admins(`user_id`, `salary`, `employment_date`) VALUES (1, 0, STR_TO_DATE('1 January 2000', '%d %M %Y'));

CREATE TABLE `companies` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `address` varchar(100) NOT NULL,
    `iban` varchar(100) NOT NULL,
    `bank_name` varchar(100) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE `products` (
	`id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `price` double NOT NULL,
    `producer_id` int NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`producer_id`) REFERENCES companies(id)
);

CREATE TABLE `shoppingCarts` (
	`customer_id` int NOT NULL,
    `product_id` int NOT NULL,
    `quantity` int NOT NULL,
    FOREIGN KEY(`customer_id`) REFERENCES customers(user_id),
    FOREIGN KEY(`product_id`) REFERENCES products(id),
    PRIMARY KEY(`customer_id`, `product_id`)
);

CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,
	`customer_id` int NOT NULL,
    `dateTime` Timestamp NOT NULL,
    `total_cost` double NOT NULL,
    `address` varchar(100) NOT NULL,
    `discount` double NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`customer_id`) REFERENCES customers(user_id)
);

CREATE TABLE `orderedProducts` (
	`order_id` int NOT NULL,
    `product_id` int NOT NULL,
    `quantity` int NOT NULL,
    PRIMARY KEY(`order_id`, `product_id`),
    FOREIGN KEY(`order_id`) REFERENCES orders(id),
    FOREIGN KEY(`product_id`) REFERENCES products(id)
);