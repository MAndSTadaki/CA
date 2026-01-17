package rule184;

import java.awt.geom.Point2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

/**
 * 流量を求めるクラス
 */
public class Flow extends Observable {

    /**
     * セルの数を与えて初期化するコンストラクタ
     *
     * @param n
     * @param random
     */
    public Flow(int n, Random random) {
        super(n, random);
    }

    /**
     * 流量を求める
     *
     * @param numCar 車両数
     * @return 平均速度
     */
    @Override
    public double calcValue(int numCar) {
        int num = ca.getNumDifference() / 2;//移動した車両数
        return ((double) num) / (n);//流量を返す
    }

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        int n = 100;
        double dp = 0.02;
        int tmax = 100;
        Flow sys = new Flow(n, new Random(48L));
        List<Point2D.Double> points = sys.calcValues(dp, tmax);
        String filename = "fundamental.txt";
        try ( PrintStream out = new PrintStream(new FileOutputStream(filename))) {
            for (Point2D.Double p : points) {
                out.println(p.x + " " + p.y);
            }
        }
    }

}
