package com.ulpay.testplatform.utils;

import com.mysql.jdbc.*;
import com.ulpay.testplatform.domain.TOutbaseConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接数据库支持的库
 * u1.测试业务库
 * u2.测试基础支付库
 * u3.测试共享查询库
 * l1.联调业务库
 * l2.联调基础支付库
 * l3.联调共享查询库
 */
@Component
public class DBConnectUtils {
    private static Logger logger = LoggerFactory.getLogger(DBConnectUtils.class);

    private Connection getConn(TOutbaseConf outbaseConf) throws Exception{
        switch (outbaseConf.getBaseType()){
            case 1 :
                Class.forName("oracle.jdbc.OracleDriver"); return DriverManager.getConnection("jdbc:oracle:thin:@//"+outbaseConf.getBaseUrl()+":"+outbaseConf.getBasePort()+"/"+outbaseConf.getBaseServicename(),outbaseConf.getBaseUsername(),outbaseConf.getBasePassword());
            case 2 :
                logger.info("获取mysql数据库链接");
                return null;
            default:
                return null;
        }
    }

    /**
     * 调用存储过程
     *
     * @param args
     * @throws Exception
     */
    public void callSeq(String[] args,TOutbaseConf outbaseConf) throws Exception {
        Connection db2conn = getConn(outbaseConf);
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

    public Map<String, String> queryOne(String sql, TOutbaseConf outbaseConf) {
        return query(sql, outbaseConf).get(0);
    }

    public List<Map<String, String>> query(String sql, TOutbaseConf outbaseConf) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConn(outbaseConf);
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
    public int excuteUpdate(String sql,TOutbaseConf outbaseConf) {
        Connection db2conn = null;
        Statement st = null;
        int result = 0;
        try {
            db2conn = getConn(outbaseConf);
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

