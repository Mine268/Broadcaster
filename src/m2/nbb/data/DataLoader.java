package m2.nbb.data;

import m2.nbb.debug.img.NumberImage;

import java.io.InputStream;
import java.io.FileInputStream;

/**
 * 计算类-条件概率
 */
public class DataLoader {
    private String srcLabel;
    private String srcImage;
    // 下标i表示分类为i时这个点上存在数据的个数
    private int[][] data;
    // 用来记录每个类别下的数据的数量
    private int[] count;
    private int data_count;

    public DataLoader(String srcLabel, String srcImage, int N) {
        try {
            this.srcLabel = srcLabel;
            this.srcImage = srcImage;
            this.count = new int[N];

            InputStream fileLabel = new FileInputStream(this.srcLabel),
                    fileImage = new FileInputStream(this.srcImage);
            byte[] buffer1 = new byte[8], buffer2 = new byte[8];
            int rows = 0, cols = 0, c1 = 0, c2 = 0;

            // 读入c1,c2并校验
            fileLabel.readNBytes(buffer1, 0, 8);
            fileImage.readNBytes(buffer2, 0, 8);
            for (int i = 4; i < 8; i++) {
                c1 <<= 8;
                c2 <<= 8;
                c1 += buffer1[i] & 0xff;
                c2 += buffer2[i] & 0xff;
            }
            if (c1 == c2) this.data_count = c1;
            else throw new Exception("标签数据与图像数据的长度不一致。");

            // 读入图像的尺寸
            fileImage.readNBytes(buffer2, 0, 8);
            for (int i = 0; i < 4; i++) {
                rows <<= 8;
                cols <<= 8;
                rows += buffer2[i] & 0xff;
                cols += buffer2[i] & 0xff;
            }
            // 初始化data成员数组
            this.data = new int[N][rows * cols];

            // 开始读入并统计数据
            buffer1 = new byte[1];
            buffer2 = new byte[rows * cols];
            int len = rows * cols;
            for (int i = 0; i < this.data_count; i++) {
                fileLabel.readNBytes(buffer1, 0, 1);
                fileImage.readNBytes(buffer2, 0, len);
                this.count[buffer1[0]]++;
                for (int j = 0; j < len; j++)
                    if (buffer2[j] != (byte) 0)
                        this.data[buffer1[0]][j]++;
            }

            fileImage.close();
            fileLabel.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据D下的条件概率
     * @param D 数据，这是一个向量
     * @param h 某一归类h
     * @return 返回条件概率
     */
    public double getClassCondProb(byte[] D, int h) {
        double res = 1.;
        for (int j = 0; j < D.length; j++) {
            double tmp = this.getClassCondProb_Sub(j, D[j], h);
            if (tmp != 1.) res *= tmp * 1.3;
        }
        return res;
    }

    /**
     * 获取类-条件概率的乘法分量P(d_i|h_k)
     * @param d 第d维下的数据
     * @param val D中第d维下的数据值
     * @param h 归类为h下
     * @return 返回条件概率分量
     */
    private double getClassCondProb_Sub(int d, int val, int h) {
        if(val != 0) return (double)this.data[h][d] / this.count[h];
        else return 1. - (double)this.data[h][d] / this.count[h];
    }
}
