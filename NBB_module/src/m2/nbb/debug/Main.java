package m2.nbb.debug;

import m2.nbb.data.DataLoader;
import m2.nbb.debug.img.ImageReader;

// for debug
public class Main {
    public static void main(String[] args) {
        // DataLoader用于调用
        // srcLabel: 标签数据的位置
        // srcImage: 图像数据的位置
        // N: 10
        DataLoader dl = new DataLoader("D:\\study\\人工智能基础\\mnist\\train-labels.idx1-ubyte",
                "D:\\study\\人工智能基础\\mnist\\train-images.idx3-ubyte", 10);
        // ImageReader和LabelReader用于调试，不用理会
        ImageReader ir = new ImageReader("D:\\study\\人工智能基础\\mnist\\t10k-images.idx3-ubyte");
        LabelReader lr = new LabelReader("D:\\study\\人工智能基础\\mnist\\t10k-labels.idx1-ubyte");

        // dl.getJudgementFactor(a, b)获取分类概率
        // a就是byte[]数组传入位置，e.g.
        // 表示这个byte数组代表的数字为4的条件概率
        // 只需要遍历dl.getJudgementFactor(new byte[]{1,2,3,4,5}, 0); dl.getJudgementFactor(new byte[]{1,2,3,4,5}, 1);
        // ...dl.getJudgementFactor(new byte[]{1,2,3,4,5}, 9); 找出其中的最大值对应的h即为结果
        // 统计正确率
        int size = lr.getSize();
        int success = 0, failure = 0;
        for (int k = 0; k < size; k++) {
            int num = -1;
            double max = Double.MIN_VALUE;
            for (int i = 0; i < 10; i++) {
                double tmp = dl.getJudgementFactor(ir.getImgVector(k), i);
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