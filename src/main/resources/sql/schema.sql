
CREATE SCHEMA IF NOT EXISTS `demo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `demo`;

-- users table (compatible with Spring's JdbcUserDetailsManager)
CREATE TABLE IF NOT EXISTS `users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- authorities table (compatible with Spring's JdbcUserDetailsManager)
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1`
    FOREIGN KEY (`username`) REFERENCES `users` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX IF NOT EXISTS `idx_authority` ON `authorities` (`authority`);

-- === Example data (FOR TESTING ONLY) ===
-- NOTE: Passwords must be BCrypt hashed before inserting.
-- Example: BCrypt hash for password "admin"
INSERT INTO `users` (`username`, `password`, `enabled`)
VALUES
  ('admin', '$2a$10$e0NRb5y1Xv0sK8bQeYtP1eZcY1t9s3G9Zs6Hn2Q1uK/6sW0s1h9aK', 1);

INSERT INTO `authorities` (`username`, `authority`)
VALUES
  ('admin', 'ROLE_ADMIN'),

