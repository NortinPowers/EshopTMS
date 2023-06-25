insert into product_category (category)
values ('photo'),
       ('computer'),
       ('watch');

alter table products alter column info type varchar(1500);

insert into products (name, price, product_category_id, info)
values ('Canon EOS 4000D Kit 18-55mm III', 350, 3, 'SLR camera, Canon EF-S bayonet mount, APS-C matrix (1.6 crop) 18 MP, with F3.5-5.6 18-55mm lens, Wi-Fi'),
       ('Canon EOS R Body', 2499, 3, 'mirrorless camera, Canon RF mount, Full frame matrix (full frame) 30.3 MP, without lens (body), Wi-Fi'),
       ('Fujifilm Instax Mini 11', 110, 3, 'camera for instant printing'),
       ('Canon EOS 250D Kit 18-55 IS STM', 920, 3, 'SLR camera, Canon EF-S bayonet mount, APS-C matrix (1.6 crop) 24.1 MP, with F4-5.6 18-55 mm lens, Wi-Fi'),
       ('Sony Alpha a6000 Kit 16-50mm', 850, 3, 'Mirrorless camera, Sony E mount, APS-C matrix (1.5 crop) 24.3 MP, with F3.5-5.6 16-50 mm lens, Wi-Fi'),
       ('Fujifilm Instax Mini 40', 220, 3, 'camera for instant printing F5.6-16 60 mm'),
       ('Xiaomi Watch S1 Active', 190, 5, 'smart watch, Android/iOS support, AMOLED 1.43" screen (466x466, touch), pedometer, Heart rate monitor, Case: plastic/metal, Bracelet: Silicone'),
       ('Huawei Watch GT 3 Classic', 320, 5, 'smart watch, Android/iOS support, AMOLED 1.43" screen (466x466, touch), pedometer, heart rate monitor, working time: 2 days, Case: plastic/steel, Bracelet: leather'),
       ('Haylou RS3 LS04', 50, 5, 'smart watch, Android/iOS support, AMOLED 1.2" screen (390x390, touch), pedometer, heart rate monitor, Working time: 3 weeks, Case: aluminum, Bracelet: silicone'),
       ('Apple Watch Series 8', 700, 5, 'smart watch, iOS support, OLED screen, pedometer, heart rate monitor, working time: 1 day 12 hours, case: aluminum, bracelet: silicone'),
       ('Apple Watch Series 7', 600, 5, 'smart watch, iOS support, AMOLED screen, pedometer, heart rate monitor, working time: 18 hours, case: aluminum, bracelet: fluoroelastomer'),
       ('Samsung Galaxy Watch 5 Pro', 500, 5, 'smart watch, Android support, AMOLED 1.36" screen (450x450, touch), pedometer, heart rate monitor, working time: 3 days 8 hours, case: titanium, bracelet: fluoroelastomer'),
       ('ASUS M3700WUAK-WA027M', 1320, 4, '27" 1920 x 1080 IPS, matte, non-touch, AMD Ryzen 7 5700U 1800 MHz, 16 GB, SSD 512 GB, video card built-in, without OS, color white'),
       ('Lenovo IdeaCentre 3 27ITL6 F0FW00LERK', 710, 4, '27" 1920 x 1080 IPS, matte, non-touch, Intel Core i3 1115G4 3000 MHz, 8 GB, HDD 1000 GB + SSD 256 GB, video card built-in, without OS, color white'),
       ('HP 24-df1062ny 4X5E0EA', 1230, 4, '23.8" 1920 x 1080 IPS, matte, touch, Intel Core i5 1135G7 2400 MHz, 8 GB, HDD 1000 GB, video card built-in, without OS, color white'),
       ('Apple iMac M1 2021', 3320, 4, '23.5" 4480 x 2520 IPS, glossy, non-touch, Apple M1, 8 GB, 512 GB SSD, Built-in graphics card, Mac OS, color green'),
       ('PowerCool P2386BK', 340, 4, '23.8" 1920 x 1080, matte, non-touch, color black'),
       ('MSI Pro AP242 12M-234X', 1100, 4, '23.8" 1920 x 1080 IPS, matte, non-touch, Intel Core i5 12400 2500 MHz, 8 GB, SSD 512 GB, video card built-in, without OS, color white'),
       ('SMitsu AIO-O2708X', 600, 4, '27" 1920 x 1080 IPS, matte, non-touch, Intel Pentium Gold G6405 4100MHZ, 8 GB, 512 GB SSD, built-in graphics card, without OS, color black');