--CREATE DATABASE SHOPTSHIRT;
--USE SHOPTSHIRT;

CREATE TABLE SANPHAM (
    ID VARCHAR(20) PRIMARY KEY,
    TenSanPham NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE MAUSAC (
    ID VARCHAR(20) PRIMARY KEY,
    TenMau NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE SIZE (
    ID VARCHAR(20) PRIMARY KEY,
    Ten NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE CHATLIEU (
    ID VARCHAR(20) PRIMARY KEY,
    Ten NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE THUONGHIEU (
    ID VARCHAR(20) PRIMARY KEY,
    Ten NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE SANPHAMCHITIET (
    ID VARCHAR(20) PRIMARY KEY,
    ID_SanPham VARCHAR(20) REFERENCES SANPHAM(ID),
    ID_MauSac VARCHAR(20) REFERENCES MAUSAC(ID),
    ID_Size VARCHAR(20) REFERENCES SIZE(ID),
    ID_ChatLieu VARCHAR(20) REFERENCES CHATLIEU(ID),
    ID_ThuongHieu VARCHAR(20) REFERENCES THUONGHIEU(ID),
    GiaBan DECIMAL(20,0) NOT NULL,
    SoLuongTon INT NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE NHANVIEN (
    ID VARCHAR(20) PRIMARY KEY,
    HoTen NVARCHAR(50) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    Email VARCHAR(30) NOT NULL,
    NamSinh INT NOT NULL,
    GioiTinh NVARCHAR(10) NOT NULL,
    ChucVu BIT NOT NULL,
    MatKhau VARCHAR(50) NOT NULL,
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE KHACHHANG (
    ID VARCHAR(20) PRIMARY KEY,
    HoTen NVARCHAR(50) NOT NULL,
    DiaChi NVARCHAR(100) NOT NULL,
    SoDienThoai VARCHAR(15) NOT NULL,
    Email VARCHAR(30) NOT NULL,
    GioiTinh NVARCHAR(10) NOT NULL,
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE VOUCHER (
    ID VARCHAR(20) PRIMARY KEY,
    TenVoucher NVARCHAR(50) NOT NULL,
    SoLuong INT NOT NULL,
    LoaiVoucher NVARCHAR(50) NOT NULL,
    MucGiamGia DECIMAL(20,3) NOT NULL,
    NgayBatDau DATETIME NOT NULL,
    NgayKetThuc DATETIME NOT NULL,
    MoTa NVARCHAR(255),
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE HOADON (
    ID VARCHAR(20) PRIMARY KEY,
    ID_NhanVien VARCHAR(20) REFERENCES NHANVIEN(ID),
    ID_KhachHang VARCHAR(20) REFERENCES KHACHHANG(ID),
    ID_Voucher VARCHAR(20) REFERENCES VOUCHER(ID),
    HinhThucThanhToan NVARCHAR(50),
    TongTien DECIMAL(20,0) NOT NULL,
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

CREATE TABLE HOADONCHITIET (
    ID VARCHAR(20) PRIMARY KEY,
    ID_HoaDon VARCHAR(20) REFERENCES HOADON(ID),
    ID_SanPhamChiTiet VARCHAR(20) REFERENCES SANPHAMCHITIET(ID),
    SoLuong INT NOT NULL,
    ThanhTien DECIMAL(20,0) NOT NULL,
    NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NgaySua DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    TrangThai NVARCHAR(100) NOT NULL
);

-- Insert dữ liệu mẫu vào từng bảng
INSERT INTO SANPHAM(ID, TenSanPham, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('SP01', N'UNIQLO SAZA', N'Họa tiết in logo nổi tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SP02', N'UNISEX LAZA', N'Họa tiết in logo nổi đơn giản nhỏ gọn, tạo điểm nhấn tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SP03', N'ADIDAS BLAZ', N'Họa tiết in nổi nhỏ nhắn tinh tế', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SP04', N'DIOR NIZAO', N'Thiết kế áo đơn giản, không họa tiết', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SP05', N'NIKE AZOA', N'Ứng dụng kiểu dáng Raglan theo xu hướng hiện tại', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh');

INSERT INTO MAUSAC(ID, TenMau, MoTa, NgayTao, NgaySua)
VALUES ('MS001', N'Trắng', N'new', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('MS002', N'Đen', N'new', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('MS003', N'Xanh', N'new', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('MS004', N'Be', N'new', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO SIZE(ID, Ten, MoTa, NgayTao, NgaySua)
VALUES ('S001', N'S', N'Cân nặng 53-60kg; Ngang vai: 44cm; Rộng ngực: 100cm; Dài áo: 69cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('S002', N'M', N'Cân nặng 60-68kg; Ngang vai: 45cm; Rộng ngực: 104cm; Dài áo: 70cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('S003', N'L', N'Cân nặng 68-78kg; Ngang vai: 46cm; Rộng ngực: 108cm; Dài áo: 71cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('S004', N'XL', N'Cân nặng 78-88kg; Ngang vai: 48cm; Rộng ngực: 112cm; Dài áo: 72cm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO CHATLIEU(ID, Ten, MoTa, NgayTao, NgaySua)
VALUES ('CL001', N'Chất vải CVC', N'Chất vải CVC co giãn thoải mái, bề mặt vải thông thoáng', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CL002', N'Chất liệu Cotton', N'Chất liệu Cotton mềm mại, co giãn, thông thoáng tối đa.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CL003', N'Chất vải cá sấu', N'Chất vải cá sấu co giãn 2 chiều thoải mái, bề mặt vải thông thoáng.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('CL004', N'Chất vải nhung', N'Chất vải nhung gân mềm mịn, hỗ trợ giữ nhiệt nhẹ.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO THUONGHIEU(ID, Ten, MoTa, NgayTao, NgaySua)
VALUES ('TH001', N'Guccibag', N'Guccibag là nhãn hiệu thời trang với nhiều sản phẩm giày dép và túi xách có màu sắc và kiểu dáng cổ điển', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('TH002', N'Adidss', N'Adidss là nhãn hiệu thời trang được biết đến bởi sản phẩm giày thể thao và quần áo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('TH003', N'Chaneel', N'Chaneel là nhãn hiệu thời trang với nhiều sản phẩm giày dép và túi xách có màu sắc và kiểu dáng cổ điển', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('TH004', N'Doel', N'Doel là nhãn hiệu thời trang được biết đến bởi sản phẩm giày thể thao và quần áo', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO SANPHAMCHITIET(ID, ID_SanPham, ID_MauSac, ID_Size, ID_ChatLieu, ID_ThuongHieu, GiaBan, SoLuongTon, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('SPCT001', 'SP01', 'MS001', 'S001', 'CL001', 'TH001', 1290000, 50, N'Trước khi sản phẩm xuất hiện nhiều sản phẩm giày dép này sẽ trở nên bí ẩn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SPCT002', 'SP01', 'MS002', 'S002', 'CL002', 'TH002', 1190000, 30, N'Sản phẩm đẹp đến từ nước ngoài đẹp mắt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SPCT003', 'SP02', 'MS003', 'S003', 'CL003', 'TH003', 2390000, 40, N'Sản phẩm được tạo ra dựa trên một nhiều đặc thù bản độ đẹp mắt của sản phẩm giày dép.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SPCT004', 'SP02', 'MS004', 'S004', 'CL004', 'TH004', 2890000, 60, N'Đây là một sản phẩm tạo ra nhiều cảm hứng cho người mua', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh'),
       ('SPCT005', 'SP03', 'MS001', 'S001', 'CL001', 'TH001', 1590000, 80, N'Có sự xuất hiện nhiều sản phẩm giày dép này sẽ có sự trở nên bí ẩn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang kinh doanh');

INSERT INTO NHANVIEN(ID, HoTen, DiaChi, SoDienThoai, Email, NamSinh, GioiTinh, ChucVu, MatKhau, NgayTao, NgaySua, TrangThai)
VALUES ('NV001', N'Nguyễn Văn A', N'Hà Nội', '0987654321', 'nva@gmail.com', 1990, N'Nam', 1, '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang làm'),
       ('NV002', N'Nguyễn Văn B', N'Hà Nội', '0987654321', 'nvb@gmail.com', 1991, N'Nam', 0, '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Nghỉ việc'),
       ('NV003', N'Nguyễn Văn C', N'Hà Nội', '0987654321', 'nvc@gmail.com', 1992, N'Nam', 1, '123456', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Nghỉ việc');

INSERT INTO KHACHHANG(ID, HoTen, DiaChi, SoDienThoai, Email, GioiTinh, NgayTao, NgaySua, TrangThai)
VALUES ('KH001', N'Trần Thị A', N'Hà Nội', '0987654321', 'a@gmail.com', N'Nữ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang hoạt động'),
       ('KH002', N'Trần Thị B', N'Hà Nội', '0987654321', 'b@gmail.com', N'Nữ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang hoạt động');

INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, NgayBatDau, NgayKetThuc, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('VC001', N'Voucher Giảm 10%', 100, N'Giảm giá', 10.000, '2023-05-01 00:00:00', '2023-06-01 00:00:00', N'Giảm giá 10% cho toàn bộ sản phẩm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang hoạt động'),
       ('VC002', N'Voucher Giảm 20%', 200, N'Giảm giá', 20.000, '2023-05-01 00:00:00', '2023-06-01 00:00:00', N'Giảm giá 20% cho toàn bộ sản phẩm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đang hoạt động');

INSERT INTO HOADON(ID, ID_NhanVien, ID_KhachHang, ID_Voucher, HinhThucThanhToan, TongTien, NgayTao, NgaySua, TrangThai)
VALUES ('HD001', 'NV001', 'KH001', 'VC001', N'Tiền mặt', 3000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HD002', 'NV001', 'KH002', 'VC002', N'Chuyển khoản', 5000000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán');

INSERT INTO HOADONCHITIET(ID, ID_HoaDon, ID_SanPhamChiTiet, SoLuong, ThanhTien, NgayTao, NgaySua, TrangThai)
VALUES ('HDCT001', 'HD001', 'SPCT001', 1, 1290000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT002', 'HD001', 'SPCT002', 1, 1190000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT003', 'HD002', 'SPCT003', 1, 2390000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán'),
       ('HDCT004', 'HD002', 'SPCT004', 1, 2890000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Đã thanh toán');

	   INSERT INTO KHACHHANG(ID, HoTen, DiaChi, SoDienThoai, Email, GioiTinh, NgayTao, NgaySua, TrangThai)Values
('KH00', N'Khách bán lẻ', N'Hà nội', '0123456789', 'khachbanle@gmail.com', N'Nam', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động');
GO
INSERT INTO VOUCHER(ID, TenVoucher, SoLuong, LoaiVoucher, MucGiamGia, NgayBatDau, NgayKetThuc, MoTa, NgayTao, NgaySua, TrangThai)
VALUES ('V00', 'Discount0%', 999999999, N'Giảm theo phần trăm', 0, '2100-03-18 00:00:00', '2024-06-30 00:00:00', N'Hóa đơn giảm 0%', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, N'Hoạt động');
