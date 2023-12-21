CREATE DATABASE IF NOT EXISTS atmmanager;

USE atmmanager;
-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
    fullName nvarchar(100),
    dni nvarchar(20),
    email nvarchar(50),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.0
);

-- Tabla de movimientos
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    transaction_type NVARCHAR(20) NOT NULL, -- 'deposit' o 'withdrawal'
    amount DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);