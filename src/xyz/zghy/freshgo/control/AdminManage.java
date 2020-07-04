package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ghy
 * @date 2020/7/4 ����10:03
 */
public class AdminManage {
    public BeanAdmin login(String adminName, String adminPwd) throws BaseException {
        //TODO ����Ա��¼
        if (adminName.length() <= 0 || adminName.length() >= 20) {
            throw new BusinessException("�û���Ӧ����1����20���ַ�֮�䣡");
        }
        if (adminPwd.length() <= 0 || adminPwd.length() >= 20) {
            throw new BusinessException("����Ӧ����1����20���ַ�֮�䣡");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select a_pwd from admin where a_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, adminName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(adminPwd)) {
                    BeanAdmin res = new BeanAdmin();
                    res.setAdminName(adminName);
                    return res;
                }
                else {
                    throw new BusinessException("�������");
                }
            } else {
                throw new BusinessException("�˺Ų�����");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
