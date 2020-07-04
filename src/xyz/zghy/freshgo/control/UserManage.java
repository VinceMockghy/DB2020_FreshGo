package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanAdmin;
import xyz.zghy.freshgo.model.BeanUsers;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;

import java.sql.*;

/**
 * @author ghy
 * @date 2020/7/4 ����9:55
 */
public class UserManage {
    public BeanUsers register(String userName, String userPwd, String userPwd2
            , String userSex, int userPhone, String userCity, String userEmail) throws BusinessException {
        //TODO �û�ע��
        if (userName.length() <= 0 || userName.length() >= 20) {
            throw new BusinessException("�û���Ӧ����1����20���ַ�֮�䣡");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 20) {
            throw new BusinessException("����Ӧ����1����20���ַ�֮�䣡");
        }
        if (!userPwd.equals(userPwd2)) {
            throw new BusinessException("������������벻һ��");
        }

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from users where u_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("�˺��Դ��ڣ�������û���");
            }
            rs.close();
            pst.close();

            int insertUserId = 1;
            sql = "select max(u_id) from users ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                insertUserId = rs.getInt(1) + 1;
            } else {
                insertUserId = 1;
            }
            rs.close();
            pst.close();

            sql = "insert into users(u_id,u_name,u_sex,u_pwd,u_phone,u_email,u_city,u_createdate,u_isvip) values(?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, insertUserId);
            pst.setString(2, userName);
            pst.setString(3, userSex);
            pst.setString(4, userPwd);
            pst.setInt(5, userPhone);
            pst.setString(6, userEmail);
            pst.setString(7, userCity);
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setString(9,"��");
            if (pst.executeUpdate() == 1) {
                System.out.println("��ӳɹ�");
            } else {
                throw new BusinessException("�������ʧ��");
            }
            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }


        return null;
    }

    public BeanUsers login(String userName, String userPwd) throws BusinessException {
        //TODO �û���¼
        if (userName.length() <= 0 || userName.length() >= 20) {
            throw new BusinessException("�û���Ӧ����1����20���ַ�֮�䣡");
        }
        if (userPwd.length() <= 0 || userPwd.length() >= 20) {
            throw new BusinessException("����Ӧ����1����20���ַ�֮�䣡");
        }

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select u_pwd from users where u_name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString(1).equals(userPwd)) {
                    rs.close();
                    pst.close();
                    BeanUsers res = new BeanUsers();
                    res.setUserName(userName);
                    return res;
                } else {
                    throw new BusinessException("�������");
                }
            } else {
                throw new BusinessException("�˺Ų�����");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
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
