package ca;

import java.util.List;
import java.util.Random;

/**
 * Walframの1次元セルオートマトン 周期境界条件
 */
public class CA {

    private final int cells[];//状態を表す配列
    private final int n;//セルの総数
    private int[] ruleMap;//ルール番号に対応した写像
    private int numDifference = 0;//一回の更新で値に変更のあったセルの数
    private int numSiteWithOne;//初期に値が1であるセルの数
    private final Random random;

    /**
     * コンストラクタ
     *
     * @param n セル総数
     * @param rule ルール番号
     * @param random
     */
    public CA(int n, int rule, Random random) {
        this.n = n;
        cells = new int[n];
        rule = rule % 256;
        ruleMap = CAUtils.mkRuleMap(rule);//ルールを表す写像の設置
        this.random = random;
    }

    /**
     * ルール番号の設定
     *
     * @param rule ルール番号
     */
    public void setRule(int rule) {
        ruleMap = CAUtils.mkRuleMap(rule);
    }

    /**
     * 初期化 確率rで1をでたらめにばらまく
     *
     * @param r 1 とする確率
     */
    public void initialize(double r) {
        initialize((int) (n * r));
    }

    public void initialize() {
        initialize(0.5);
    }

    /**
     * 初期化 指定した個数のセルをでたらめにえらび、1とする
     *
     * @param numSiteWithOne
     */
    public void initialize(int numSiteWithOne) {
        this.numSiteWithOne = Math.min(n, numSiteWithOne);
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        if (numSiteWithOne <= 1) {
            initialSingle();
        } else {
            List<Integer> ar = CAUtils.createRandomList(n, random);
            for (int i = 0; i < numSiteWithOne; i++) {
                cells[ar.get(i)] = 1;
            }
        }
    }

    /**
     * 中央だけを1とする初期化
     */
    public void initialSingle() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        cells[(int) (n / 2)] = 1;
    }

    /**
     * 状態更新
     *
     * @return 状態が更新された新しい状態
     */
    public int[] update() {
        int cellsDummy[] = new int[n];//ダミーの状態
        for (int i = 0; i < n; i++) {
            //左右のセルの番号を求める（周期境界に注意）
            int right = (i + 1) % n;//右側
            int left = (i - 1 + n) % n;//左側
            //対象となるセル及び左右のセルの状態を10進数kに変換
            //ruleMap[k]が次の時刻の状態に対応
            int k = 4 * cells[left] + 2 * cells[i] + cells[right];
            cellsDummy[i] = ruleMap[k];//cellsDummyに次の状態を設定
        }
        //状態が変わったセルの数を数える
        numDifference = 0;
        for (int i = 0; i < n; i++) {
            numDifference += (cells[i] + cellsDummy[i]) % 2;
        }
        System.arraycopy(cellsDummy, 0, cells, 0, n);//ダミーをシステムへ複写
        return cellsDummy;
    }

    public String state2String() {
        return CAUtils.state2String(cells);
    }

    public int[] getCells() {
        return cells;
    }

    public int getN() {
        return n;
    }

    public int getNumDifference() {
        return numDifference;
    }

    public int getNumSiteWithOne() {
        return numSiteWithOne;
    }
}
