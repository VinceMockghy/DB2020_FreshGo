package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.UserManage;
import xyz.zghy.freshgo.model.BeanUsers;
import xyz.zghy.freshgo.model.CurrentLogin;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author ghy
 * @date 2020/7/4 ����1:02
 */
public class FrmRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("ע��");
    private Button btnCancel = new Button("ȡ��");

    private JLabel labelUser = new JLabel("�û���:");
    private JLabel labelPwd = new JLabel("����:");
    private JLabel labelPwd2 = new JLabel("ȷ������:");
    private JLabel labelSex = new JLabel("�Ա�:");
    private JLabel labelPhone = new JLabel("�绰����:");
    private JLabel labelCity = new JLabel("���ڳ���:");
    private JLabel lableEmail = new JLabel("����:");

    private JTextField editUser = new JTextField(20);
    private JPasswordField editPwd = new JPasswordField(20);
    private JPasswordField editPwd2 = new JPasswordField(20);
    private JRadioButton jr1 = new JRadioButton("��");
    private JRadioButton jr2 = new JRadioButton("Ů");
    private JTextField editPhone = new JTextField(11);
    private JTextField editCity = new JTextField(20);
    private JTextField editEmail = new JTextField(20);

    private String SexType = null;

    private GroupLayout glMain = new GroupLayout(workPane);
    private GroupLayout glUser = new GroupLayout(workPane);
    private GroupLayout glPwd = new GroupLayout(workPane);
    private GroupLayout glPwd2 = new GroupLayout(workPane);
    private GroupLayout glSex = new GroupLayout(workPane);
    private GroupLayout glPhone = new GroupLayout(workPane);
    private GroupLayout glCity = new GroupLayout(workPane);
    private GroupLayout glEmail = new GroupLayout(workPane);


    public FrmRegister(Dialog owner,String title,boolean modal){
        super(owner,title,modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(this.btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        GroupLayout.SequentialGroup sequentialGroupUser = glUser.createSequentialGroup();
        sequentialGroupUser.addComponent(labelUser);
        sequentialGroupUser.addComponent(editUser);
        GroupLayout.SequentialGroup sequentialGroupPwd = glPwd.createSequentialGroup();
        sequentialGroupPwd.addComponent(labelPwd);
        sequentialGroupPwd.addComponent(editPwd);
        GroupLayout.SequentialGroup sequentialGroupPwd2 = glPwd2.createSequentialGroup();
        sequentialGroupPwd2.addComponent(labelPwd2);
        sequentialGroupPwd2.addComponent(editPwd2);
        ButtonGroup rbtGroup = new ButtonGroup();
        rbtGroup.add(jr1);
        rbtGroup.add(jr2);
        GroupLayout.SequentialGroup sequentialGroupSex = glSex.createSequentialGroup();
        sequentialGroupSex.addComponent(labelSex);
        sequentialGroupSex.addComponent(jr1);
        sequentialGroupSex.addComponent(jr2);
        GroupLayout.SequentialGroup sequentialGroupPhone = glPhone.createSequentialGroup();
        sequentialGroupPhone.addComponent(labelPhone);
        sequentialGroupPhone.addComponent(editPhone);
        GroupLayout.SequentialGroup sequentialGroupCity = glCity.createSequentialGroup();
        sequentialGroupCity.addComponent(labelCity);
        sequentialGroupCity.addComponent(editCity);
        GroupLayout.SequentialGroup sequentialGroupEmail = glEmail.createSequentialGroup();
        sequentialGroupEmail.addComponent(lableEmail);
        sequentialGroupEmail.addComponent(editEmail);
        GroupLayout.ParallelGroup parallelGroupMain = glMain.createParallelGroup();
        parallelGroupMain.addGroup(sequentialGroupUser);
        parallelGroupMain.addGroup(sequentialGroupPwd);
        parallelGroupMain.addGroup(sequentialGroupPwd2);
        parallelGroupMain.addGroup(sequentialGroupSex);
        parallelGroupMain.addGroup(sequentialGroupPhone);
        parallelGroupMain.addGroup(sequentialGroupCity);
        parallelGroupMain.addGroup(sequentialGroupEmail);


        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(305, 300);
        // ��Ļ������ʾ
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
        jr1.addActionListener(this);
        jr2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource()==this.jr1){
            this.SexType="��";
        }
        else if(e.getSource()==this.jr2){
            this.SexType = "Ů";
        }
        else if(e.getSource()==this.btnOk){
            //TODO �û�ע��
            String userName = editUser.getText();
            String userPwd = new String(editPwd.getPassword());
            String userPwd2 = new String(editPwd2.getPassword());
            String userSex = this.SexType;
            int userPhone = Integer.parseInt(editPhone.getText());
            String userCity = editCity.getText();
            String userEmail = editEmail.getText();
            try {
                SystemUtil.userManage.register(userName,userPwd,userPwd2,userSex,userPhone,userCity,userEmail);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }

    }
}
