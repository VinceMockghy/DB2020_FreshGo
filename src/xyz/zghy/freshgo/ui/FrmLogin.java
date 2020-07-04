package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.AdminManage;
import xyz.zghy.freshgo.control.UserManage;
import xyz.zghy.freshgo.model.CurrentLogin;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ghy
 * @date 2020/7/4 ����8:28
 */
public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnUserReRegister = new Button("�û�ע��");
    private Button btnLogin = new Button("��½");
    private Button btnCancel = new Button("�˳�");
    private JLabel labelUser = new JLabel("�û���");
    private JLabel labelPwd = new JLabel("���룺");
    private JTextField edtUserName = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JRadioButton jr1 = new JRadioButton("�û�", true);
    private JRadioButton jr2 = new JRadioButton("����Ա");
    private GroupLayout glMain = new GroupLayout(workPane);
    private GroupLayout glRadioButton = new GroupLayout(workPane);
    private GroupLayout glUser = new GroupLayout(workPane);
    private GroupLayout glPwd = new GroupLayout(workPane);
    private String LoginAccountType = "�û�";


    public FrmLogin(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnUserReRegister);
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout.SequentialGroup seqGroupUser = glUser.createSequentialGroup();
        seqGroupUser.addComponent(labelUser);
        seqGroupUser.addComponent(edtUserName);
        GroupLayout.SequentialGroup seqGroupPwd = glPwd.createSequentialGroup();
        seqGroupPwd.addComponent(labelPwd);
        seqGroupPwd.addComponent(edtPwd);
        ButtonGroup rbtgroup = new ButtonGroup();
        rbtgroup.add(jr1);
        rbtgroup.add(jr2);
        GroupLayout.SequentialGroup seqRadioButton = glRadioButton.createSequentialGroup();
        seqGroupPwd.addComponent(jr1);
        seqGroupPwd.addComponent(jr2);
        GroupLayout.ParallelGroup paralGroupMain = glMain.createParallelGroup();
        paralGroupMain.addGroup(seqGroupUser);
        paralGroupMain.addGroup(seqGroupPwd);
        paralGroupMain.addGroup(seqRadioButton);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 160);
        // ��Ļ������ʾ
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        btnUserReRegister.addActionListener(this);
        jr1.addActionListener(this);
        jr2.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jr1) {
            this.LoginAccountType = "�û�";
        } else if (e.getSource() == this.jr2) {
            this.LoginAccountType = "����Ա";
        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        } else if (e.getSource() == this.btnLogin) {
            //TODO �û��͹���Ա��¼
            String loginName = this.edtUserName.getText();
            String loginPwd = new String(this.edtPwd.getPassword());
            try {
                if ("�û�".equals(this.LoginAccountType)) {
                    SystemUtil.currentLoginType="�û�";
                    SystemUtil.currentUser = SystemUtil.userManage.login(loginName,loginPwd);
                    System.out.println("�û���¼�ɹ�");
                } else if ("����Ա".equals(this.LoginAccountType)) {
                    SystemUtil.currentLoginType="����Ա";
                    SystemUtil.currentAdmin= SystemUtil.adminManage.login(loginName,loginPwd);
                    System.out.println("����Ա��¼�ɹ�");
                } else {
                    throw new BusinessException("���ʳ���");
                }
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.btnUserReRegister) {
            //TODO �û�ע�� ��Ҫע��ui
//            System.out.println(111);
            FrmRegister Register = new FrmRegister(this,"ע��",true);
            Register.setVisible(true);
        }
    }
}
