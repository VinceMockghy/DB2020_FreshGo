package xyz.zghy.freshgo.control;

import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.DBUtil;
import xyz.zghy.freshgo.util.SystemUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/10 上午10:40
 */
public class OrderDetailsManage {
    /**
     * 这个函数用来没有使用优惠之前的原始价格总和//            // TODO 后面订单部分使用了地址而无法删除
//            String sql = "";
//            PreparedStatement pst = null;
//            ResultSet rs = null;
     * @return
     * @throws BusinessException
     */
    public double getOldPrice() throws BusinessException {
        double oldPrice = 0;
        List<BeanOrderDetail> details = SystemUtil.globalOrderDetails;
        Connection conn = null;

        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;
            for (BeanOrderDetail detail : details) {
                int ownGoodsCount = 0;
                sql = "select g_count from goods_msg where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    ownGoodsCount = rs.getInt(1);
                    if (rs.getInt(1) < detail.getGoodsCount()) {
                        throw new BusinessException("商品库存不足，请重新选择商品");
                    }
                } else {
                    throw new BusinessException("商品不存在");
                }
                rs.close();
                pst.close();
                sql = "update goods_msg set g_count =? where g_id=?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, (ownGoodsCount - detail.getGoodsCount()));
                pst.setInt(2, detail.getGoodsId());
                if (pst.executeUpdate() == 1) {
                    System.out.println("商品数量更新成功");
                } else {
                    throw new BusinessException("更新异常");
                }
                rs.close();
                pst.close();
                conn.commit();
                oldPrice += detail.getGoodsPrice() * detail.getGoodsCount();
            }

        } catch (SQLException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return oldPrice;
    }

    /**
     * 这个函数用来计算使用了限时促销券或者满折券后的新价格
     * @param orderId
     * @return
     * @throws BusinessException
     */
    public double getNewPrice(int orderId) throws BusinessException {
        double newPrice = 0;
        List<BeanOrderDetail> details = SystemUtil.globalOrderDetails;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "";
            PreparedStatement pst = null;
            ResultSet rs = null;


            for (BeanOrderDetail detail : details) {
                sql = "select ltp_price,ltp_count,ltp_start_date,ltp_end_date from LTpromotion  where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (SystemUtil.SDF.parse(rs.getString(3)).getTime() < System.currentTimeMillis() && SystemUtil.SDF.parse(rs.getString(4)).getTime() > System.currentTimeMillis()) {
                        if (rs.getInt(2) > detail.getGoodsCount()) {
                            double simplePrice = rs.getDouble(1);
                            newPrice += rs.getDouble(1) * detail.getGoodsCount();
                            rs.close();
                            pst.close();
                            sql = "insert into orders_detail(goods_id,goods_count,goods_price,order_id) " +
                                    "values(?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, detail.getGoodsId());
                            pst.setInt(2, detail.getGoodsCount());
                            pst.setDouble(3, simplePrice);
                            pst.setInt(4, orderId);
                            if (pst.executeUpdate() == 1) {
                                System.out.println("订单数据插入成功");
                            } else {
                                throw new BusinessException("数据插入异常");
                            }
                            rs.close();
                            pst.close();
                        } else {
                            double realPrice = newPrice;
                            newPrice += rs.getInt(1) * rs.getInt(2);
                            newPrice += detail.getGoodsPrice() * (detail.getGoodsCount() - rs.getInt(2));
                            realPrice = (newPrice - realPrice) / detail.getGoodsCount();
                            rs.close();
                            pst.close();
                            sql = "insert into orders_detail(goods_id,goods_count,goods_price,order_id) " +
                                    "values(?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setInt(1, detail.getGoodsId());
                            pst.setInt(2, detail.getGoodsCount());
                            pst.setDouble(3, realPrice);
                            pst.setInt(4, orderId);
                            if (pst.executeUpdate() == 1) {
                                System.out.println("订单数据插入成功");
                            } else {
                                throw new BusinessException("数据插入异常");
                            }
                            rs.close();
                            pst.close();
                        }
                    }
                    continue;
                }
                rs.close();
                pst.close();


                //TODO 创建视图 判断当前数据是否满足满折优惠券 满折的话需要加上满折信息与id，后期更新details
                sql = "select fd_id,fd_needcount,fd_data,start_date,end_date from fulldiscountandgoodsmsg where g_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(2) <= detail.getGoodsCount() &&
                            SystemUtil.SDF.parse(rs.getString(4)).getTime() < System.currentTimeMillis() &&
                            SystemUtil.SDF.parse(rs.getString(5)).getTime() > System.currentTimeMillis()) {
                        System.out.println(rs.getDouble(3));
                        newPrice += detail.getGoodsPrice() * detail.getGoodsCount() * rs.getDouble(3)/10;
                        double discount = rs.getDouble(3)/10;
                        int fulldID=rs.getInt(1);
                        rs.close();
                        pst.close();
                        sql = "insert into orders_detail(goods_id,goods_count,goods_price,order_id,fd_data,fulld_id)" +
                                " values(?,?,?,?,?,?)";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, detail.getGoodsId());
                        pst.setInt(2, detail.getGoodsCount());
                        pst.setDouble(3, detail.getGoodsPrice()*discount);
                        pst.setInt(4, orderId);
                        pst.setDouble(5,discount*10);
                        pst.setInt(6,fulldID);
                        if (pst.executeUpdate() == 1) {
                            System.out.println("使用满折成功");
                        }
                        continue;
                    }
                }


                newPrice += detail.getGoodsPrice() * detail.getGoodsCount();
                sql = "insert into orders_detail(goods_id,goods_count,goods_price,order_id) " +
                        "values(?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, detail.getGoodsId());
                pst.setInt(2, detail.getGoodsCount());
                pst.setDouble(3, detail.getGoodsPrice());
                pst.setInt(4, orderId);
                if (pst.executeUpdate() == 1) {
                    System.out.println("订单数据插入成功");
                } else {
                    throw new BusinessException("数据插入异常");
                }
                rs.close();
                pst.close();

            }
            conn.commit();
        } catch (SQLException | ParseException throwables) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return newPrice;
    }

    /**
     * 这个函数用来根据订单Id加载订单的商品内容
     * @param o_id
     * @return
     */
    public List<BeanOrderDetail> loadOrderDetails(int o_id) {
        System.out.println("---"+o_id);
        Connection conn = null;
        List<BeanOrderDetail> res = new ArrayList<BeanOrderDetail>();

        try {
            conn = DBUtil.getConnection();
            String sql = "select goods_id,goods_count from orders_detail where order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, o_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanOrderDetail bod = new BeanOrderDetail();
                bod.setGoodsId(rs.getInt(1));
                bod.setGoodsCount(rs.getInt(2));
                res.add(bod);
            }
            rs.close();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }


        return res;
    }
}
