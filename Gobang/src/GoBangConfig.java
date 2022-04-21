import javax.swing.*;
import java.awt.*;

public interface GoBangConfig {
    int x = 20,y = 20,size = 40;
    int scrwidth = 1265,scrhighth = 785;
    ImageIcon newgame = new ImageIcon("pic/newgame.png");
    ImageIcon rules = new ImageIcon("pic/rules.jpg");
    Image black = new ImageIcon("pic/black.png").getImage();
    Image white = new ImageIcon("pic/white.png").getImage();
    Image login0 = new ImageIcon("pic/login.png").getImage();
}
