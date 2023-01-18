package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryBuilder {
    private final Connection conn;
    private PreparedStatement stmt;
    private StringBuilder query;

    public QueryBuilder(Connection conn) {
        this.conn = conn;
        this.query = new StringBuilder();
    }

    public QueryBuilder select(String... columns) {
        query.append("SELECT ");
        if (columns[0].equals("*")) {
            query.append("* ");
            return this;
        }
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        return this;
    }

    public QueryBuilder insert(String table, String... columns) {
        query.append("INSERT INTO ").append(table).append(" (");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");
        for (int i = 0; i < columns.length; i++) {
            query.append("?");
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(")");
        return this;
    }

    public QueryBuilder update(String table) {
        query.append("UPDATE ").append(table).append(" SET ");
        return this;
    }

    public QueryBuilder delete() {
        query.append("DELETE");
        return this;
    }

    public QueryBuilder from(String table) {
        query.append(" FROM ").append(table);
        return this;
    }

    public QueryBuilder set(String column) {
        query.append(column).append(" = ?");
        return this;
    }

    public QueryBuilder where(String column, String operator) {
        query.append(" WHERE ")
                .append(column)
                .append(" ")
                .append(operator)
                .append(" ?");
        return this;
    }

    public PreparedStatement build() throws SQLException {
        stmt = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
        return stmt;
    }
}
