-- To use this script I had to:
--      - remove all quotes
--      - add semicolons to the end of each statement

CREATE TABLE creditcards (
  id varchar(20) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  expiration date NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE genres (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(32) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE movies (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  year int(11) NOT NULL,
  director varchar(100) NOT NULL,
  banner_url varchar(200) DEFAULT NULL,
  trailer_url varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE stars (
  id int(11) NOT NULL AUTO_INCREMENT,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  dob date DEFAULT NULL,
  photo_url varchar(200) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `cc_id` varchar(20) NOT NULL,
  `address` varchar(200) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cust_cc_cons` (`cc_id`),
  CONSTRAINT `cust_cc_cons` FOREIGN KEY (`cc_id`) REFERENCES `creditcards` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `genres_in_movies` (
  `genre_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  KEY `genre_gen_cons` (`genre_id`),
  KEY `genres_mov_cons` (`movie_id`),
  CONSTRAINT `genre_gen_cons` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `genres_mov_cons` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `sales_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sales_customer_cons` (`customer_id`),
  KEY `sales_movie_cons` (`movie_id`),
  CONSTRAINT `sales_customer_cons` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sales_movie_cons` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `stars_in_movies` (
  `star_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  KEY `star_star_cons` (`star_id`),
  KEY `star_movie_cons` (`movie_id`),
  CONSTRAINT `star_star_cons` FOREIGN KEY (`star_id`) REFERENCES `stars` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `star_movie_cons` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
