CREATE TABLE t_user
(
    id         varchar(32) NOT NULL COMMENT '人员主键',
    name       varchar(32)  DEFAULT NULL COMMENT '人员名称',
    avatar_url varchar(256) DEFAULT NULL COMMENT '人员头像地址',
    PRIMARY KEY (id)
);