DROP DATABASE IF EXISTS QuanLyCuaHang;
CREATE DATABASE IF NOT EXISTS QuanLyCuaHang;

USE QuanLyCuaHang;

DROP TABLE IF EXISTS ChiNhanh;
CREATE TABLE IF NOT EXISTS ChiNhanh(
	id 			INT AUTO_INCREMENT PRIMARY KEY,
    diaChi		VARCHAR(100) NOT NULL UNIQUE,
    soLuongQuanLy		INT NOT NULL,
    soLuongNhanVien		INT NOT NULL
);

DROP TABLE IF EXISTS NhanVien;
CREATE TABLE IF NOT EXISTS NhanVien(
	id 			INT AUTO_INCREMENT PRIMARY KEY,
    firstName	VARCHAR(50) NOT NULL,
    lastName	VARCHAR(50) NOT NULL,
    gioiTinh	ENUM("Nam", "Nữ", "Khác"),
    `role`		ENUM("PARTTIME", "FULLTIME"),
    namSinh		INT NOT NULL,
    chiNhanhID	INT NOT NULL,
	FOREIGN KEY(chiNhanhID) REFERENCES ChiNhanh(id)
);

DROP TABLE IF EXISTS QuanLy;
CREATE TABLE IF NOT EXISTS QuanLy(
	id INT AUTO_INCREMENT PRIMARY KEY,
    firstName	VARCHAR(50) NOT NULL,
    lastName	VARCHAR(50) NOT NULL,
    gioiTinh	ENUM("Nam", "Nữ", "Khác"),
    namSinh		INT NOT NULL,
    chiNhanhId	INT,
    username	VARCHAR(50) NOT NULL UNIQUE,
    `password`	VARCHAR(100) NOT NULL,
    `role`		ENUM("ADMIN", "QL1", "QL2", "QL3"),	-- 1: Chi nhánh 1, 2: Chi nhánh 2, 3: Chi nhánh 3, 0: Admin
     FOREIGN KEY(chiNhanhID) REFERENCES ChiNhanh(id)
);

DROP TABLE IF EXISTS SanPham;
CREATE TABLE IF NOT EXISTS SanPham(
	id INT AUTO_INCREMENT PRIMARY KEY,
    tenSP		VARCHAR(50) NOT NULL UNIQUE,
    donGia		INT UNSIGNED NOT NULL,
    soLuong		INT UNSIGNED NOT NULL,
    ngayNhap	DATE NOT NULL,
    ngayHetHan	DATE NOT NULL
);
        
INSERT INTO ChiNhanh (diaChi, soLuongQuanLy, soLuongNhanVien)
VALUES	("Phúc Diễn, Từ Liêm, Hà Nội", 2, 8),
        ("Mỹ Đình, Nam Từ Liêm, Hà Nội", 2, 10),
        ("Minh Khai, Từ Liêm, Hà Nội", 2, 8);
        
INSERT INTO NhanVien (firstName, lastName, gioiTinh, `role`, namSinh, chiNhanhID)
VALUES	("Nguyễn Trọng", "Hoàng", "Nam", "PARTTIME", 2000, 1),
		("Nguyễn Hải", "Yến", "Nữ", "PARTTIME", 2000, 1),
        ("Nguyễn Quốc", "Quân", "Nam", "FULLTIME", 1999, 1),
        ("Phạm Thanh", "Sơn", "Nam", "PARTTIME", 2000, 1),
        ("Lê Hà", "Thu", "Nữ", "FULLTIME", 2002, 1),
        ("Nguyễn Thảo", "Nguyên", "Nữ", "PARTTIME", 2000, 1),
        ("Đỗ Khánh", "Vinh", "Nam", "FULLTIME", 2000, 1),
        ("Lý Thánh", "Lâm", "Nam", "FULLTIME", 2001, 1),
        ("Phạm Văn", "Đức", "Nam", "FULLTIME", 2001, 2),
        ("Hoàng Hữu", "Duy", "Nam", "PARTTIME", 2001, 2),
        ("Lưu Thuỳ", "Dung", "Nữ", "PARTTIME", 2000, 2),
        ("Bùi Thị", "Hà", "Nữ", "FULLTIME", 2001, 2),
        ("Bùi Huy", "Đông", "Nam", "PARTTIME", 1999, 2),
        ("Nguyễn Văn", "Huy", "Nam", "FULLTIME", 2001, 2),
        ("Trần Viết", "Cường", "Nam", "FULLTIME", 2001, 2),
        ("Hoàng Đức", "Hùng", "Nam", "PARTTIME", 2000, 2),
        ("Nguyễn Thị", "Hảo", "Nữ", "PARTTIME", 2001, 2),
        ("Phạm Văn", "Vũ", "Nam", "FULLTIME", 2001, 2),
        ("Nguyễn Trung", "Dũng", "Nam", "FULLTIME", 2001, 3),
        ("Nguyễn Trọng", "Thành", "Nam", "FULLTIME", 2000, 3),
        ("Nguyễn Hải", "Linh", "Nữ", "PARTTIME", 1999, 3),
        ("Nguyễn Thị Ngọc", "Hân", "Nữ", "PARTTIME", 2001, 3),
        ("Nguyễn Thuỳ", "Linh", "Nữ", "PARTTIME", 2000, 3),
        ("Nguyễn Văn", "Toàn", "Nam", "PARTTIME", 2001, 3),
        ("Nguyễn Văn", "Việt", "Nam", "FULLTIME", 2000, 3),
        ("Nguyễn Công", "Huy", "Nam", "FULLTIME", 2001, 3);
        
        
INSERT INTO QuanLy(firstName, lastName, gioiTinh, namSinh, chiNhanhID, username, `password`, `role`)
VALUES	("Phạm Thu", "Lan", "Nữ", 1993, 1, "lanpham", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL1"),
		("Nguyễn Hoàng", "Dũng", "Nam", 1994, 1, "dungnguyen", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL1"),
        ("Bùi Thị", "Linh", "Nữ", 1993, 2, "linhbui", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL2"),
        ("Phan Thanh", "Sơn", "Nam", 1992, 2, "sonphan", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL2"),
        ("Nguyễn Văn", "Tuấn", "Nam", 1993, 3, "tuannguyen", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL3"),
        ("Nguyễn Văn", "Vượng", "Nam", 1993, 3, "vuongnguyen", '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "QL3"),
        ("Nguyễn Hữu", "Trí", "Nam", 1988, NULL, 'tringuyen', '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', "ADMIN");

insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Beef - Striploin Aa', 10, 69, '2022-09-23', '2024-05-02');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Cheese - Camembert', 28, 30, '2023-06-22', '2024-05-17');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Oven Mitt - 13 Inch', 10, 45, '2022-11-19', '2024-07-17');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Appetizer - Southwestern', 6, 21, '2022-12-01', '2024-08-30');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Milk - Buttermilk', 6, 94, '2023-01-23', '2025-05-04');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pastry - Cheese Baked Scones', 18, 31, '2022-09-05', '2024-09-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Lobster - Base', 17, 23, '2022-12-23', '2024-05-08');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Lamb Shoulder Boneless Nz', 19, 89, '2022-12-25', '2024-11-27');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Scallop - St. Jaques', 23, 11, '2022-12-13', '2024-07-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Cod - Salted, Boneless', 19, 39, '2022-09-07', '2025-05-06');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Wine - Prosecco Valdobienne', 16, 30, '2022-12-02', '2025-01-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Garam Marsala', 24, 5, '2023-08-05', '2024-04-12');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Tea - Grapefruit Green Tea', 17, 41, '2022-09-26', '2024-04-10');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Truffle Cups - Brown', 25, 11, '2023-04-01', '2024-05-16');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Parsnip', 1, 74, '2023-04-22', '2025-05-15');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pepper - Green, Chili', 4, 45, '2023-04-18', '2024-06-15');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Wine - Ej Gallo Sierra Valley', 22, 36, '2023-06-07', '2024-11-28');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('White Baguette', 3, 46, '2023-03-01', '2025-03-13');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Salt And Pepper Mix - Black', 24, 33, '2023-08-23', '2024-07-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Garbage Bags - Black', 9, 10, '2023-05-25', '2024-02-25');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Cassis', 20, 43, '2023-06-03', '2024-07-11');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Vinegar - White', 6, 7, '2023-03-27', '2024-03-13');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pasta - Cannelloni, Sheets, Fresh', 10, 50, '2023-07-25', '2024-10-24');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Lemonade - Black Cherry, 591 Ml', 10, 62, '2023-08-06', '2024-03-14');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Beef - Top Sirloin', 7, 3, '2023-04-07', '2025-04-22');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Appetizer - Assorted Box', 18, 33, '2023-07-06', '2025-03-10');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Cheese Cheddar Processed', 20, 42, '2023-07-18', '2025-02-16');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pasta - Lasagna, Dry', 3, 11, '2023-04-08', '2024-12-12');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pesto - Primerba, Paste', 20, 16, '2022-09-28', '2024-03-06');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Nestea - Ice Tea, Diet', 21, 39, '2022-08-27', '2024-06-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Pork - Bacon Cooked Slcd', 22, 30, '2022-12-11', '2024-04-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Turkey - Breast, Double', 21, 77, '2022-10-12', '2024-12-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Magnotta - Bel Paese White', 20, 39, '2023-05-31', '2024-12-07');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Jam - Raspberry,jar', 12, 63, '2022-09-03', '2025-01-17');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Sauerkraut', 17, 15, '2022-09-15', '2024-10-29');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Wine - Pinot Grigio Collavini', 2, 63, '2023-05-26', '2024-07-26');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Sardines', 30, 5, '2023-04-21', '2024-10-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Sauce - Sesame Thai Dressing', 11, 28, '2023-07-16', '2024-04-27');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Bread - Multigrain', 9, 1, '2023-04-21', '2025-05-11');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Tabasco Sauce, 2 Oz', 24, 71, '2022-09-18', '2025-05-12');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Crawfish', 20, 82, '2023-01-02', '2024-06-01');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Goat - Whole Cut', 6, 56, '2023-02-24', '2025-01-21');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Fondant - Icing', 13, 34, '2022-12-01', '2025-02-10');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Haggis', 10, 8, '2022-11-23', '2024-05-25');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Cheese - Grana Padano', 29, 55, '2022-10-27', '2024-04-02');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Sauce - Mint', 23, 17, '2022-09-06', '2024-06-24');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Loaf Pan - 2 Lb, Foil', 1, 93, '2023-08-17', '2025-04-02');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Sauce - Alfredo', 1, 81, '2022-11-23', '2024-05-06');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Longos - Grilled Chicken With', 27, 99, '2023-05-30', '2025-02-23');
insert into SanPham (tenSP, donGia, soLuong, ngayNhap, ngayHetHan) values ('Lettuce - Frisee', 23, 85, '2023-03-28', '2024-08-05');
