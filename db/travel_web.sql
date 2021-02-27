CREATE DATABASE `travel_web`;

# 产品表
DROP TABLE `product`;
CREATE TABLE `product`(
    `id` VARCHAR(32) PRIMARY KEY COMMENT '产品id',
    `productNum` VARCHAR(50) NOT NULL UNIQUE COMMENT '产品编号',
    `productName` VARCHAR(50) COMMENT '产品名字',
    `cityName` VARCHAR(50) COMMENT '出发城市',
    `departureTime` TIMESTAMP COMMENT '出发时间',
    `productPrice` DECIMAL(11,2) COMMENT '产品价格',
    `productDesc` VARCHAR(500) COMMENT '产品描述',
    `productStatus` INT COMMENT '产品状态 0关闭 1开启'
);

# 创建id的触发器
DELIMITER $$
CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `travel_web`.`product_trigger` -- 触发器名称
    BEFORE INSERT             -- 触发器被触发的时机
    ON `travel_web`.`product`       -- 触发器所作用的表名称
    FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END$$
DELIMITER ;

INSERT INTO `product`(`productNum`,`productName`,`cityName`,`departureTime`,`productPrice`,`productDesc`,`productStatus`)
VALUES('001','钦州三日游','钦州','2020-11-16 22:39:54',1000,'非常不错的旅行',1);

INSERT INTO `product`(`productNum`,`productName`,`cityName`,`departureTime`,`productPrice`,`productDesc`,`productStatus`)
VALUES('002','北京三日游','北京','2018-10-6 8:23:50',1500,'记忆深刻的旅行',1);

INSERT INTO `product`(`productNum`,`productName`,`cityName`,`departureTime`,`productPrice`,`productDesc`,`productStatus`)
VALUES('003','合肥三日游','合肥','2021-2-16 9:30:00',800,'家乡的旅行',1);

# order表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
     `id` VARCHAR(32) NOT NULL COMMENT '主键uuid',
     `orderNum` VARCHAR(50) NOT NULL COMMENT '订单编号',
     `orderTime` TIMESTAMP DEFAULT NULL COMMENT '下单时间',
     `travelerCount` INT DEFAULT NULL COMMENT '旅客人数',
     `orderDesc` VARCHAR(500) DEFAULT NULL COMMENT '订单描述',
     `payType` INT DEFAULT NULL COMMENT '支付方式 0支付宝 1微信 2其他',
     `orderStatus` INT DEFAULT NULL COMMENT '订单状态 0未支付 1已支付',
     `productId` VARCHAR(32) NOT NULL COMMENT '产品id外键',
     `memberId` VARCHAR(32) NOT NULL COMMENT '会员（联系人）id外键',
     PRIMARY KEY (`id`),
     UNIQUE KEY  (`orderNum`),
     FOREIGN KEY (`productId`) REFERENCES product(`id`),
     FOREIGN KEY (`memberId`) REFERENCES member(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DELIMITER $$
CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `travel_web`.`order_trigger` -- 触发器名称
    BEFORE INSERT             -- 触发器被触发的时机
    ON `travel_web`.`order`       -- 触发器所作用的表名称
    FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END$$
DELIMITER ;

insert into `order`( `orderNum`, `orderTime`, `travelerCount`, `orderDesc`, `payType`, `orderStatus`, `productId`, `memberId`)
VALUES ('001', '2020-11-15 22:39:54', 5, '家庭出游', 0, 0, '0c8e7958783611ebb96d005056c00001', '437d4c2178be11ebb96d005056c00001');

insert into `order`( `orderNum`, `orderTime`, `travelerCount`, `orderDesc`, `payType`, `orderStatus`, `productId`, `memberId`)
VALUES ('002', '2020-11-15 22:39:54', 20, '公司出游', 3, 1, '928b9fcb74e411eb80f9005056c00001', '437d4c2178be11ebb96d005056c00001');

insert into `order`( `orderNum`, `orderTime`, `travelerCount`, `orderDesc`, `payType`, `orderStatus`, `productId`, `memberId`)
VALUES ('003', '2020-11-15 22:39:54', 50, '学生出游', 2, 0, '928da15374e411eb80f9005056c00001', '437d4c2178be11ebb96d005056c00001');

# 会员表
DROP TABLE `member`;
CREATE TABLE `member`(
     `id` VARCHAR(32) PRIMARY KEY COMMENT '会员id',
     `memberName` VARCHAR(20) COMMENT '会员名',
     `nickname` VARCHAR(20) COMMENT '昵称',
     `phoneNum` VARCHAR(20) COMMENT '手机号码',
     `email` VARCHAR(50)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DELIMITER $$
CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `travel_web`.`member_trigger` -- 触发器名称
    BEFORE INSERT             -- 触发器被触发的时机
    ON `travel_web`.`member`       -- 触发器所作用的表名称
    FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END$$
DELIMITER ;

INSERT INTO `MEMBER` (`memberName`, `nickname`, `phonenum`, `email`)
VALUES ('张三', '小三', '18888888888', 'zs@163.com');

# 旅客信息表
DROP TABLE `traveler`;
CREATE TABLE `traveler`(
    `id` VARCHAR(32) PRIMARY KEY COMMENT '旅客id',
    `travelerName` VARCHAR(20) COMMENT '旅客名',
    `sex` VARCHAR(20) COMMENT '性别',
    `phoneNum` VARCHAR(20) COMMENT '手机号码',
    `credentialsType` INT COMMENT '证件类型 0身份证 1护照 2军官证',
    `credentialsNum` varchar(50) COMMENT '证件号码',
    `travelerType` INT COMMENT '旅客类型(人群) 0成人 1儿童'
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DELIMITER $$
CREATE
    /*[DEFINER = { user | CURRENT_USER }]*/
    TRIGGER `travel_web`.`traveler_trigger` -- 触发器名称
    BEFORE INSERT             -- 触发器被触发的时机
    ON `travel_web`.`traveler`       -- 触发器所作用的表名称
    FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END$$
DELIMITER ;

insert into `traveler`(`travelerName`, `sex`, `phoneNum`, `credentialsType`, `credentialsNum`, `travelerType`)
values ('张龙', '男', '17632456425', 0, '340205200010130885', 0);
insert into `traveler`(`travelerName`, `sex`, `phoneNum`, `credentialsType`, `credentialsNum`, `travelerType`)
values ('张小龙', '男', '17632456425', 0, '340205201002352434', 1);

# 旅客-订单关系表（多对多）
DROP TABLE IF EXISTS `order_traveler`;
CREATE TABLE `order_traveler`(
   `orderId` VARCHAR(32) COMMENT '订单id',
   `travelerId` VARCHAR(32) COMMENT '旅客id',
   PRIMARY KEY (`orderId`, `travelerId`),
   FOREIGN KEY (`orderId`) REFERENCES `order`(`id`),
   FOREIGN KEY (`travelerId`) REFERENCES `traveler`(`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into order_traveler(orderId, travelerId) VALUES ('2eed19c178c611ebb96d005056c00001', 'a2fb57f978cb11ebb96d005056c00001');
insert into order_traveler(orderId, travelerId) VALUES ('2eed19c178c611ebb96d005056c00001', 'a2fd426478cb11ebb96d005056c00001');