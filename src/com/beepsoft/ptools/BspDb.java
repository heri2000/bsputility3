/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heri
 */
public class BspDb {

    public static final int DRIVER_TYPE_MYSQL = 1;
    public static final int DRIVER_TYPE_POSTGRES = 2;
    public static final int DRIVER_TYPE_SQLITE = 3;

    public static final String LOCAL_HOST = "localhost";

    // Database configuration for MySQL
    public static final int MYSQL_DEFAULT_PORT = 3306;
    public static final String MYSQL_DEFAULT_DATABASE = "mysql";
    public static final String MYSQL_USER_NAME = "root";
    public static final String MYSQL_PWD = "";
    public static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String MYSQL_DRIVER_STRING = "jdbc:mysql";

    // Database configuration for PostgreSQL
    public static final int POSTGRES_DEFAULT_PORT = 5432;
    public static final String POSTGRES_DEFAULT_DATABASE = "postgres";
    public static final String POSTGRES_USER_NAME = "postgres";
    public static final String POSTGRES_PWD = "postgres";
    public static final String POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String POSTGRES_DRIVER_STRING = "jdbc:postgresql";

    // Database configuration for SQLite
    public static final int SQLITE_DEFAULT_PORT = 0;
    public static final String SQLITE_DEFAULT_DATABASE = "srdefdb.db";
    public static final String SQLITE_USER_NAME = "";
    public static final String SQLITE_PWD = "";
    public static final String SQLITE_DRIVER_CLASS = "org.sqlite.JDBC";
    public static final String SQLITE_DRIVER_STRING = "jdbc:sqlite";

    private boolean connected;
    private String user;
    private String password;
    private String driver_string;
    private String address;
    private int port;
    private String database;
    private Connection conn;
    private Statement statement;
    private ResultSet rset;
    private boolean driver_loaded = false;
    private int driver_type;
    private boolean executing = false;
    private int lastInsertId;

    public boolean interrupt = false;

    public BspDb(String driver_class, String driver_string, int driver_type)
            throws ClassNotFoundException {
        this.driver_string = driver_string;
        this.driver_type = driver_type;

        connected = false;
        address = LOCAL_HOST;
        port = 0;
        database = "";

        Class.forName(driver_class);
        driver_loaded = true;
    }

    public boolean closeConnection() {
        if (conn == null) {
            connected = false;
            return true;
        }
        try {
            conn.close();
            connected = false;
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean closeQuery() {
        if (statement == null) {
            executing = false;
            return true;
        }
        try {
            statement.close();
            executing = false;
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean commit() {
        try {
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean executeQuery(String sql) {
        if (executing) {
            int wait = 0;
            do {
                try {
                    wait++;
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (executing && wait < 5);
            if (executing) {
                return false;
            }
        }
        executing = true;

        try {
            statement = conn.createStatement();
            rset = statement.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            executing = false;
            return false;
        }
        return true;
    }

    public int executeUpdate(String sql) {
        if (executing) {
            int wait = 0;
            do {
                try {
                    wait++;
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (executing && wait < 5);
            if (executing) {
                return -1;
            }
        }
        executing = true;

        int result = 0;
        try {
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
            
            sql = "SELECT last_insert_id()";
            rset = statement.executeQuery(sql);
            if (rset.next()) {
                lastInsertId = rset.getInt(1);
            }
            
            statement.close();
            executing = false;
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            executing = false;
            return -1;
        }
        return result;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isDriverLoaded() {
        return driver_loaded;
    }

    public String getAddress() {
        return address;
    }

    public byte getByte(int col) {
        try {
            return rset.getByte(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public byte getByte(String col) {
        try {
            return rset.getByte(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public char getChar(int col) {
        try {
            String temp_str = rset.getString(col);
            if (temp_str == null) {
                return ' ';
            }
            if (temp_str.length() > 0) {
                return temp_str.charAt(0);
            } else {
                return ' ';
            }
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return ' ';
        }
    }

    public char getChar(String col) {
        try {
            String temp_str = rset.getString(col);
            if (temp_str == null) {
                return ' ';
            }
            if (temp_str.length() > 0) {
                return temp_str.charAt(0);
            } else {
                return ' ';
            }
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return ' ';
        }
    }

    public double getDouble(int col) {
        try {
            return rset.getDouble(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public double getDouble(String col) {
        try {
            return rset.getDouble(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getColumnCount() {
        int count = -1;
        try {
            count = rset.getMetaData().getColumnCount();
        } catch (SQLException e) {
        }
        return count;
    }

    public Connection getConn() {
        return conn;
    }

    public int getColumnType(int i) {
        int type = 0;
        try {
            type = rset.getMetaData().getColumnType(i);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return type;
    }

    public String getColumnTypeName(int i) {
        String type = "";
        try {
            type = rset.getMetaData().getColumnTypeName(i);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return type;
    }

    public String getDatabase() {
        return database;
    }

    public int getDriverType() {
        return driver_type;
    }
    
    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public int getPort() {
        return port;
    }

    public int getInt(int col) {
        try {
            return rset.getInt(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getInt(String col) {
        try {
            return rset.getInt(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getRowCount() {
        int temp = -1;

        try {
            rset.last();
            temp = rset.getRow();
            rset.beforeFirst();
        } catch (Exception e) {
            temp = 0;
        }
        return temp;
    }

    public float getFloat(int col) {
        try {
            return rset.getFloat(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public float getFloat(String col) {
        try {
            return rset.getFloat(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getLastInsertId_1() {
        return lastInsertId;
    }

    public long getLastInsertID() {
        long last_insert_id = 0;

        if (driver_type == DRIVER_TYPE_MYSQL) {
            if (executeQuery("LAST_INSERT_ID()")) {
                if (next()) {
                    last_insert_id = getLong(1);
                }
            }
        } else if (driver_type == DRIVER_TYPE_SQLITE) {
            if (executeQuery("sqlite_last_insert_rowid()")) {
                if (next()) {
                    last_insert_id = getLong(1);
                }
            }
        }

        return last_insert_id;
    }

    public long getLong(int col) {
        try {
            return rset.getLong(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public long getLong(String col) {
        try {
            return rset.getLong(col);
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public ResultSet getResultSet() {
        return rset;
    }

    public String getString(int col) {
        try {
            String temp_str = rset.getString(col);
            if (temp_str == null) {
                return "";
            }
            return temp_str;
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public String getString(String col) {
        try {
            String temp_str = rset.getString(col);
            if (temp_str == null) {
                return "";
            }
            return temp_str;
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public boolean next() {
        boolean result = false;
        try {
            result = rset.next();
        } catch (SQLException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return result;
    }

    public boolean openConnection() {
        if (connected) {
            interrupt = true;
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            interrupt = false;
            if (connected) {
                return false;
            }
        }
        try {
            String url = driver_string;
            if (driver_type == DRIVER_TYPE_MYSQL
                    || driver_type == DRIVER_TYPE_POSTGRES) {
                url += "://" + address;
                if (port != 0) {
                    url += ":" + port;
                }
                url += "/" + database;
                conn = DriverManager.getConnection(url, user, password);
            } else if (driver_type == DRIVER_TYPE_SQLITE) {
                url += ":" + database;
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            connected = false;
            return false;
        }
        if (conn != null) {
            connected = true;
        }
        return true;
    }

    public boolean rollBack() {
        try {
            if (!conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean setAutoCommit(boolean acmt) {
        try {
            conn.setAutoCommit(acmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setParameters(String address, int port, String database, String user_name, String pwd) {
        setAddress(address);
        setPort(port);
        setDatabase(database);
        setUser(user_name);
        setPassword(pwd);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void startTransaction() {
        setAutoCommit(false);
    }

}
