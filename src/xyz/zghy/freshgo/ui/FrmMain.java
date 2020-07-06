package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ghy
 * @date 2020/7/4 上午8:25
 */
public class FrmMain extends JFrame implements ActionListener {
    private JPanel statusBar = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuAdmin = new JMenu("管理员维护");
    private JMenuItem freshManage = new JMenuItem("生鲜类型管理");
    private JMenuItem goodsManage = new JMenuItem("商品管理");
    private JMenuItem menuManage = new JMenuItem("菜谱管理");
    private JMenuItem couponManage = new JMenuItem("优惠券管理");
    private JMenuItem fullDiscountManage = new JMenuItem("满折信息管理");
    private JMenuItem limitTimeManage = new JMenuItem("限时促销管理");

    private FrmLogin Login = null;

    public FrmMain() {
        this.setExtendedState(FrmMain.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超");
        Login = new FrmLogin(this, "登录", true);
        Login.setVisible(true);


        this.menuAdmin.add(this.freshManage);
        this.freshManage.addActionListener(this);
        this.menuAdmin.add(this.goodsManage);
        this.goodsManage.addActionListener(this);
        this.menuAdmin.add(this.menuManage);
        this.menuManage.addActionListener(this);
        this.menuAdmin.add(this.couponManage);
        this.couponManage.addActionListener(this);
        this.menuAdmin.add(this.fullDiscountManage);
        this.fullDiscountManage.addActionListener(this);
        this.menuAdmin.add(limitTimeManage);
        this.limitTimeManage.addActionListener(this);

        menuBar.add(menuAdmin);

        this.setJMenuBar(menuBar);


        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = null;
        if ("管理员".equals(SystemUtil.currentLoginType)) {
            label = new JLabel("您好!   尊敬的管理员:" + SystemUtil.currentAdmin.getAdminName());
        }
        else {
            label = new JLabel("您好! " + SystemUtil.currentUser.getUserName());

        }
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.freshManage){
            FrmFreshType dlg = new FrmFreshType(this,"生鲜类型管理",true);
            dlg.setVisible(true);
        }
    }
}
