package rule184;

import ca.CA;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 観測量を表す抽象クラス
 *
 * 密度を変えながらシミュレーションを実施しその結果（流量、速度）を得る
 */
abstract public class Observable {

    protected CA ca;
    protected final int n;

    /**
     * コンストラクタ
     *
     * @param n サイト数
     * @param random
     */
    public Observable(int n, Random random) {
        ca = new CA(n, 184, random);
        this.n = n;
    }

    /**
     * 平均量の取得
     *
     * @param numCar 車両数
     * @return 平均量
     */
    abstract public double calcValue(int numCar);

    /**
     * 平均量の取得
     *
     * @param dp 密度の間隔
     * @param tmax 平均を行う時間
     * @return 密度に対する平均量のリスト
     */
    public List<Point2D.Double> calcValues(double dp, int tmax) {
        List<Point2D.Double> pList = Collections.synchronizedList(new ArrayList<>());
        int k = (int) (1. / dp);
        pList.add(new Point2D.Double(0., 0.));
        for (int i = 1; i < k; i++) {
            double p = i * dp;//初期密度
            int numCar = (int) (n * p);
            initializeAndRelax(numCar, tmax);
            double value = calcValue(numCar);//平均量
            p = (double) numCar / n;//実際の密度を再計算
            pList.add(new Point2D.Double(p, value));
        }
        pList.add(new Point2D.Double(1., 0.));
        return pList;
    }

    /**
     * 初期化して緩和
     *
     * @param numCar 車両数
     * @param tmax 緩和させる時間
     */
    protected void initializeAndRelax(int numCar, int tmax) {
        ca.initialize(numCar);//初期化
        //緩和
        for (int t = 0; t < tmax; t++) {
            ca.update();
        }
    }
}
