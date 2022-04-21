import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class FrameListener implements GoBangConfig, MouseListener {

    public GoBangFrame gf;
    public void setGraphics(GoBangFrame gf){
        this.gf = gf;
    }

    private int win(int ix,int iy,int turn){//1是黑色，2是白色
        int min_x = ix - 4;
        int max_x = ix + 4;
        if(min_x < 0){
            min_x = 0;
        }
        if(max_x > 14){
            max_x = 14;
        }
        int min_y = iy - 4;
        int max_y = iy + 4;
        if(min_y < 0){
            min_y = 0;
        }
        if(max_y > 14){
            max_y = 14;
        }
        if(turn == 1){
            for(int i = min_x, count1 = 0;i <= max_x;i++){//纵向
                if(gf.isPiece[iy][i]==1) count1++;
                else count1=0;
                if(count1==5) {
                    return 1;
                }
            }
            for(int i = min_y,count = 0; i <= max_y;i++) {//横向
                if(gf.isPiece[i][ix]==1) count++;
                else count = 0;
                if(count == 5) {
                    return 1;
                }
            }
            for(int i=-4, count3 = 0;i<=4;i++) {
                if((ix + i >= 0) && (iy + i >= 0) && (ix + i < 15) && (iy + i < 15)) {
                    if(gf.isPiece[iy+i][ix+i]==1) count3++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count3=0;
                    if(count3==5) {
                        return 1;
                    }
                }
            }
            for(int i=-4, count4=0;i<=4;i++) {
                if((iy+i>=0)&&(ix-i>=0)&&(iy + i < 15)&&(ix - i < 15)) {
                    if(gf.isPiece[iy+i][ix-i]==1) count4++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count4=0;
                    if(count4==5) {
                        return 1;
                    }
                }
            }
        }
        else if(turn == 2 ){
            for(int i = min_x, count1 = 0;i <= max_x;i++){//纵向
                if(gf.isPiece[iy][i]==2) count1++;
                else count1=0;
                if(count1==5) {
                    return 2;
                }
            }
            for(int i = min_y,count = 0; i <= max_y;i++) {//横向
                if(gf.isPiece[i][ix]==2) count++;
                else count = 0;
                if(count == 5) {
                    return 2;
                }
            }
            for(int i=-4, count3 = 0;i<=4;i++) {
                if((ix + i >= 0) && (iy + i >= 0) && (ix + i < 15) && (iy + i < 15)) {
                    if(gf.isPiece[iy+i][ix+i]==2) count3++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count3=0;
                    if(count3==5) {
                        return 2;
                    }
                }
            }
            for(int i=-4, count4=0;i<=4;i++) {
                if((iy+i>=0)&&(ix-i>=0)&&(iy + i < 15)&&(ix - i < 15)) {
                    if(gf.isPiece[iy+i][ix-i]==2) count4++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count4=0;
                    if(count4==5) {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    private int[] dir_judge(int ix, int iy, int ex, int ey ) {
        // 该方向没意义,返回0，死
        int rt = 1;//有多少位置不是白子
        for (int i = 1; ix + i * ex < 15 && ix + i * ex >= 0 && iy + i * ey < 15 && iy + i * ey >= 0 && rt < 5; i++)
            if (gf.isPiece[iy + i * ey][ix + i * ex] != 2)
                rt++;
            else {
                break;
            }
        for (int i = 1; ix - i * ex >= 0 && ix - i * ex < 15 && iy - i * ey >= 0 && iy - i * ey < 15 && rt < 5; i++) {
            if ((gf.isPiece[iy - i * ey][ix - i * ex] != 2))
                rt++;
            else {
                break;
            }
        }
        if(rt < 5){
            return new int[] {0,1};
        }

        int chess_num1 = 1,chess_num2 = 1;

        int live_1 = 0,live_2 =0;

        int live;

        boolean flag_mid1 = false,flag_mid2 = false;

        int flag_i1 = 1,flag_i2 = 1;
        int i;

        for (i = 1; ix + i * ex < 15 && ix + i * ex >= 0 && iy + i * ey < 15 && iy + i * ey >= 0; i++) {
            if (gf.isPiece[iy + i * ey][ix + i * ex] == 1)
                chess_num1++;

            else if (gf.isPiece[iy + i * ey][ix + i * ex] == 0) {
                if (!flag_mid1) {
                    flag_mid1 = true;
                    flag_i1 = i;
                } else {
                    break;
                }
            }
            else {
                break;
            }
        }

        if (ix + i * ex < 15 && ix + i * ex >= 0 && iy + i * ey < 15 && iy + i * ey >= 0) {
            // 最后一个位置为空位+1活
            if( gf.isPiece[iy + i * ey][ix + i * ex] == 0) {
                live_1++;
                // 若是在尾部检测到连续的空格而退出搜索,则不算有中空
                if(chess_num1 == flag_i1)
                    flag_mid1 = false;
                // 若中空的位置在4以下 且 棋子数>=4,则这一边的4死
                if(flag_mid1 && chess_num1 > 3 && flag_i1 < 4) {
                    live_1--;
                }
            }
            // 最后一个位置不是空格,且搜索了2步以上,若前一个是空格,  则不算中空,且为活的边
            else if( gf.isPiece[iy + i * ey][ix + i * ex]  != 1 && i >= 2)
                if(gf.isPiece[iy + (i - 1) * ey][ix + (i - 1) * ex] == 0) {
                    live_1++;
                    flag_mid1 = false;
                }
        }
        // 最后一个位置是边界  搜索了2步以上,且前一个是空格,  则不算中空,且为活的边
        else if(i >= 2 && gf.isPiece[iy + (i - 1) * ey][ix + (i - 1) * ex] == 0) {
            live_1++;
            flag_mid1 = false;
        }

        // 往反方向搜索
        for (i = 1; ix - i * ex >= 0 && ix - i * ex < 15 && iy - i * ey >= 0 && iy - i * ey < 15; i++) {
            if (gf.isPiece[iy - i * ey][ix - i * ex] == 1)
                chess_num2++;
            else if (gf.isPiece[iy - i * ey][ix - i * ex] == 0) {
                if (!flag_mid2) {
                    flag_mid2 = true;
                    flag_i2 = i;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        // 计算反方向类型
        if (ix - i * ex < 15 && ix - i * ex >= 0 && iy - i * ey < 15 && iy - i * ey >= 0) {
            if( gf.isPiece[iy - i * ey][ix - i * ex] == 0) {
                live_2++;
                if(chess_num2 == flag_i2)
                    flag_mid2 = false;
                if(flag_mid2 && chess_num2 > 3 && flag_i2 < 4) {
                    live_2--;
                }
            }
            else if( gf.isPiece[iy - i * ey][ix - i * ex] != 1 && i >= 2 )
                if(gf.isPiece[iy - (i - 1) * ey][ix - (i - 1) * ex] == 0) {
                    live_2++;
                    flag_mid2 = false;
                }
        }
        else if(i >= 2 && gf.isPiece[iy - (i - 1) * ey][ix - (i - 1) * ex] == 0) {
            live_2++;
            flag_mid2 = false;
        }


        // 两边都没中空,直接合成
        if( !flag_mid1 && !flag_mid2 ) {
            live = live_1 + live_2;
            return new int[] {chess_num1 + chess_num2 - 1, live};
        }
        // 两边都有中空
        else if( flag_mid1 && flag_mid2 ){
            int temp = flag_i1 + flag_i2 - 1;
            // 判断中间的纯连子数,在5以上,直接返回;  为4,返回活4;
            if(temp >= 5)
                return new int[] {temp, 2};
            if(temp == 4)
                return new int[] {temp, 2};
            // 先看有没死4,再看有没活3,剩下只能是死3
            if(chess_num1 + flag_i2 - 1 >= 4 || chess_num2 + flag_i1 - 1 >= 4)
                return new int[] {4, 1};
            if(chess_num1+flag_i2 - 1 == 3 && live_1 > 0 || chess_num2+flag_i1 - 1 == 3 && live_2 > 0)
                return new int[] {3, 2};
            return new int[] {3, 1};
        }
        // 有一边有中空
        else {
            // 总棋子数少于5,直接合成
            if( chess_num1 + chess_num2 - 1 < 5 )
                return new int[] {chess_num1 + chess_num2 - 1, live_1 + live_2};
                // 多于5,先找成5,再找活4,剩下的只能是死4
            else {
                if(flag_mid1 && chess_num2 + flag_i1 - 1 >= 5)
                    return new int[] {chess_num2 + flag_i1 - 1, live_2 + 1};
                if(flag_mid2 && chess_num1 + flag_i2 - 1 >= 5)
                    return new int[] {chess_num1 + flag_i2 - 1, live_1 + 1};

                if(flag_mid1 && (chess_num2 + flag_i1 - 1 == 4 && live_2 == 1 || flag_i1 == 4) )
                    return new int[] {4, 2};
                if(flag_mid2 && (chess_num1 + flag_i2 - 1 == 4 && live_1 == 1 || flag_i2 == 4) )
                    return new int[] {4, 2};
                return new int[] {4, 1};
            }
        }
    }

    private int getType(int ix, int iy) {
        if (gf.isPiece[iy][ix] != 0)
            return 2;
        int[][] types = new int[4][2];

        types[0] = dir_judge(ix, iy, 0, 1);   // 竖直

        types[1] = dir_judge(ix, iy, 1, 0);   // 横向

        types[2] = dir_judge(ix, iy, 2, 1);  // 斜上

        types[3] = dir_judge(ix, iy, 1, 1);   // 斜下
        // 各种棋型的方向的数目
        int longfive = 0;
        int five_OR_more = 0;
        int four_died = 0, four_live = 0;
        int three_died = 0, three_live = 0;
        int two_died  = 0, two_live = 0;
        // 各方向上棋型的判别
        for (int k = 0; k < 4; k++) {
            if (types[k][0] > 5) {
                longfive++;              // 长连
                five_OR_more++;
            }
            else if (types[k][0] == 5)
                five_OR_more++;          // 成5
            else if (types[k][0] == 4 && types[k][1] == 2)
                four_live++;             // 活4
            else if (types[k][0] == 4 && types[k][1] != 2)
                four_died++;             // 死4
            else if (types[k][0] == 3 && types[k][1] == 2)
                three_live ++;           // 活3
            else if (types[k][0] == 3 && types[k][1] != 2)
                three_died++;            // 死3
            else if (types[k][0] == 2 && types[k][1] == 2)
                two_live++;              // 活2
            else if (types[k][0] == 2 && types[k][1] != 2)
                two_died++;              // 死2

        }
        // 总棋型的判别
 		// 黑棋且选择有禁手
        if (longfive != 0)        		//长连禁手
            return 20;
        if (three_live  >=2) {
            if(four_live + four_died >= 1){
                return 23;      //四三三禁手
            }else{
                return 21;      //三三禁手
            }
        }
        if (four_live + four_died >=2)  //四四禁手
            return 22;


        if (five_OR_more != 0)
            return 1;   //成5
        if (four_live != 0 || four_died >= 2 || four_died != 0 && three_live  != 0)
            return 2;   //活4或者是双死4或者是死4活3
        if (three_died != 0 && three_live  != 0)
            return 4;   //死3活3
        if (four_died != 0)
            return 5;   //死4
        if (three_live  != 0)
            return 6;   //单活3
        if (two_live >= 2)
            return 7;   //双活2
        if (three_died != 0)
            return 8;   //死3
        if (two_live != 0 && two_died != 0)
            return 9;   //死2活2
        if (two_live != 0)
            return 10;  //成活2
        if (two_died != 0)
            return 11;  //成死2
        return 12;
    }

    public Integer unionValue0_1(Integer a,Integer b) {
        if((a==null)||(b==null)) {
            return 0;
        }
        else if(a == 1000 && (b == 950 || b == 1000 || b == 900) || b == 1000 && (a == 950 || a == 900)){
            return 6000;
        }
        else if(a == 150 && b == 150 || a == 140 && b == 140){
            return 5000;
        }
        else if(a == 950 && b == 950 || a == 850 && b == 850){
            return 5000;
        }
        else if(((a == 150)&&(b > 150)&&(b != 800)&&(b != 850)&&(b!=7000))||((b == 150)&&(a > 150)&&(a != 800)&&(a != 850)&&(a!=7000))){
            return 5000;
        }
        else if((a == 5000)||(b == 5000)){
            return 3000;
        }

        else if(((a == 900)&&(b == 900))){
            return 2000;
        }
        else if(((a>=60)&&(a<=80)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=60)&&(b<=80))){
            return 1500;
        }
        else if(((a>=10)&&(a<=25)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=10)&&(b<=25))||((a>=60)&&(a<=80)&&(b>=60)&&(b<=80))) {
            return 1500;
        }
        else if(((a>=10)&&(a<=25)&&(b>=60)&&(b<=80))||((a>=60)&&(a<=80)&&(b>=10)&&(b<=25))) {
            return 800;
        }
        else if((a>=10)&&(a<=25)&&(b>=10)&&(b<=25)) {
            return 60;
        }
        return 0;
    }

    public Integer unionValue2_0(Interger_pair a,Interger_pair b){
        if((a==null)||(b==null)) {
            return 0;
        }
        else if(a.value == 1000 && (b.value == 950 || b.value == 1000 || b.value == 900) || b.value == 1000 && (a.value == 950 || a.value == 900)){
            return 6000;
        }
        else if(a.value == 150 && b.value == 150 || a.value == 140 && b.value == 140){
            return 5000;
        }
        else if(a.value == 950 && b.value == 950 || a.value == 850 && b.value == 850){
            return 5000;
        }
        else if(((a.value == 150)&&(b.value > 150)&&(b.value != 800)&&(b.value != 850)&&(b.value!=7000))||((b.value == 150)&&(a.value > 150)&&(a.value != 800)&&(a.value != 850)&&(a.value!=7000))){
            return 5000;
        }
        else if((a.value == 5000)||(b.value == 5000)){
            return 3000;
        }

        else if(((a.value == 900)&&(b.value == 900))){
            return 2000;
        }
        else if(((a.value>=60)&&(a.value<=80)&&(b.value>=140)&&(b.value<=1000))||((a.value>=140)&&(a.value<=1000)&&(b.value>=60)&&(b.value<=80))){
            return 1500;
        }
        else if(((a.value>=10)&&(a.value<=25)&&(b.value>=140)&&(b.value<=1000))||((a.value>=140)&&(a.value<=1000)&&(b.value>=10)&&(b.value<=25))||((a.value>=60)&&(a.value<=80)&&(b.value>=60)&&(b.value<=80))) {
            return 1500;
        }
        else if(((a.value>=10)&&(a.value<=25)&&(b.value>=60)&&(b.value<=80))||((a.value>=60)&&(a.value<=80)&&(b.value>=10)&&(b.value<=25))) {
            return 800;
        }
        else if((a.value>=10)&&(a.value<=25)&&(b.value>=10)&&(b.value<=25)) {
            return 60;
        }
        return 0;
    }

    public int[] normal_getPoistion0(){
        for(int i = 0; i < gf.isPiece.length;i++){
            for(int j = 0; j < gf.isPiece[i].length;j++){
                if(gf.isPiece[i][j] == 0){


                    StringBuilder connected = new StringBuilder("0");
                    int jmin = Math.max(0,j - 4);
                    for(int pj = j - 1;pj >= jmin;pj--){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Integer valueleft = GoBangFrame.map0.get(connected.toString());
                    if(valueleft != null){
                        gf.theValue[i][j] += valueleft;
                    }


                    connected = new StringBuilder("0");
                    int jmax = Math.min(14 , j+4);
                    for(int pj = j + 1;pj <= jmax;pj ++){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Integer valueright = GoBangFrame.map0.get(connected.toString());
                    if(valueright != null) {
                        gf.theValue[i][j] += valueright;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueleft,valueright);


                    connected = new StringBuilder("0");
                    int imin = Math.max(0,i - 4);
                    for(int pi = i - 1; pi >= imin; pi--){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Integer valueup = GoBangFrame.map0.get(connected.toString());
                    if(valueup != null) gf.theValue[i][j] += valueup;


                    connected = new StringBuilder("0");
                    int imax = Math.min(14,i + 4);
                    for(int pi = i + 1; pi <= imax; pi++){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Integer valuedown = GoBangFrame.map0.get(connected.toString());
                    if(valuedown != null) {
                        gf.theValue[i][j] += valuedown;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueup,valuedown);


                    connected = new StringBuilder("0");
                    for(int p = 1;p <= 4;p++) {
                        if ((i - p >= 0)  && (j - p >= 0)) {
                            connected.append(gf.isPiece[i - p][j - p]);
                        }
                    }
                    Integer valueleftup = GoBangFrame.map0.get(connected.toString());
                    if(valueleftup != null){
                        gf.theValue[i][j] += valueleftup;
                    }


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++){
                        if((i + p <= 14)&&(j + p <= 14)){
                            connected.append(gf.isPiece[i + p][j + p]);
                        }
                    }
                    Integer valueRightDown= GoBangFrame.map0.get(connected.toString());
                    if(valueRightDown != null) {
                        gf.theValue[i][j] += valueRightDown;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueleftup,valueRightDown);


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if ((i + p <= 14) && (j - p >= 0)) {
                            connected.append(gf.isPiece[i + p][j - p]);
                        }
                    }
                    Integer valueLeftDown = GoBangFrame.map0.get(connected.toString());
                    if(valueLeftDown != null){
                        gf.theValue[i][j] += valueLeftDown;
                    }


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if((i-p>=0)&&(j+p<=14))
                            connected.append(gf.isPiece[i - p][j + p]);
                    }
                    Integer valueRightUp= GoBangFrame.map0.get(connected.toString());
                    if(valueRightUp!=null) {
                        gf.theValue[i][j] += valueRightUp;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueLeftDown,valueRightUp);
                }
            }
        }
        ArrayList<Integer> maxi = new ArrayList<>();
        ArrayList<Integer> maxj = new ArrayList<>();
        int max = 0;
        maxi.add(0);
        maxj.add(0);
        ArrayList<Integer> morei = new ArrayList<>();
        ArrayList<Integer> morej = new ArrayList<>();
        int more = 0;
        morei.add(0);
        morej.add(0);
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                if(gf.theValue[i][j] > max){
                    more = max;
                    max = gf.theValue[i][j];
                    morei = maxi;
                    morej = maxj;
                    maxi.clear();
                    maxj.clear();
                    maxi.add(i);
                    maxj.add(j);
                }else if(gf.theValue[i][j] == max){
                    maxi.add(i);
                    maxj.add(j);
                }
            }
        }

        int[] tci = new int[2];
        int[] tcj = new int[2];
        if(maxi.size() > 1) {
            for(int t = 0; t < Math.min(2,maxi.size());t++){
                tci[t] = maxi.get(t);
                tcj[t] = maxj.get(t);
                more = max;
            }
        }else{
            tci[0] = maxi.get(0);
            tcj[0] = maxj.get(0);
            Random rn = new Random();
            int tc = rn.nextInt(morei.size());
            tci[1] = morei.get(tc);
            tcj[1] = morej.get(tc);
        }
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                gf.theValue[i][j] = 0;
            }
        }
        return new int[]{tci[0], tcj[0], max,tci[1],tcj[1],more};//y和x
    }

    public int[] normal_getPoistion1(){
        for(int i = 0; i < gf.isPiece.length;i++){
            for(int j = 0; j < gf.isPiece[i].length;j++){
                if(gf.isPiece[i][j] == 0){

                    StringBuilder connected = new StringBuilder("0");
                    int jmin = Math.max(0,j - 4);
                    for(int pj = j - 1;pj >= jmin;pj--){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Integer valueleft = GoBangFrame.map1.get(connected.toString());
                    if(valueleft != null){
                        gf.theValue[i][j] += valueleft;
                    }
                    connected = new StringBuilder("0");
                    int jmax = Math.min(14 , j+4);
                    for(int pj = j + 1;pj <= jmax;pj ++){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Integer valueright = GoBangFrame.map1.get(connected.toString());
                    if(valueright != null) {
                        gf.theValue[i][j] += valueright;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueleft,valueright);


                    connected = new StringBuilder("0");
                    int imin = Math.max(0,i - 4);
                    for(int pi = i - 1; pi >= imin; pi--){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Integer valueup = GoBangFrame.map1.get(connected.toString());
                    if(valueup != null) gf.theValue[i][j] += valueup;


                    connected = new StringBuilder("0");
                    int imax = Math.min(14,i + 4);
                    for(int pi = i + 1; pi <= imax; pi++){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Integer valuedown = GoBangFrame.map1.get(connected.toString());
                    if(valuedown != null) {
                        gf.theValue[i][j] += valuedown;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueup,valuedown);


                    connected = new StringBuilder("0");
                    for(int p = 1;p <= 4;p++) {
                        if ((i - p >= 0)  && (j - p >= 0)) {
                            connected.append(gf.isPiece[i - p][j - p]);
                        }
                    }
                    Integer valueleftup = GoBangFrame.map1.get(connected.toString());
                    if(valueleftup != null){
                        gf.theValue[i][j] += valueleftup;
                    }


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++){
                        if((i + p <= 14)&&(j + p <= 14)){
                            connected.append(gf.isPiece[i + p][j + p]);
                        }
                    }
                    Integer valueRightDown= GoBangFrame.map1.get(connected.toString());
                    if(valueRightDown != null) {
                        gf.theValue[i][j] += valueRightDown;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueleftup,valueRightDown);


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if ((i + p <= 14) && (j - p >= 0)) {
                            connected.append(gf.isPiece[i + p][j - p]);
                        }
                    }
                    Integer valueLeftDown = GoBangFrame.map1.get(connected.toString());
                    if(valueLeftDown != null){
                        gf.theValue[i][j] += valueLeftDown;
                    }


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if((i-p>=0)&&(j+p<=14))
                            connected.append(gf.isPiece[i - p][j + p]);
                    }
                    Integer valueRightUp= GoBangFrame.map1.get(connected.toString());
                    if(valueRightUp!=null) {
                        gf.theValue[i][j] += valueRightUp;
                    }
                    gf.theValue[i][j] += unionValue0_1(valueLeftDown,valueRightUp);
                }
            }
        }
        ArrayList<Integer> maxi = new ArrayList<>();
        ArrayList<Integer> maxj = new ArrayList<>();
        int max = 0;
        maxi.add(0);
        maxj.add(0);
        ArrayList<Integer> morei = new ArrayList<>();
        ArrayList<Integer> morej = new ArrayList<>();
        int more = 0;
        morei.add(0);
        morej.add(0);
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                if(gf.theValue[i][j] > max){
                    more = max;
                    max = gf.theValue[i][j];
                    morei = maxi;
                    morej = maxj;
                    maxi.clear();
                    maxj.clear();
                    maxi.add(i);
                    maxj.add(j);
                }else if(gf.theValue[i][j] == max){
                    maxi.add(i);
                    maxj.add(j);
                }
            }
        }

        int[] tci = new int[2];
        int[] tcj = new int[2];
        if(maxi.size() > 1) {
            for(int t = 0; t < Math.min(2,maxi.size());t++){
                tci[t] = maxi.get(t);
                tcj[t] = maxj.get(t);
                more = max;
            }
        }else{
            tci[0] = maxi.get(0);
            tcj[0] = maxj.get(0);
            Random rn = new Random();
            int tc = rn.nextInt(morei.size());
            tci[1] = morei.get(tc);
            tcj[1] = morej.get(tc);
        }
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                gf.theValue[i][j] = 0;
            }
        }
        return new int[]{tci[0], tcj[0], max,tci[1],tcj[1],more};//y和x
    }

    public int[] decision_tree1(int num, int i0,int j0,int max,int i1,int j1,int more){
        if(num == 1){
            return normal_getPoistion1();
        }
        gf.isPiece[i0][j0] = 1;
        int[] a0 = normal_getPoistion0();

        int[] b0 = decision_tree0(num - 1,a0[0],a0[1],a0[2],a0[3],a0[4],a0[5]);
        if(b0[2] > 18000){
            gf.isPiece[i0][j0] = 0;
            return new int[]{i1,j1,more};
        }
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 1;
        int[] a1 = normal_getPoistion0();
        int[] b1 =  decision_tree0(num - 1,a1[0],a1[1],a1[2],a1[3],a1[4],a1[5]);
        gf.isPiece[i1][j1] = 0;

        if((max - (b0[2])/2) > (more - (b1[2])/2)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }
    
    public int[] decision_tree0(int num, int i0,int j0,int max,int i1,int j1,int more){
        if(num == 1){
            return normal_getPoistion0();
        }
        gf.isPiece[i0][j0] = 2;
        int[] a0 = normal_getPoistion1();
        int[] b0 = decision_tree1(num - 1,a0[0],a0[1],a0[2],a0[3],a0[4],a0[5]);
        if(b0[2] > 18000){
            gf.isPiece[i0][j0] = 0;
            return new int[]{i1,j1,more};
        }
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 2;
        int[] a1 = normal_getPoistion1();
        int[] b1 =  decision_tree1(num - 1,a1[0],a1[1],a1[2],a1[3],a1[4],a1[5]);
        gf.isPiece[i1][j1] = 0;
        if((max - (b0[2])/2) > (more - (b1[2])/2)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }

    public int[] decision_tree2_0(int num, int i0,int j0,int max,int i1,int j1,int more){
        if(num == 1){
            return normal_getPoistion2_0();
        }
        gf.isPiece[i0][j0] = 2;
        int[] a0 = normal_getPoistion2_1();
        int[] b0 = decision_tree2_1(num - 1,a0[0],a0[1],a0[2],a0[3],a0[4],a0[5]);
        if(b0[2] > 18000){
            gf.isPiece[i0][j0] = 0;
            return new int[]{i1,j1,more};
        }
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 2;
        int[] a1 = normal_getPoistion2_1();
        int[] b1 =  decision_tree2_1(num - 1,a1[0],a1[1],a1[2],a1[3],a1[4],a1[5]);
        gf.isPiece[i1][j1] = 0;
        if((max - (b0[2])/2) > (more - (b1[2])/2)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }

    public int[] decision_tree2_1(int num, int i0,int j0,int max,int i1,int j1,int more){
        if(num == 1){
            return normal_getPoistion2_1();
        }
        gf.isPiece[i0][j0] = 2;
        int[] a0 = normal_getPoistion2_0();
        int[] b0 = decision_tree2_0(num - 1,a0[0],a0[1],a0[2],a0[3],a0[4],a0[5]);
        if(b0[2] > 20000){
            gf.isPiece[i0][j0] = 0;
            return new int[]{i1,j1,max};
        }
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 2;
        int[] a1 = normal_getPoistion2_0();
        int[] b1 =  decision_tree2_0(num - 1,a1[0],a1[1],a1[2],a1[3],a1[4],a1[5]);
        gf.isPiece[i1][j1] = 0;
        if((max - (b0[2])/2) > (more - (b1[2])/2)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }
    /*
    public int[] decision_tree0(int i0,int j0,int max,int i1,int j1,int more){
        gf.isPiece[i0][j0] = 2;
        int[] a = normal_getPoistion1();
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 2;
        gf.isPiece[i1][j1] = 0;
        int[] b = normal_getPoistion1();
        if((a[2] + a[5] - max) < (b[2] + b[5] - more)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }
    public int[] decision_tree1(int i0,int j0,int max,int i1,int j1,int more){
        gf.isPiece[i0][j0] = 1;
        int[] a = normal_getPoistion0();
        gf.isPiece[i0][j0] = 0;
        gf.isPiece[i1][j1] = 1;
        int[] b = normal_getPoistion0();
        gf.isPiece[i1][j1] = 0;
        if((a[2] + a[5] - max) < (b[2] + b[5] - more)){
            return new int[]{i0,j0,max};
        }else{
            return new int[]{i1,j1,more};
        }
    }
    */
    
    public int[] normal_getPoistion2_0(){
        for(int i = 0; i < gf.isPiece.length;i++){
            for(int j = 0; j < gf.isPiece[i].length;j++){
                if(gf.isPiece[i][j] == 0){
                    StringBuilder connected = new StringBuilder("0");
                    int jmin = Math.max(0,j - 4);
                    for(int pj = j - 1;pj >= jmin;pj--){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Interger_pair valueleft = GoBangFrame.map2.get(connected.toString());
                    if(valueleft != null){
                        gf.theValue[i][j] += valueleft.value;
                    }

                    connected = new StringBuilder("0");
                    int jmax = Math.min(14 , j+4);
                    for(int pj = j + 1;pj <= jmax;pj ++){
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Interger_pair valueright = GoBangFrame.map2.get(connected.toString());
                    if(valueright != null) {
                        gf.theValue[i][j] += valueright.value;
                    }

                    gf.theValue[i][j] += unionValue2_0(valueleft,valueright);


                    connected = new StringBuilder("0");
                    int imin = Math.max(0,i - 4);
                    for(int pi = i - 1; pi >= imin; pi--){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Interger_pair valueup = GoBangFrame.map2.get(connected.toString());
                    if(valueup != null) {
                        gf.theValue[i][j] += valueup.value;
                    }

                    connected = new StringBuilder("0");
                    int imax = Math.min(14,i + 4);
                    for(int pi = i + 1; pi <= imax; pi++){
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Interger_pair valuedown = GoBangFrame.map2.get(connected.toString());
                    if(valuedown != null) {
                        gf.theValue[i][j] += valuedown.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueup,valuedown);


                    connected = new StringBuilder("0");
                    for(int p = 1;p <= 4;p++) {
                        if ((i - p >= 0)  && (j - p >= 0)) {
                            connected.append(gf.isPiece[i - p][j - p]);
                        }
                    }
                    Interger_pair valueleftup = GoBangFrame.map2.get(connected.toString());
                    if(valueleftup != null){
                        gf.theValue[i][j] += valueleftup.value;
                    }


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++){
                        if((i + p <= 14)&&(j + p <= 14)){
                            connected.append(gf.isPiece[i + p][j + p]);
                        }
                    }
                    Interger_pair valueRightDown= GoBangFrame.map2.get(connected.toString());
                    if(valueRightDown != null) {
                        gf.theValue[i][j] += valueRightDown.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueleftup,valueRightDown);


                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if ((i + p <= 14) && (j - p >= 0)) {
                            connected.append(gf.isPiece[i + p][j - p]);
                        }
                    }
                    Interger_pair valueLeftDown = GoBangFrame.map2.get(connected.toString());
                    if(valueLeftDown != null){
                        gf.theValue[i][j] += valueLeftDown.value;
                    }

                    connected = new StringBuilder("0");
                    for(int p = 1; p <= 4; p++) {
                        if((i-p>=0)&&(j+p<=14))
                            connected.append(gf.isPiece[i - p][j + p]);
                    }
                    Interger_pair valueRightUp= GoBangFrame.map2.get(connected.toString());
                    if(valueRightUp!=null) {
                        gf.theValue[i][j] += valueRightUp.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueLeftDown,valueRightUp);
                }
            }
        }
        ArrayList<Integer> maxi = new ArrayList<>();
        ArrayList<Integer> maxj = new ArrayList<>();
        int max = 0;
        maxi.add(0);
        maxj.add(0);
        ArrayList<Integer> morei = new ArrayList<>();
        ArrayList<Integer> morej = new ArrayList<>();
        int more = 0;
        morei.add(0);
        morej.add(0);
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                if(gf.theValue[i][j] > max){
                    more = max;
                    max = gf.theValue[i][j];
                    morei = maxi;
                    morej = maxj;
                    maxi.clear();
                    maxj.clear();
                    maxi.add(i);
                    maxj.add(j);
                }else if(gf.theValue[i][j] == max){
                    maxi.add(i);
                    maxj.add(j);
                }
            }
        }

        int[] tci = new int[2];
        int[] tcj = new int[2];
        if(maxi.size() > 1) {
            for(int t = 0; t < Math.min(2,maxi.size());t++){
                tci[t] = maxi.get(t);
                tcj[t] = maxj.get(t);
                more = max;
            }
        }else{
            tci[0] = maxi.get(0);
            tcj[0] = maxj.get(0);
            Random rn = new Random();
            int tc = rn.nextInt(morei.size());
            tci[1] = morei.get(tc);
            tcj[1] = morej.get(tc);
        }
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                gf.theValue[i][j] = 0;
            }
        }
        return new int[]{tci[0], tcj[0], max,tci[1],tcj[1],more};//y和x
    }

    public int[] normal_getPoistion2_1(){//黑方禁手
        for(int i = 0; i < gf.isPiece.length;i++){
            for(int j = 0; j < gf.isPiece[i].length;j++) {
                if (gf.isPiece[i][j] == 0) {
                    int value = getType(j, i);
                    if (value == 20 || value == 21 || value == 22 || value == 23) {
                        gf.theValue[i][j] = -50000;
                    }
                    else if (value == 1) {
                        gf.theValue[i][j] = 20000;
                    } else if (value == 2) {
                        gf.theValue[i][j] = 3000;
                    } else if (value == 4) {
                        gf.theValue[i][j] = 2000;
                    } else if (value == 5) {
                        gf.theValue[i][j] = 1000;
                    } else if (value == 6) {
                        gf.theValue[i][j] = 500;
                    } else if (value == 7) {
                        gf.theValue[i][j] = 400;
                    } else if (value == 8) {
                        gf.theValue[i][j] = 50;
                    } else if (value == 9) {
                        gf.theValue[i][j] = 200;
                    } else if (value == 10) {
                        gf.theValue[i][j] = 80;
                    } else if (value == 11) {
                        gf.theValue[i][j] = 30;
                    }
                    StringBuilder connected = new StringBuilder("0");
                    int jmin = Math.max(0, j - 4);
                    for (int pj = j - 1; pj >= jmin; pj--) {
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Interger_pair valueleft = GoBangFrame.map3.get(connected.toString());
                    if (valueleft != null) {
                        gf.theValue[i][j] += valueleft.value;
                    }

                    connected = new StringBuilder("0");
                    int jmax = Math.min(14, j + 4);
                    for (int pj = j + 1; pj <= jmax; pj++) {
                        connected.append(gf.isPiece[i][pj]);
                    }
                    Interger_pair valueright = GoBangFrame.map3.get(connected.toString());
                    if (valueright != null) {
                        gf.theValue[i][j] += valueright.value;
                    }

                    gf.theValue[i][j] += unionValue2_0(valueleft, valueright);


                    connected = new StringBuilder("0");
                    int imin = Math.max(0, i - 4);
                    for (int pi = i - 1; pi >= imin; pi--) {
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Interger_pair valueup = GoBangFrame.map3.get(connected.toString());
                    if (valueup != null) {
                        gf.theValue[i][j] += valueup.value;
                    }

                    connected = new StringBuilder("0");
                    int imax = Math.min(14, i + 4);
                    for (int pi = i + 1; pi <= imax; pi++) {
                        connected.append(gf.isPiece[pi][j]);
                    }
                    Interger_pair valuedown = GoBangFrame.map3.get(connected.toString());
                    if (valuedown != null) {
                        gf.theValue[i][j] += valuedown.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueup, valuedown);


                    connected = new StringBuilder("0");
                    for (int p = 1; p <= 4; p++) {
                        if ((i - p >= 0) && (j - p >= 0)) {
                            connected.append(gf.isPiece[i - p][j - p]);
                        }
                    }
                    Interger_pair valueleftup = GoBangFrame.map3.get(connected.toString());
                    if (valueleftup != null) {
                        gf.theValue[i][j] += valueleftup.value;
                    }


                    connected = new StringBuilder("0");
                    for (int p = 1; p <= 4; p++) {
                        if ((i + p <= 14) && (j + p <= 14)) {
                            connected.append(gf.isPiece[i + p][j + p]);
                        }
                    }
                    Interger_pair valueRightDown = GoBangFrame.map3.get(connected.toString());
                    if (valueRightDown != null) {
                        gf.theValue[i][j] += valueRightDown.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueleftup, valueRightDown);


                    connected = new StringBuilder("0");
                    for (int p = 1; p <= 4; p++) {
                        if ((i + p <= 14) && (j - p >= 0)) {
                            connected.append(gf.isPiece[i + p][j - p]);
                        }
                    }
                    Interger_pair valueLeftDown = GoBangFrame.map3.get(connected.toString());
                    if (valueLeftDown != null) {
                        gf.theValue[i][j] += valueLeftDown.value;
                    }

                    connected = new StringBuilder("0");
                    for (int p = 1; p <= 4; p++) {
                        if ((i - p >= 0) && (j + p <= 14))
                            connected.append(gf.isPiece[i - p][j + p]);
                    }
                    Interger_pair valueRightUp = GoBangFrame.map3.get(connected.toString());
                    if (valueRightUp != null) {
                        gf.theValue[i][j] += valueRightUp.value;
                    }
                    gf.theValue[i][j] += unionValue2_0(valueLeftDown, valueRightUp);
                }
            }
        }
        ArrayList<Integer> maxi = new ArrayList<>();
        ArrayList<Integer> maxj = new ArrayList<>();
        int max = 0;
        maxi.add(0);
        maxj.add(0);
        ArrayList<Integer> morei = new ArrayList<>();
        ArrayList<Integer> morej = new ArrayList<>();
        int more = 0;
        morei.add(0);
        morej.add(0);
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                if(gf.theValue[i][j] > max){
                    more = max;
                    max = gf.theValue[i][j];
                    morei = maxi;
                    morej = maxj;
                    maxi.clear();
                    maxj.clear();
                    maxi.add(i);
                    maxj.add(j);
                }else if(gf.theValue[i][j] == max){
                    maxi.add(i);
                    maxj.add(j);
                }
            }
        }

        int[] tci = new int[2];
        int[] tcj = new int[2];
        if(maxi.size() > 1) {
            for(int t = 0; t < Math.min(2,maxi.size());t++){
                tci[t] = maxi.get(t);
                tcj[t] = maxj.get(t);
                more = max;
            }
        }else{
            tci[0] = maxi.get(0);
            tcj[0] = maxj.get(0);
            Random rn = new Random();
            int tc = rn.nextInt(morei.size());
            tci[1] = morei.get(tc);
            tcj[1] = morej.get(tc);
        }
        for(int i = 0; i < 15; i ++){
            for(int j = 0; j < 15; j ++){
                gf.theValue[i][j] = 0;
            }
        }
        return new int[]{tci[0], tcj[0], max,tci[1],tcj[1],more};//y和x
    }
    @Override

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int lx = (x/40)*40 + 20;
        int ly = (y/40)*40 + 20;
        Graphics g = gf.getGraphics();
        int ix = (lx - 20)/40;
        int iy = (ly - 20)/40;

        if(gf.type == 0) {//对战玩家
            if (gf.turn != 0) {
                if (gf.mode == 2) {//禁手
                    if (gf.isPiece[iy][ix] != 0) {
                        gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                    } else {
                        if (gf.turn == 1) {
                            if (getType(ix, iy) == 20) {
                                gf.pop("禁手提示", "黑方长连禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else if (getType(ix, iy) == 21) {
                                gf.pop("禁手提示", "黑方三三禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;

                            } else if (getType(ix, iy) == 22) {
                                gf.pop("禁手提示", "黑方四四禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else if (getType(ix, iy) == 23) {
                                gf.pop("禁手提示", "黑方四三三禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else {
                                gf.isPiece[iy][ix] = 1;
                                g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                                gf.chessLocations.add(new ChessLocation(iy, ix));
                                gf.turn++;
                                int tmp0 = win(ix, iy, 1);
                                if (tmp0 == 1) {
                                    gf.turn = 0;
                                    gf.pop("游戏结束", "黑方获胜");
                                    gf.isRestart = 2;

                                }
                            }
                        } else if (gf.turn == 2) {
                            g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 2;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn--;
                            int tmp1 = win(ix, iy, 2);
                            if (tmp1 == 2) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "白方获胜");
                                gf.isRestart = 2;
                            }
                        }
                        if (gf.mode == 1 && gf.chessLocations.size() == 3) {
                            gf.Exchange();
                        }
                    }
                }
                else {
                    if (gf.isPiece[iy][ix] != 0) {
                        gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                    } else {
                        if (gf.turn == 1) {
                            g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 1;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn++;
                            int tmp0 = win(ix, iy, 1);
                            if (tmp0 == 1) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "黑方获胜");
                                gf.isRestart = 2;
                            }
                        } else if (gf.turn == 2) {
                            g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 2;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn--;
                            int tmp1 = win(ix, iy, 2);
                            if (tmp1 == 2) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "白方获胜");
                                gf.isRestart = 2;
                            }

                        }
                        if (gf.mode == 1 && gf.chessLocations.size() == 3) {
                            gf.Exchange();
                        }

                    }
                }
            }
        }
        else if(gf.type == 1){//对战AI
            if(gf.turn != 0) {
                if (gf.mode == 2) {
                    if (gf.isPiece[iy][ix] != 0) {
                        gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                    }
                    else {
                        if (gf.turn == 1) {
                            if (getType(ix, iy) == 20) {
                                gf.pop("禁手提示", "黑方长连禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else if (getType(ix, iy) == 21) {
                                gf.pop("禁手提示", "黑方三三禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else if (getType(ix, iy) == 22) {
                                gf.pop("禁手提示", "黑方四四禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else if (getType(ix, iy) == 23) {
                                gf.pop("禁手提示", "黑方四三三禁手，白方获胜");
                                gf.isRestart = 2;
                                gf.turn = 0;
                            } else {
                                gf.isPiece[iy][ix] = 1;
                                g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                                gf.chessLocations.add(new ChessLocation(iy, ix));
                                gf.turn++;
                                int tmp0 = win(ix, iy, 1);
                                if (tmp0 == 1) {
                                    gf.turn = 0;
                                    gf.pop("游戏结束", "黑方获胜");
                                    gf.isRestart = 2;

                                }
                            }
                            if(gf.turn != 0) {
                                int[] a = normal_getPoistion2_0();
                                int[] ai = decision_tree2_0(5,a[0],a[1],a[2],a[3],a[4],a[5]);
                                int laix = ai[1] * 40 + 20;
                                int laiy = ai[0] * 40 + 20;

                                g.drawImage(white, laix - size / 2, laiy - size / 2, size, size, null);
                                gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                gf.isPiece[ai[0]][ai[1]] = 2;
                                gf.turn--;
                                int tmp1 = win(ai[1], ai[0], 2);
                                if (tmp1 == 2) {
                                    gf.turn = 0;
                                    gf.pop("游戏结束", "白方 AI 获胜");
                                    gf.isRestart = 2;
                                }
                            }
                        }
                        else if(gf.turn == 2) {
                            g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 2;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn--;
                            int tmp1 = win(ix, iy, 2);
                            if (tmp1 == 2) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "白方获胜");
                                gf.isRestart = 2;
                            }
                            if(gf.turn != 0) {
                                int[] a = normal_getPoistion2_1();
                                int[] ai = decision_tree2_1(6,a[0],a[1],a[2],a[3],a[4],a[5]);
                                int laix = ai[1] * 40 + 20;
                                int laiy = ai[0] * 40 + 20;
                                if (getType(ai[1], ai[0]) == 20) {
                                    gf.pop("禁手提示", "黑方长连禁手，白方获胜");
                                    gf.isRestart = 2;
                                    gf.turn = 0;
                                } else if (getType(ai[1], ai[0]) == 21) {
                                    gf.pop("禁手提示", "黑方三三禁手，白方获胜");
                                    gf.isRestart = 2;
                                    gf.turn = 0;
                                } else if (getType(ai[1], ai[0]) == 22) {
                                    gf.pop("禁手提示", "黑方四四禁手，白方获胜");
                                    gf.isRestart = 2;
                                    gf.turn = 0;
                                } else if (getType(ai[1], ai[0]) == 23) {
                                    gf.pop("禁手提示", "黑方四三三禁手，白方获胜");
                                    gf.isRestart = 2;
                                    gf.turn = 0;
                                }
                                if(gf.turn != 0) {
                                    g.drawImage(black, laix - size / 2, laiy - size / 2, size, size, null);
                                    gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                    gf.isPiece[ai[0]][ai[1]] = 1;
                                    gf.turn++;
                                    tmp1 = win(ai[1], ai[0], 1);
                                    if (tmp1 == 1) {
                                        gf.turn = 0;
                                        gf.pop("游戏结束", "黑方 AI 获胜");
                                        gf.isRestart = 2;
                                    }
                                }
                            }
                        }
                    }
                }
                else if(gf.mode == 0){
                    if (gf.isPiece[iy][ix] != 0) {
                        gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                    } else {
                        if (gf.turn == 1) {
                            g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 1;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn++;
                            int tmp0 = win(ix, iy, 1);
                            if (tmp0 == 1) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "黑方 player 获胜");
                                gf.isRestart = 2;
                            }
                            if(gf.turn != 0) {
                                int[] a = normal_getPoistion0();
                                int[] ai = decision_tree0(8,a[0],a[1],a[2],a[3],a[4],a[5]);
                                int laix = ai[1] * 40 + 20;
                                int laiy = ai[0] * 40 + 20;

                                g.drawImage(white, laix - size / 2, laiy - size / 2, size, size, null);
                                gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                gf.isPiece[ai[0]][ai[1]] = 2;
                                gf.turn--;
                                int tmp1 = win(ai[1], ai[0], 2);
                                if (tmp1 == 2) {
                                    gf.turn = 0;
                                    gf.pop("游戏结束", "白方 AI 获胜");
                                    gf.isRestart = 2;
                                }
                            }
                        }
                        else if(gf.turn == 2){
                            g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                            gf.isPiece[iy][ix] = 2;
                            gf.chessLocations.add(new ChessLocation(iy, ix));
                            gf.turn--;
                            int tmp1 = win(ix, iy, 2);
                            if (tmp1 == 2) {
                                gf.turn = 0;
                                gf.pop("游戏结束", "白方获胜");
                                gf.isRestart = 2;
                            }
                            if(gf.turn != 0) {
                                int[] a = normal_getPoistion1();
                                int[] ai = decision_tree1(6, a[0], a[1], a[2], a[3], a[4], a[5]);
                                int laix = ai[1] * 40 + 20;
                                int laiy = ai[0] * 40 + 20;
                                g.drawImage(black, laix - size / 2, laiy - size / 2, size, size, null);
                                gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                gf.isPiece[ai[0]][ai[1]] = 1;
                                gf.turn++;
                                tmp1 = win(ai[1], ai[0], 1);
                                if (tmp1 == 1) {
                                    gf.turn = 0;
                                    gf.pop("游戏结束", "黑方 AI 获胜");
                                    gf.isRestart = 2;
                                }
                            }
                        }
                    }

                }
                else if(gf.mode == 1) {
                        if (gf.chessLocations.size() < 3) {
                            if (gf.isPiece[iy][ix] != 0) {
                                gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                            } else {
                                if (gf.turn == 1) {
                                    g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                                    gf.isPiece[iy][ix] = 1;
                                    gf.chessLocations.add(new ChessLocation(iy, ix));
                                    gf.turn++;
                                } else if (gf.turn == 2) {
                                    g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                                    gf.isPiece[iy][ix] = 2;
                                    gf.chessLocations.add(new ChessLocation(iy, ix));
                                    gf.turn--;

                                }
                                if (gf.chessLocations.size() == 3) {
                                    int[] ai0 = normal_getPoistion0();
                                    int[] a = decision_tree0(8, ai0[0], ai0[1], ai0[2], ai0[3], ai0[4], ai0[5]);
                                    if (a[2] > 90) {//大于80，AI会选择交换
                                        gf.pop("提示", "AI决定交换执子");
                                        gf.AI_choose = 1;
                                    } else if (a[2] >= 80) {
                                        Random rn = new Random();
                                        int getchoose = rn.nextInt(2);
                                        if (getchoose == 0) {
                                            gf.pop("提示", "AI决定交换执子");
                                            gf.AI_choose = 1;
                                        } else {
                                            gf.pop("提示", "AI决定不交换持子");
                                            gf.AI_choose = 0;
                                            if (gf.turn != 0) {
                                                int[] ai1 = normal_getPoistion0();
                                                int[] ai = decision_tree0(8, ai1[0], ai1[1], ai1[2], ai1[3], ai1[4], ai1[5]);
                                                int laix = ai[1] * 40 + 20;
                                                int laiy = ai[0] * 40 + 20;

                                                g.drawImage(white, laix - size / 2, laiy - size / 2, size, size, null);
                                                gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                                gf.isPiece[ai[0]][ai[1]] = 2;
                                                gf.turn--;
                                            }
                                        }
                                    } else {
                                        gf.pop("提示", "AI决定不交换持子");
                                        gf.AI_choose = 0;
                                        if (gf.turn != 0) {
                                            int[] at = normal_getPoistion0();
                                            int[] ai = decision_tree0(8, at[0], at[1], at[2], at[3], at[4], at[5]);
                                            int laix = ai[1] * 40 + 20;
                                            int laiy = ai[0] * 40 + 20;

                                            g.drawImage(white, laix - size / 2, laiy - size / 2, size, size, null);
                                            gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                            gf.isPiece[ai[0]][ai[1]] = 2;
                                            gf.turn--;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            if (gf.AI_choose == 0) {
                                if (gf.isPiece[iy][ix] != 0) {
                                    gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                                } else {
                                    if (gf.turn == 1) {
                                        g.drawImage(black, lx - size / 2, ly - size / 2, size, size, null);
                                        gf.isPiece[iy][ix] = 1;
                                        gf.chessLocations.add(new ChessLocation(iy, ix));
                                        gf.turn++;
                                        int tmp0 = win(ix, iy, 1);
                                        if (tmp0 == 1) {
                                            gf.turn = 0;
                                            gf.pop("游戏结束", "黑方 player 获胜");
                                            gf.isRestart = 2;
                                        }
                                        if (gf.turn != 0) {
                                            int[] a = normal_getPoistion0();
                                            int[] ai = decision_tree0(8, a[0], a[1], a[2], a[3], a[4], a[5]);
                                            int laix = ai[1] * 40 + 20;
                                            int laiy = ai[0] * 40 + 20;

                                            g.drawImage(white, laix - size / 2, laiy - size / 2, size, size, null);
                                            gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                            gf.isPiece[ai[0]][ai[1]] = 2;
                                            gf.turn--;
                                            int tmp1 = win(ai[1], ai[0], 2);
                                            if (tmp1 == 2) {
                                                gf.turn = 0;
                                                gf.pop("游戏结束", "白方 AI 获胜");
                                                gf.isRestart = 2;
                                            }
                                        }
                                    }
                                }
                            } else if (gf.AI_choose == 1) {
                                if (gf.isPiece[iy][ix] != 0) {
                                    gf.pop("错误提示", "此处已有棋子，请于其他地方落子");
                                } else {
                                    if (gf.turn == 2) {
                                        g.drawImage(white, lx - size / 2, ly - size / 2, size, size, null);
                                        gf.isPiece[iy][ix] = 2;
                                        gf.chessLocations.add(new ChessLocation(iy, ix));
                                        gf.turn--;
                                        int tmp0 = win(ix, iy, 2);
                                        if (tmp0 == 2) {
                                            gf.turn = 0;
                                            gf.pop("游戏结束", "白方 player 获胜");
                                            gf.isRestart = 2;
                                        }
                                        if (gf.turn != 0) {
                                            int[] a = normal_getPoistion1();
                                            int[] ai = decision_tree1(8, a[0], a[1], a[2], a[3], a[4], a[5]);
                                            int laix = ai[1] * 40 + 20;
                                            int laiy = ai[0] * 40 + 20;

                                            g.drawImage(black, laix - size / 2, laiy - size / 2, size, size, null);
                                            gf.chessLocations.add(new ChessLocation(ai[0], ai[1]));
                                            gf.isPiece[ai[0]][ai[1]] = 1;
                                            gf.turn++;
                                            int tmp1 = win(ai[1], ai[0], 1);
                                            if (tmp1 == 1) {
                                                gf.turn = 0;
                                                gf.pop("游戏结束", "黑方 AI 获胜");
                                                gf.isRestart = 2;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
