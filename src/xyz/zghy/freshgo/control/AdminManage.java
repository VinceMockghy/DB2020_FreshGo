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
 * @date 2020/7/4 上午10:03
 */
public class AdminManage {
    /**
     * 这个函数用于管理员账户的登录
     * @param adminName
     * @param adminPwd
     * @return
     * @throws BaseException
     */
    public BeanAdmin login(String adminName, String adminPwd) throws BaseException {
        if (adminName.length() <= 0 || adminName.length() >= 20) {
            throw new BusinessException("用户名应该在1——20个字符之间！");
        }
        if (adminPwd.length() <= 0 || adminPwd.length() >= 20) {
            throw new BusinessException("密码应该在1——20个字符之间！");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where a_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, adminName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString(3).equals(adminPwd)) {
                    BeanAdmin res = new BeanAdmin();
                    res.setAdminId(rs.getInt(1));
                    res.setAdminName(rs.getString(2));
                    res.setAdminPwd(rs.getString(3));
                    rs.close();
                    pst.close();
                    return res;
                }
                else {
                    throw new BusinessException("密码错误");
                }
            } else {
                throw new BusinessException("账号不存在");
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
