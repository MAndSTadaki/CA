
import ca.CA;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

/**
 * コマンドラインからのCAクラスの実行
 *
 * @author tadaki
 */
public class CLIMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int n = 50;//セル数
        int rule = 184;//ルール番号
        int tmax = 20;//繰り返し時間
        CA ca = new CA(n, rule, new Random(48L));//CAインスタンスを生成
//        ca.initialSingle();//一つだけ1にした初期条件
        ca.initialize(0.55);
        String filename = "rule-" + String.valueOf(rule) + ".txt";
        try ( PrintStream out = new PrintStream(new FileOutputStream(filename))) {
            out.println(ca.state2String());
            for (int t = 0; t < tmax; t++) {
                ca.update();
                out.println(ca.state2String());
            }
        }
    }

}
