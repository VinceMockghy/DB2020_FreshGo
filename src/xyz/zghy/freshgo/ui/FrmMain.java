package xyz.zghy.freshgo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/4 ����8:25
 */
public class FrmMain extends JFrame implements ActionListener {

    private FrmLogin Login = null;

    public FrmMain(){
        this.setExtendedState(FrmMain.MAXIMIZED_BOTH);
        this.setTitle("��������");
        Login = new FrmLogin(this,"��¼",true);
        Login.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
