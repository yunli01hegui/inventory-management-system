-- 创建数据库
CREATE DATABASE inventory_system;

-- 使用数据库
USE inventory_system;

-- 创建用户表，存储用户身份信息
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('admin', 'user') NOT NULL -- admin为管理员，user为普通用户
);

-- 创建商品表，存储商品库存信息
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- 插入一些用户数据
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
INSERT INTO users (username, password, role) VALUES ('user1', 'user123', 'user');
INSERT INTO users (username, password, role) VALUES ('user2', 'user123', 'user');

-- 插入一些商品数据
INSERT INTO products (name, quantity, price) VALUES ('Product A', 100, 19.99);
INSERT INTO products (name, quantity, price) VALUES ('Product B', 200, 9.99);
INSERT INTO products (name, quantity, price) VALUES ('Product C', 50, 29.99);
