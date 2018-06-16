import java.io.IOException;

/**
 * Created by Yifan on 2017/10/9.
 */
public class main {
    public static void main(String [] args) throws IOException {
        String traintxtPath = "./data/test/test.txt";
        String trainarffPath = "./data/test/test.arff";
        String testArffPath = "./data/test/test_DS.arff";
//        String traintxtPath = "./data/train/train.txt";
//        String trainarffPath = "./data/train/train.arff";
//        String testArffPath = "./data/train/train_DS.arff";
        FileOperation fo = new FileOperation();
        fo.readAttr(trainarffPath);
        fo.ReadTXTWriteARFF(traintxtPath,trainarffPath,testArffPath);
    }
}
