package m2.nbb.debug;

import m2.nbb.data.DataLoader;
import m2.nbb.debug.img.ImageReader;

// for debug
public class Main {
    public static void main(String[] args) {
        DataLoader dl = new DataLoader("D:\\study\\人工智能基础\\mnist\\train-labels.idx1-ubyte",
                "D:\\study\\人工智能基础\\mnist\\train-images.idx3-ubyte", 10);
        ImageReader ir = new ImageReader("D:\\study\\人工智能基础\\mnist\\t10k-images.idx3-ubyte");
        LabelReader lr = new LabelReader("D:\\study\\人工智能基础\\mnist\\t10k-labels.idx1-ubyte");

        int size = lr.getSize();
        int success = 0, failure = 0;
        for (int k = 0; k < size; k++) {
            int num = -1;
            double max = Double.MIN_VALUE;
            for (int i = 0; i < 10; i++) {
                double tmp = dl.getClassCondProb(ir.getImgVector(k), i);
                if (tmp > max) {
                    max = tmp;
                    num = i;
                }
            }
            if (num == lr.getLabel(k)) success++;
            else failure++;
        }
        System.out.println("success: " + success + "\nfailure: " + failure
        + "\nrate: " + (double)success / (success + failure));
    }
}

/*
*  驱动器 D 中的卷是 新加卷
 卷的序列号是 8049-F4E4

 D:\study\人工智能基础\mnist 的目录

2020/12/09  19:42    <DIR>          .
2020/12/09  19:42    <DIR>          ..
2020/12/09  19:55    <DIR>          pre-process
2020/12/02  11:30         1,648,877 t10k-images-idx3-ubyte.gz
1998/01/26  23:07         7,840,016 t10k-images.idx3-ubyte
2020/12/02  11:30             4,542 t10k-labels-idx1-ubyte.gz
1998/01/26  23:07            10,008 t10k-labels.idx1-ubyte
2020/12/02  11:30         9,912,422 train-images-idx3-ubyte.gz
1996/11/18  23:36        47,040,016 train-images.idx3-ubyte
2020/12/02  11:30            28,881 train-labels-idx1-ubyte.gz
1996/11/18  23:36            60,008 train-labels.idx1-ubyte
               8 个文件     66,544,770 字节
               3 个目录 14,223,646,720 可用字节
* */