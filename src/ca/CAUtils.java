package ca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author tadaki
 */
public class CAUtils {

    private CAUtils() {
    }

    /**
     * ルール番号に対応した写像を作成
     *
     * @param ruleNumber
     * @return 写像
     */
    public static int[] mkRuleMap(int ruleNumber) {
        int newRuleMap[] = new int[8];
        for (int i = 0; i < 8; i++) {//ruleNumberの下位ビットから調べる
            int r = 1 & ruleNumber;//最下位ビットの値を求める
            newRuleMap[i] = r;//その値をnewRuleMap[i]に代入する        
            ruleNumber = ruleNumber >> 1;//右に1ビットシフト（2で割る）
        }
        return newRuleMap;
    }
    
    /**
     * 状態を文字列に変換：0->" ", 1->"*";
     *
     * @param cells
     * @return
     */
    public static String state2String(int cells[]) {
        String symbol[] = {" ", "*"};
        var sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            sb.append(symbol[cells[i]]);
        }
        return sb.toString();
    }

    /**
     * ruleMapを確認するための文字列生成
     *
     * @param ruleMapDummy
     * @return
     */
    public static String ruleMap2String(int ruleMapDummy[]) {
        //改行コード(OS毎に異なる)
        String nl = System.getProperty("line.separator");

        var sb = new StringBuilder();
        sb.append("rule-").append(ruleMap2Int(ruleMapDummy)).append(nl);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    int r = 4 * i + 2 * j + k;
                    sb.append("(").append(i).append(j).append(k).append(")->");
                    sb.append(ruleMapDummy[r]).append(nl);
                }
            }
        }
        return sb.toString();
    }

    /**
     * ruleMapを整数に変換
     *
     * @param ruleMapDummy
     * @return
     */
    public static int ruleMap2Int(int ruleMapDummy[]) {
        int k = 0;
        int m = 1;
        for (int i = 0; i < 8; i++) {
            k += m * ruleMapDummy[i];
            m *= 2;
        }
        return k;
    }
    
    /**
     * 0 からn-1をでたらめに並べ替えたリストを返す
     * @param n
     * @param random
     * @return 
     */
    public static List<Integer> createRandomList(int n, Random random){
        List<Integer> ar = new ArrayList<>();
        for(int i=0;i<n;i++){
            ar.add(i);
        }
        Collections.shuffle(ar, random);
        return ar;
    }
}
