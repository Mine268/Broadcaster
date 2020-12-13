package m2.nbb.debug.img;

import m2.nbb.debug.LabelReader;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * ImageReader用于读取图像数据，主要作用是调试，
 * 不推荐用来读取图像数据以进行贝叶斯估计
 */
public class ImageReader {
    final private String src;
    private int count;
    private NumberImage[] imgs;

    public ImageReader(String src) {
        this.src = src;
        this.loadDate();
    }

    private void loadDate() {
        try {
            InputStream file = new FileInputStream(this.src);
            int row = 0, col = 0;

            byte[] buffer = new byte[8];
            file.readNBytes(4);
            file.readNBytes(buffer, 0, 4);

            // 获取数据数量
            for (int i = 0; i < 4; i++) {
                this.count <<= 8;
                this.count += buffer[i] & 0xff;
            }
            this.imgs = new m2.nbb.debug.img.NumberImage[this.count];

            file.readNBytes(buffer, 0, 8);
            // 维护row col
            for (int i = 0; i < 4; i++) {
                row <<= 8;
                row += buffer[i] & 0xff;
                col <<= 8;
                col += buffer[i + 4] & 0xff;
            }

            buffer = new byte[row * col];
            for (int i = 0; i < this.count; i++) {
                file.readNBytes(buffer, 0, row * col);
                this.imgs[i] = new m2.nbb.debug.img.NumberImage(row, col, buffer);
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印第index幅图像
     * @param index 图像的序号
     */
    public void printImg(int index) {
        this.imgs[index].drawImg();
    }

    /**
     * 打印第index幅图像
     * @param index 图像编号
     * @param yes 如果大于阈值需要打印的字符
     * @param no 如果小于阈值需要打印的字符
     */
    public void printImg(int index, char yes, char no) {
        this.imgs[index].drawImg(yes, no);
    }

    /**
     * 获取第index个图像的图像向量
     * @param index 序号
     * @return 图像向量
     */
    public byte[] getImgVector(int index) {
        return this.imgs[index].getImgVector();
    }

    /**
     * 返回数据个数
     * @return 数据个数
     */
    public int getSize() { return this.count; }

    public static void main(String[] args) {
        LabelReader l1 = new LabelReader("D:\\study\\人工智能基础\\mnist\\t10k-labels.idx1-ubyte");
        ImageReader i1 = new ImageReader("D:\\study\\人工智能基础\\mnist\\t10k-images.idx3-ubyte");
        final int ind = 432;
        System.out.println(l1.getLabel(ind));
        i1.printImg(ind, '■', '□');
    }
}
