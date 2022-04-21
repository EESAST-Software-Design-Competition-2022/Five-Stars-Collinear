import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GoBangFrame extends JPanel implements GoBangConfig {
    public Graphics g;
    public int[][] isPiece = new int[15][15];//考虑坐标
    public int[][] theValue = new int[15][15];
    public ArrayList<ChessLocation> chessLocations = new ArrayList<>();
    public int turn = 0;//1为黑色，2为白色，0不许下
    public int mode = 0;//2为禁手，1为SWAP1,0为普通
    public int isRestart = 0;//是否可以重新开始,0为未开始，1为游戏进行中，2为游戏已结束。
    public int type = 0;//选择AI还是player
    public int AI_choose = 0;//0代表正常，1代表AI要交换

    public static HashMap<String,Integer> map0 = new HashMap<>();
    static {

        map0.put("01", 17);
        map0.put("02", 12);
        map0.put("001", 17);
        map0.put("002", 12);
        map0.put("0001", 17);
        map0.put("0002", 12);

        map0.put("0102", 17);
        map0.put("0201", 12);
        map0.put("0012", 15);
        map0.put("0021", 10);
        map0.put("01002", 19);
        map0.put("02001", 14);
        map0.put("00102", 17);
        map0.put("00201", 12);
        map0.put("00012", 15);
        map0.put("00021", 10);

        map0.put("01000", 21);
        map0.put("02000", 16);
        map0.put("00100", 19);
        map0.put("00200", 14);
        map0.put("00010", 17);
        map0.put("00020", 12);
        map0.put("00001", 15);
        map0.put("00002", 10);


        map0.put("0101", 65);
        map0.put("0202", 60);
        map0.put("0110", 65);
        map0.put("0220", 60);
        map0.put("011", 65);
        map0.put("022", 60);
        map0.put("0011", 65);
        map0.put("0022", 60);

        map0.put("01012", 65);
        map0.put("02021", 60);
        map0.put("01102", 65);
        map0.put("02201", 60);
        map0.put("00112", 65);
        map0.put("00221", 60);

        map0.put("01010", 75);
        map0.put("02020", 70);
        map0.put("01100", 75);
        map0.put("02200", 70);
        map0.put("00110", 75);
        map0.put("00220", 70);
        map0.put("00011", 75);
        map0.put("00022", 70);


        map0.put("0111", 150);
        map0.put("0222", 140);

        map0.put("01112", 150);
        map0.put("02221", 140);

        map0.put("01101", 1000);
        map0.put("02202", 800);
        map0.put("01011", 1000);
        map0.put("02022", 600);
        map0.put("01110", 1000);
        map0.put("02220", 800);

        map0.put("01111", 3000);
        map0.put("02222", 10000);
    }

    public static  HashMap<String,Integer> map1 = new HashMap<>();
    static {

        map1.put("02", 17);
        map1.put("01", 12);
        map1.put("002", 17);
        map1.put("001", 12);
        map1.put("0002", 17);
        map1.put("0001", 12);

        map1.put("0201", 17);
        map1.put("0102", 12);
        map1.put("0021", 15);
        map1.put("0012", 10);
        map1.put("02001", 19);
        map1.put("01002", 14);
        map1.put("00201", 17);
        map1.put("00102", 12);
        map1.put("00021", 15);
        map1.put("00012", 10);

        map1.put("02000", 21);
        map1.put("01000", 16);
        map1.put("00200", 19);
        map1.put("00100", 14);
        map1.put("00020", 17);
        map1.put("00010", 12);
        map1.put("00002", 15);
        map1.put("00001", 10);


        map1.put("0202", 65);
        map1.put("0101", 60);
        map1.put("0220", 65);
        map1.put("0110", 60);
        map1.put("022", 65);
        map1.put("011", 60);
        map1.put("0022", 65);
        map1.put("0011", 60);

        map1.put("02021", 65);
        map1.put("01012", 60);
        map1.put("02201", 65);
        map1.put("01102", 60);
        map1.put("00221", 65);
        map1.put("00112", 60);

        map1.put("02020", 75);
        map1.put("01010", 70);
        map1.put("02200", 75);
        map1.put("01100", 70);
        map1.put("00220", 75);
        map1.put("00110", 70);
        map1.put("00022", 75);
        map1.put("00011", 70);


        map1.put("0222", 150);
        map1.put("0111", 140);

        map1.put("02221", 150);
        map1.put("01112", 140);

        map1.put("02202", 1000);
        map1.put("01101", 800);
        map1.put("02022", 1000);
        map1.put("01011", 800);
        map1.put("02220", 1000);
        map1.put("01110", 800);

        map1.put("02222", 3000);
        map1.put("01111", 4000);
    }

    public static HashMap<String,Interger_pair> map2 = new HashMap<>();
    static {
        map2.put("01", new Interger_pair(17,1));
        map2.put("02", new Interger_pair(12,2));
        map2.put("001",new Interger_pair(17,1));
        map2.put("002", new Interger_pair(12,2));
        map2.put("0001", new Interger_pair(17,1));


        map2.put("0002", new Interger_pair(12,2));
        map2.put("0102", new Interger_pair(17,0));
        map2.put("0201", new Interger_pair(12,0));
        map2.put("0012", new Interger_pair(15,0));
        map2.put("0021", new Interger_pair(10,0));

        map2.put("01002", new Interger_pair(19,0));
        map2.put("02001", new Interger_pair(14,0));
        map2.put("00102", new Interger_pair(17,0));
        map2.put("00201", new Interger_pair(12,0));
        map2.put("00012", new Interger_pair(15,0));

        map2.put("00021", new Interger_pair(10,0));
        map2.put("01000", new Interger_pair(21,1));
        map2.put("02000", new Interger_pair(16,2));
        map2.put("00100", new Interger_pair(19,1));
        map2.put("00200", new Interger_pair(14,2));

        map2.put("00010", new Interger_pair(17,1));
        map2.put("00020", new Interger_pair(12,2));
        map2.put("00001", new Interger_pair(15,1));
        map2.put("00002", new Interger_pair(10,2));
        map2.put("0101", new Interger_pair(65,1));

        map2.put("0202", new Interger_pair(60,2));
        map2.put("0110", new Interger_pair(65,1));
        map2.put("0220", new Interger_pair(60,2));
        map2.put("011", new Interger_pair(65,1));
        map2.put("022", new Interger_pair(60,2));

        map2.put("0011", new Interger_pair(65,1));
        map2.put("0022", new Interger_pair(60,2));
        map2.put("01012", new Interger_pair(65,0));
        map2.put("02021", new Interger_pair(60,0));
        map2.put("01102", new Interger_pair(65,0));

        map2.put("02201", new Interger_pair(60,0));
        map2.put("00112", new Interger_pair(65,0));
        map2.put("00221", new Interger_pair(60,0));
        map2.put("01010", new Interger_pair(75,1));
        map2.put("02020", new Interger_pair(70,2));

        map2.put("01100", new Interger_pair(75,1));
        map2.put("02200", new Interger_pair(70,2));
        map2.put("00110", new Interger_pair(75,1));
        map2.put("00220", new Interger_pair(70,2));
        map2.put("00011", new Interger_pair(75,1));

        map2.put("00022", new Interger_pair(70,2));
        map2.put("0111", new Interger_pair(150,1));
        map2.put("0222", new Interger_pair(140,2));
        map2.put("01112", new Interger_pair(150,0));
        map2.put("02221", new Interger_pair(140,0));

        map2.put("01101", new Interger_pair(1000,1));
        map2.put("02202", new Interger_pair(800,2));
        map2.put("01011", new Interger_pair(1000,1));
        map2.put("02022", new Interger_pair(800,2));
        map2.put("01110", new Interger_pair(1000,1));

        map2.put("02220", new Interger_pair(800,2));
        map2.put("01111", new Interger_pair(3000,1));
        map2.put("02222", new Interger_pair(10000,2));
    }

    public static HashMap<String,Interger_pair> map3 = new HashMap<>();
    static {
        map3.put("02", new Interger_pair(17,1));
        map3.put("01", new Interger_pair(12,2));
        map3.put("002",new Interger_pair(17,1));
        map3.put("001", new Interger_pair(12,2));
        map3.put("0002", new Interger_pair(17,1));


        map3.put("0001", new Interger_pair(12,2));
        map3.put("0201", new Interger_pair(17,0));
        map3.put("0102", new Interger_pair(12,0));
        map3.put("0021", new Interger_pair(15,0));
        map3.put("0012", new Interger_pair(10,0));

        map3.put("02001", new Interger_pair(19,0));
        map3.put("01002", new Interger_pair(14,0));
        map3.put("00201", new Interger_pair(17,0));
        map3.put("00102", new Interger_pair(12,0));
        map3.put("00021", new Interger_pair(15,0));

        map3.put("00012", new Interger_pair(10,0));
        map3.put("02000", new Interger_pair(21,1));
        map3.put("01000", new Interger_pair(16,2));
        map3.put("00200", new Interger_pair(19,1));
        map3.put("00100", new Interger_pair(14,2));

        map3.put("00020", new Interger_pair(17,1));
        map3.put("00010", new Interger_pair(12,2));
        map3.put("00002", new Interger_pair(15,1));
        map3.put("00001", new Interger_pair(10,2));
        map3.put("0202", new Interger_pair(65,1));

        map3.put("0101", new Interger_pair(60,2));
        map3.put("0220", new Interger_pair(65,1));
        map3.put("0110", new Interger_pair(60,2));
        map3.put("022", new Interger_pair(65,1));
        map3.put("011", new Interger_pair(60,2));

        map3.put("0022", new Interger_pair(65,1));
        map3.put("0011", new Interger_pair(60,2));
        map3.put("02021", new Interger_pair(65,0));
        map3.put("01012", new Interger_pair(60,0));
        map3.put("02201", new Interger_pair(65,0));

        map3.put("01102", new Interger_pair(60,0));
        map3.put("00221", new Interger_pair(65,0));
        map3.put("00112", new Interger_pair(60,0));
        map3.put("02020", new Interger_pair(75,1));
        map3.put("01010", new Interger_pair(70,2));

        map3.put("02200", new Interger_pair(75,1));
        map3.put("01100", new Interger_pair(70,2));
        map3.put("00220", new Interger_pair(75,1));
        map3.put("00110", new Interger_pair(70,2));
        map3.put("00022", new Interger_pair(75,1));

        map3.put("00011", new Interger_pair(70,2));
        map3.put("0222", new Interger_pair(150,1));
        map3.put("0111", new Interger_pair(140,2));
        map3.put("02221", new Interger_pair(150,0));
        map3.put("01112", new Interger_pair(140,0));

        map3.put("02202", new Interger_pair(1000,1));
        map3.put("01101", new Interger_pair(800,2));
        map3.put("02022", new Interger_pair(1000,1));
        map3.put("01011", new Interger_pair(800,2));
        map3.put("02220", new Interger_pair(1000,1));

        map3.put("01110", new Interger_pair(800,2));
        map3.put("02222", new Interger_pair(3000,1));
        map3.put("01111", new Interger_pair(10000,2));
    }

    public static void main(String[] args){
        Login lg = new Login();
        lg.init();
    }

    public void init0(){
        JFrame jf = new JFrame();
        String Title = "GoBang";
        jf.setTitle(Title);
        jf.setSize(765,635);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        jf.setLayout(new BorderLayout());
        Dimension dim1=new Dimension(150,0);//设置右半部分的大小
        Dimension dim3=new Dimension(550,0);//设置左半部分的大小
        Dimension dim2=new Dimension(120,30);//设置右边按钮组件的大小
        setPreferredSize(dim3);
        setBackground(Color.DARK_GRAY);
        jf.add(this,BorderLayout.CENTER);

        JPanel jp = new JPanel();
        jp.setPreferredSize(dim1);
        jp.setBackground(Color.white);
        jf.add(jp,BorderLayout.EAST);//添加到框架布局的东边部分
        jp.setLayout(new FlowLayout());//设置JPanel为流式布局
        JButton begin = new JButton("开始游戏");
        begin.setPreferredSize(dim2);
        JButton pause = new JButton("暂停游戏");
        pause.setPreferredSize(dim2);
        JButton newgame = new JButton("重新开始");
        newgame.setPreferredSize(dim2);
        JButton repentance = new JButton("悔棋");
        repentance.setPreferredSize(dim2);
        JButton chess = new JButton("和棋");
        chess.setPreferredSize(dim2);
        JButton concede = new JButton("认输");
        concede.setPreferredSize(dim2);

        jp.add(begin);
        jp.add(pause);
        jp.add(newgame);
        jp.add(repentance);
        jp.add(concede);
        jp.add(chess);

        String[] choosetype = {"对战玩家","对战AI"};
        JComboBox<String> box = new JComboBox<>(choosetype);

        ButtonListener butListen=new ButtonListener(this,box);

        begin.addActionListener(butListen);
        pause.addActionListener(butListen);
        newgame.addActionListener(butListen);
        repentance.addActionListener(butListen);
        concede.addActionListener(butListen);
        box.addActionListener(butListen);
        chess.addActionListener(butListen);
        FrameListener fl = new FrameListener();
        fl.setGraphics(this);
        addMouseListener(fl);
        jf.setVisible(true);
    }

    public void pop(String top,String result){
        JOptionPane jo = new JOptionPane();
        JOptionPane.showMessageDialog(null,result,top,JOptionPane.PLAIN_MESSAGE);

    }

    public int Choose0(){
        JOptionPane jo = new JOptionPane();
        String[] mode = {"普通","SWAP1","禁手"};
        int op = JOptionPane.showOptionDialog(null,"选择哪一个模式","Choose",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,null,mode,mode[0]);
        return op;
    }

    public int Choosetype(){
        JOptionPane jo = new JOptionPane();
        String[] mode = {"对战玩家","对战AI"};
        int op = JOptionPane.showOptionDialog(null,"选择哪一个模式","Choosetype",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,null,mode,mode[0]);
        return op;
    }

    public int Chooseturn(){
        JOptionPane jo = new JOptionPane();
        String[] turn = {"执黑棋","执白棋"};
        int op = JOptionPane.showOptionDialog(null,"选择黑白方","Choosetype",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,null,turn,turn[0]);
        return op + 1;
    }
    public int Choose1(){
        JOptionPane jo = new JOptionPane();
        String[] mode = {"同意","拒绝"};
        String[] turns = {"白方","黑方"};
        int op = JOptionPane.showOptionDialog(null,turns[turn - 1] + "是否同意和棋","Choose",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,null,mode,mode[0]);
        return op;
    }


    public void Exchange(){
        JOptionPane jo = new JOptionPane();
        String[] mode = {"是","否"};
        JOptionPane.showOptionDialog(null,"白方是否选择交换","SWAP1",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,mode,mode[0]);
    }

    public int choosechange(){
        JOptionPane jo = new JOptionPane();
        String[] turn = {"是","否"};
        int op = JOptionPane.showOptionDialog(null,"玩家是否选择同AI交换","Choosetype",JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,null,turn,turn[0]);
        return op;
    }
    public void paint(Graphics g){
        super.paint(g);
        Image board = new ImageIcon("pic/board.jpg").getImage();
        g.drawImage(board,0,0,600,600,null);

        for(int i = 0; i < 15;i++){
            for(int j = 0; j < 15;j++){
                if(isPiece[i][j] == 1){
                    int x = 40 * j + 20;
                    int y = 40 * i + 20;
                    g.drawImage(black,x - 20,y - 20,40,40,null);
                }else if(isPiece[i][j] == 2){
                    int x = 40 * j + 20;
                    int y = 40 * i + 20;
                    g.drawImage(white,x - 20,y - 20,40,40,null);
                }
            }
        }
    }
}
