import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class ButtonListener implements GoBangConfig, ActionListener {
    public GoBangFrame gf;
    public Login login;

    public ButtonListener(Login login){this.login = login;}

    public ButtonListener(GoBangFrame gf,JComboBox<String> BOX){
        this.gf = gf;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始新游戏")){
            GoBangFrame gf=new GoBangFrame();//初始化一个五子棋界面的对象
            gf.init0();
            login.dispose();
        }
        if (e.getActionCommand().equals("开始游戏")) {//开始游戏
                if(gf.isRestart == 0) {
                    gf.isRestart = 1;
                    if (gf.turn != 0) {
                        gf.pop("错误提示", "游戏已开始");
                    }
                    else {
                        gf.chessLocations.clear();
                        gf.type = gf.Choosetype();
                        gf.mode = gf.Choose0();
                        for (int i = 0; i < gf.isPiece.length; i++)
                            Arrays.fill(gf.isPiece[i], 0);
                        gf.repaint();
                        if (gf.mode == 0) {
                            gf.pop("提示", "普通模式，棋局开始");
                            gf.turn = 1;
                            if(gf.type == 1) {
                                gf.turn = gf.Chooseturn();
                                if(gf.turn == 2){
                                    Graphics g = gf.getGraphics();
                                    g.drawImage(black,280,280,size,size,null);
                                    gf.isPiece[7][7] = 1;
                                }
                            }
                        } else if (gf.mode == 1) {
                            gf.pop("提示", "SWAP1模式，黑方先手摆放两黑一白,该模式player开始时需执黑棋");
                            gf.turn = 1;
                        } else if (gf.mode == 2) {
                            gf.pop("提示", "禁手模式，黑方先手，特定情况下受禁手限制");
                            gf.turn = 1;
                            if(gf.type == 1) {
                                gf.turn = gf.Chooseturn();
                                if(gf.turn == 2){
                                    Graphics g = gf.getGraphics();
                                    g.drawImage(black,280,280,size,size,null);
                                    gf.isPiece[7][7] = 1;
                                }
                            }
                        }
                    }
                }else if(gf.isRestart == 1){
                    gf.pop("提示","游戏已开始,请点击重新开始");
                }else if(gf.isRestart == 2){
                    gf.pop("提示","游戏已结束，请重新开始");
                }
            }
           else if (e.getActionCommand().equals("悔棋")) {
                if (gf.isRestart == 0) {
                    gf.pop("错误提示", "游戏未开始，不能悔棋");
                }
                else if(gf.isRestart == 2){
                    gf.pop("错误提示","游戏已结束，不能悔棋");
                }
                else {
                    if (gf.chessLocations.size() == 0) {
                        gf.pop("错误提示", "棋盘上已无棋子");
                    }else {
                        if (gf.type == 0) {
                            ChessLocation last = new ChessLocation();
                            last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                            gf.isPiece[last.x][last.y] = 0;
                            if (gf.turn == 1) {
                                gf.turn++;
                            } else {
                                gf.turn--;
                            }
                            gf.repaint();
                        }else if(gf.type == 1) {
                            if (gf.mode == 1 && gf.chessLocations.size() <= 3) {
                                ChessLocation last = new ChessLocation();
                                last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                                gf.isPiece[last.x][last.y] = 0;
                                if (gf.turn == 1) {
                                    gf.turn++;
                                } else {
                                    gf.turn--;
                                }
                                gf.repaint();
                            } else {
                                ChessLocation last = new ChessLocation();
                                last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                                gf.isPiece[last.x][last.y] = 0;
                                last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                                gf.isPiece[last.x][last.y] = 0;
                                gf.repaint();
                            }
                        }
                    }
                }
            }
           else if (e.getActionCommand().equals("重新开始")) {//开始游戏
                {
                    if (gf.isRestart == 0) {
                        gf.pop("错误提示", "游戏还未开始过");
                    }
                    else {
                        gf.chessLocations.clear();
                        for (int i = 0; i < gf.isPiece.length; i++)
                            Arrays.fill(gf.isPiece[i], 0);
                        gf.repaint();
                        //如果是开始新游戏的按钮，再为左半部分设置监听方法
                        gf.type = gf.Choosetype();
                        gf.mode = gf.Choose0();
                        if (gf.mode == 0) {
                            gf.pop("提示", "普通模式，重新开始");
                            gf.turn = 1;
                            if(gf.type == 1) {
                                gf.turn = gf.Chooseturn();
                                if(gf.turn == 2){
                                    Graphics g = gf.getGraphics();
                                    g.drawImage(black,280,280,size,size,null);
                                    gf.isPiece[7][7] = 1;
                                }
                            }
                        } else if (gf.mode == 1) {
                            gf.pop("提示", "SWAP1模式，重新开始，黑方player先手摆两黑一白");
                            gf.turn = 1;
                        } else if (gf.mode == 2) {
                            gf.pop("提示", "禁手模式，重新开始，黑方先手，特定情况下受禁手限制");
                            gf.turn = 1;
                            if(gf.type == 1) {
                                gf.turn = gf.Chooseturn();
                                if(gf.turn == 2){
                                    Graphics g = gf.getGraphics();
                                    g.drawImage(black,280,280,size,size,null);
                                    gf.isPiece[7][7] = 1;
                                }
                            }
                        }
                        gf.isRestart = 1;
                    }
                }
            }
           else if (e.getActionCommand().equals("暂停游戏")) {//考虑一下
                if(gf.isRestart == 2){
                    gf.pop("提示","游戏已结束");
                }
                else if(gf.isRestart == 0){
                    gf.pop("提示","游戏还未开始");
                }
                else {
                    gf.pop("提示", "游戏已暂停");
                }
            }
           else if (e.getActionCommand().equals("认输")) {
               if(gf.type == 0) {
                   if (gf.turn == 0 && gf.isRestart == 0) gf.pop("错误提示", "游戏未开始");
                   else if (gf.turn == 0 && gf.isRestart == 2) gf.pop("错误提示", "游戏已结束");
                   else if (gf.turn == 1) {
                       gf.pop("游戏结果", "白方赢");
                       gf.isRestart = 2;
                   } else {
                       gf.pop("游戏结果", "黑方赢");
                       gf.isRestart = 2;
                   }
                   gf.turn = 0;
               }else if(gf.type == 1){
                   if (gf.turn == 0 && gf.isRestart == 0) gf.pop("错误提示", "游戏未开始");
                   else if (gf.turn == 0 && gf.isRestart == 2) gf.pop("错误提示", "游戏已结束");
                   else if (gf.turn == 1) {
                       gf.pop("游戏结果", "AI赢");
                       gf.isRestart = 2;
                   } else {
                       gf.pop("游戏结果", "玩家赢");
                       gf.isRestart = 2;
                   }
                   gf.turn = 0;
               }
            }
           else if(e.getActionCommand().equals("和棋")) {
            if (gf.type == 0) {
                if (gf.turn == 0 && gf.isRestart == 0) gf.pop("提示", "游戏未开始");
                else if (gf.turn == 0 && gf.isRestart == 2) gf.pop("错误提示", "游戏已结束");
                else {
                    int i = gf.Choose1();
                    if (i == 0) {
                        gf.pop("结束提示", "双方和棋");
                        gf.turn = 0;
                    } else {
                        String[] turns = {"白方", "黑方"};
                        gf.pop("提示", turns[gf.turn - 1] + "不同意和棋，棋局继续");
                    }
                }
            } else if (gf.type == 1) {
                if (gf.turn == 0 && gf.isRestart == 0) gf.pop("提示", "游戏未开始");
                else if (gf.turn == 0 && gf.isRestart == 2) gf.pop("错误提示", "游戏已结束");
                else {
                    int i = gf.Choose1();
                    if (i == 0) {
                        gf.pop("结束提示", "AI和棋");
                        gf.turn = 0;
                    }
                }
            }
        }
        /*else if(gf.mode == 1){
            if (e.getActionCommand().equals("开始游戏")) {//开始游戏
                if(gf.isRestart == 0) {
                    gf.isRestart =1;
                    if (gf.turn != 0) {
                        gf.pop("错误提示", "游戏已开始");
                    } else {
                        gf.chessLocations.clear();
                        gf.mode = gf.Choose0();
                        for (int i = 0; i < gf.isPiece.length; i++)
                            Arrays.fill(gf.isPiece[i], 0);
                        gf.repaint();
                        if (gf.mode == 0) {
                            gf.pop("提示", "普通模式，棋局开始");
                            gf.turn = 1;
                            gf.pop("提示", "普通模式，黑方先手");
                        } else if (gf.mode == 1) {
                            gf.pop("提示", "SWAP1模式，黑方先手摆放两黑一白");
                            gf.turn = 1;
                        } else if (gf.mode == 2) {
                            gf.pop("提示", "禁手模式，黑方先手，特定情况下受禁手限制");
                            gf.turn = 1;
                        }
                    }
                }else if(gf.isRestart == 1){
                    gf.pop("提示","游戏已开始，请重新开始");
                }else if(gf.isRestart == 2){
                    gf.pop("提示","游戏已结束，请重新开始");
                }
            }
            else if (e.getActionCommand().equals("悔棋")) {
                if (gf.isRestart == 0) {
                    gf.pop("错误提示", "游戏未开始，不能悔棋");
                }
                else if(gf.isRestart == 2){
                    gf.pop("错误提示","游戏已结束，不能悔棋");
                }
                else {
                    if (gf.chessLocations.size() == 0) {
                        gf.pop("错误提示", "棋盘上已无棋子");
                    }else{
                        ChessLocation last = new ChessLocation();
                        last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                        gf.isPiece[last.x][last.y] = 0;
                        if (gf.turn == 1) {
                            gf.turn++;
                        } else {
                            gf.turn--;
                        }
                        gf.repaint();
                    }
                }
            }
            else if (e.getActionCommand().equals("重新开始")) {//开始游戏
                {
                    if (gf.isRestart == 0) {
                        gf.pop("错误提示", "游戏还未开始过");
                    }
                    else {
                        gf.chessLocations.clear();
                        for (int i = 0; i < gf.isPiece.length; i++)
                            Arrays.fill(gf.isPiece[i], 0);
                        gf.repaint();
                        //如果是开始新游戏的按钮，再为左半部分设置监听方法
                        gf.mode = gf.Choose0();
                        if (gf.mode == 0) {
                            gf.pop("提示", "普通模式，重新开始");
                            gf.turn = 1;
                            gf.pop("提示", "黑方先手");
                        } else if (gf.mode == 1) {
                            gf.pop("提示", "SWAP1模式，重新开始，黑方先手摆两黑一白");
                            gf.turn = 1;
                        } else if (gf.mode == 2) {
                            gf.pop("提示", "禁手模式，重新开始，黑方先手，特定情况下受禁手限制");
                            gf.turn = 1;
                        }
                        gf.isRestart = 1;
                    }
                }
            }
            else if (e.getActionCommand().equals("暂停游戏")) {//考虑一下
                if(gf.isRestart == 2){
                    gf.pop("提示","游戏已结束");
                }
                else if(gf.isRestart == 0){
                    gf.pop("提示","游戏还未开始");
                }
                else {
                    gf.pop("提示", "游戏已暂停");
                }
            }
            else if (e.getActionCommand().equals("认输")) {
                if (gf.turn == 0) gf.pop("提示", "游戏未开始");
                else if (gf.turn == 1) {
                    gf.pop("游戏结果", "白方赢");
                    gf.isRestart = 2;
                }
                else {
                    gf.pop("游戏结果", "黑方赢");
                    gf.isRestart = 2;
                }
                gf.turn = 0;
            }
            else if(e.getActionCommand().equals("和棋")){
                if(gf.turn == 0) gf.pop("提示","游戏未开始");
                else {
                    int i = gf.Choose1();
                    if(i == 0){
                        gf.pop("结束提示","双方和棋");
                        gf.isRestart = 2;
                        gf.turn = 0;
                    }else{
                        String[] turns = {"白方","黑方"};
                        gf.pop("提示",turns[gf.turn - 1] + "不同意和棋，棋局继续");
                    }
                }
            }
        }
        else if(gf.mode == 2){
            if (e.getActionCommand().equals("开始游戏")) {//开始游戏
                if (gf.isRestart == 0) {
                    gf.isRestart = 1;
                    if (gf.turn != 0) {
                        gf.pop("错误提示", "游戏已开始");
                    } else {
                        gf.chessLocations.clear();
                        gf.mode = gf.Choose0();
                        for (int i = 0; i < gf.isPiece.length; i++)
                            Arrays.fill(gf.isPiece[i], 0);
                        gf.repaint();
                        if (gf.mode == 0) {
                            gf.pop("提示", "普通模式，棋局开始");
                            gf.turn = 1;
                            gf.pop("提示", "黑方先手");
                        } else if (gf.mode == 1) {
                            gf.pop("提示", "SWAP1模式，黑方先手摆放两黑一白");
                            gf.turn = 1;
                        } else if (gf.mode == 2) {
                            gf.pop("提示", "禁手模式，黑方先手，特定情况下受禁手限制");
                            gf.turn = 1;
                        }
                    }
                } else if (gf.isRestart == 1) {
                    gf.pop("提示", "游戏已开始,请点击重新开始");
                } else if (gf.isRestart == 2) {
                    gf.pop("提示", "游戏已结束，请重新开始");
                }
            }
            else if (e.getActionCommand().equals("悔棋")) {
                if (gf.isRestart == 0) {
                    gf.pop("错误提示", "游戏未开始，不能悔棋");
                }
                else if(gf.isRestart == 2){
                    gf.pop("错误提示","游戏已结束，不能悔棋");
                }
                else {
                    if (gf.chessLocations.size() == 0) {
                        gf.pop("错误提示", "棋盘上已无棋子");
                    }else{
                        ChessLocation last = new ChessLocation();
                        last = gf.chessLocations.remove(gf.chessLocations.size() - 1);
                        gf.isPiece[last.x][last.y] = 0;
                        if (gf.turn == 1) {
                            gf.turn++;
                        } else {
                            gf.turn--;
                        }
                        gf.repaint();
                    }
                }
            }
            else if (e.getActionCommand().equals("重新开始")) {//开始游戏
                gf.chessLocations.clear();
                for (int i = 0; i < gf.isPiece.length; i++)
                    Arrays.fill(gf.isPiece[i], 0);
                gf.repaint();
                //如果是开始新游戏的按钮，再为左半部分设置监听方法
                gf.mode = gf.Choose0();
                if(gf.mode == 0){
                    gf.pop("提示","普通模式，重新开始");
                    gf.turn = 1;
                    gf.pop("提示","黑方先手");
                }else if(gf.mode == 1){
                    gf.pop("提示","SWAP1模式，重新开始，黑方先手摆两黑一白");
                    gf.turn = 1;
                }else if(gf.mode == 2){
                    gf.pop("提示","禁手模式，重新开始，黑方先手，特定情况下受禁手限制");
                    gf.turn = 1;
                }
                gf.isRestart = 1;
            }
            else if (e.getActionCommand().equals("暂停游戏")) {//考虑一下
                if(gf.isRestart == 2){
                    gf.pop("提示","游戏已结束");
                }
                else if(gf.isRestart == 0){
                    gf.pop("提示","游戏还未开始");
                }
                else {
                    gf.pop("提示", "游戏已暂停");
                }
            }
            else if (e.getActionCommand().equals("认输")) {
                if (gf.turn == 0) gf.pop("提示", "游戏未开始");
                else if (gf.turn == 1) gf.pop("游戏结果", "白方赢");
                else gf.pop("游戏结果", "黑方赢");
                gf.isRestart = 2;
                gf.turn = 0;
            }
            else if(e.getActionCommand().equals("和棋")){
                if(gf.turn == 0) gf.pop("提示","游戏未开始");
                else {
                    int i = gf.Choose1();
                    if(i == 0){
                        gf.pop("结束提示","双方和棋");
                        gf.isRestart = 2;
                        gf.turn = 0;
                    }else{
                        String[] turns = {"白方","黑方"};
                        gf.pop("提示",turns[gf.turn - 1] + "不同意和棋，棋局继续");
                    }
                }
            }
        }
        */
    }

}
