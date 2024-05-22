package com.gengby.starter.data.core.enums;

import com.gengby.starter.data.core.function.ISqlFunction;

import java.io.Serializable;

/**
 * 数据库类型枚举
 *
 * @author Noah
 * @since 1.4.1
 */
public enum DatabaseType implements ISqlFunction {

    /**
     * MySQL
     */
    MYSQL("MySQL") {
        @Override
        public String findInSet(Serializable value, String set) {
            return "find_in_set('%s', %s) <> 0".formatted(value, set);
        }
    },

    /**
     * PostgreSQL
     */
    POSTGRE_SQL("PostgreSQL") {
        @Override
        public String findInSet(Serializable value, String set) {
            return "(select position(',%s,' in ','||%s||',')) <> 0".formatted(value, set);
        }
    },;

    private final String database;

    DatabaseType(String database) {
        this.database = database;
    }

    /**
     * 获取数据库类型
     *
     * @param database 数据库
     */
    public static DatabaseType get(String database) {
        for (DatabaseType databaseType : DatabaseType.values()) {
            if (databaseType.database.equalsIgnoreCase(database)) {
                return databaseType;
            }
        }
        return null;
    }

    public String getDatabase() {
        return database;
    }
}
