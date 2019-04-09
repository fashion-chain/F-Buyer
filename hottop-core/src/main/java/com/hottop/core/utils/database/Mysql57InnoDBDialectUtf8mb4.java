package com.hottop.core.utils.database;

import org.hibernate.dialect.MySQL57InnoDBDialect;

public class Mysql57InnoDBDialectUtf8mb4 extends MySQL57InnoDBDialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci";
    }
}
