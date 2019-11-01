package com.interfacetest.commonutils;

import com.interfacetest.constants.DBServerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接数据库支持的库
 * 1、payadm
 * 2、gwadm
 * 3、riskadm
 * 4、actadm
 * 5、baseadm
 */
public class DBConnectUtils {
    private static Logger logger = LoggerFactory.getLogger(DBConnectUtils.class);

    private synchronized static Connection getConnPayadm() throws Exception {//核心
        Class.forName(DBServerName.DRIVER);
        return DriverManager.getConnection(DBServerName.JDBCURL_READONLY, DBServerName.PAYADM, DBServerName.PAYADM_PWD);
    }

    private synchronized static Connection getConnGwadm() throws Exception {//通道
        Class.forName(DBServerName.DRIVER);
        return DriverManager.getConnection(DBServerName.JDBCURL_READONLY, DBServerName.GWADM, DBServerName.GWADM_PWD);
    }

    private synchronized static Connection getConnRiskadm() throws Exception {//风控
        Class.forName(DBServerName.DRIVER);
        return DriverManager.getConnection(DBServerName.JDBCURL, DBServerName.RISKADM, DBServerName.RISKADM_PWD);
    }

    private synchronized static Connection getConnActadm() throws Exception {//账户
        Class.forName(DBServerName.DRIVER);
        return DriverManager.getConnection(DBServerName.JDBCURL, DBServerName.ACTADM, DBServerName.ACTADM_PWD);
    }

    private synchronized static Connection getConnBaseadm() throws Exception {//账户
        Class.forName(DBServerName.DRIVER);
       return DriverManager.getConnection(DBServerName.JDBCURL_READONLY, DBServerName.BASEADM, DBServerName.BASEADM_PWD);
    }

    private synchronized static Connection getConnTestdba() throws Exception {//账户
        Class.forName(DBServerName.DRIVER);
        return DriverManager.getConnection(DBServerName.JDBCURL_READONLY, DBServerName.TESTDBA, DBServerName.TESTDBA_PWD);
    }

    private static Connection getConn(String dbFlag) throws Exception {
        if (dbFlag.equalsIgnoreCase(DBServerName.PAYADM)) {
            return getConnPayadm();
        } else if (dbFlag.equalsIgnoreCase(DBServerName.GWADM)) {
            return getConnGwadm();
        } else if (dbFlag.equalsIgnoreCase(DBServerName.RISKADM)) {
            return getConnRiskadm();
        } else if (dbFlag.equalsIgnoreCase(DBServerName.ACTADM)) {
            return getConnActadm();
        } else if (dbFlag.equalsIgnoreCase(DBServerName.BASEADM)) {
            return getConnBaseadm();
        } else if (dbFlag.equalsIgnoreCase(DBServerName.TESTDBA)) {
            return getConnTestdba();
        } else {
            throw new Exception("dbFlag 错误！");
        }
    }

    /**
     * 调用存储过程
     *
     * @param args
     * @throws Exception
     */
    public static void callSeq(String[] args) throws Exception {
        Connection db2conn = getConn(null);
        db2conn.setAutoCommit(false);
        String sql = "{call mobile.getseq(?,?)}";//{call mobile.getseq(?,?)}
        CallableStatement cstmt = db2conn.prepareCall(sql);
        cstmt.setString(1, "mmoney");
        cstmt.registerOutParameter(2, Types.BIGINT);
        cstmt.execute();
        //cstmt.execute(); // 执行存储过程调用
        Integer rs = (Integer) cstmt.getInt(2);
        logger.info(rs.toString());
        cstmt.close();
        db2conn.close();
    }

    public static Map<String, String> queryOne(String sql, String mainDbFlag) {
        return query(sql, mainDbFlag).get(0);
    }

    public static List<Map<String, String>> query(String sql, String dbFlag) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConn(dbFlag);
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = 0;
            while (rs.next()) {
                count++;
                if (count > 100) {
                    logger.info("数据库查出数据量过多，只返回前100条");
                    return result;
                }
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i) == null ? "" : rs.getObject(i).toString());
                }
                result.add(map);
            }
            logger.info("查询出" + count + "条数据");
        } catch (SQLException e) {
            logger.info("测试出现SQL异常，异常信息如下：" + e.getMessage());
        } catch (Exception e) {
            logger.info("连接数据库失败！！\n{}", e.getMessage());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // update 功能
    public static int excuteUpdate(String sql) {
        Connection db2conn = null;
        Statement st = null;
        int result = 0;
        try {
            db2conn = getConn("testdba");
            st = db2conn.createStatement();
            result = st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                db2conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

