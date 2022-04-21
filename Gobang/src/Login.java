import javax.swing.*;
import java.awt.*;

public class Login extends JFrame implements GoBangConfig{
    public void init(){
        this.setTitle("Start Screen");
        this.setSize(scrwidth,scrhighth);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setLayout(null);
        JButton buttonLogin = new JButton("开始新游戏",newgame);
        buttonLogin.setBounds(500,580,300,80);
        this.add(buttonLogin);

        ButtonListener bu0 = new ButtonListener(this);
        buttonLogin.addActionListener(bu0);

        this.setVisible(true);
    }


    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(login0,0,0,this.getWidth(),this.getHeight(),this);
    }


}
