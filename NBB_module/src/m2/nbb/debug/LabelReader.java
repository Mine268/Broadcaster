package m2.nbb.debug;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * LabelReader类用来读取数据并转化为数组，便于快速读取，主要用于调试
 */
public class LabelReader {
    private String src;
    private int count;
    private byte[] labelMatch;

    /**
     * 接受数据库地址来初始化实例
     * @param src 数据库的位置
     */
    public LabelReader(String src) {
        this.src = src;
        this.loadData();
    }

    /**
     * 转载数据库的数据到labelMatch数组中
     */
    private void loadData() {
        try {
            InputStream file = new FileInputStream(this.src);
            int iAvail = file.available();
            byte[] buffer = new byte[4];

            this.labelMatch = new byte[iAvail - 8];
            file.readNBytes(4);
            file.readNBytes(buffer, 0, 4);
            file.read(this.labelMatch);

            for (int i = 0; i < 4; i++) {
                this.count <<= 8;
                // byte有符号，需要进行转换
                this.count += buffer[i] & 0xff;
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取第index个图像的标签
     * @param index index
     * @return 返回标签值
     */
    public byte getLabel(int index) {
        return this.labelMatch[index];
    }

    /**
     * 返回数据量
     * @return 数据量
     */
    public int getSize() {
        return this.count;
    }

    /**
     * 返回数据文件的名称
     * @return 文件名称
     */
    public String getFileName() {
        return this.src;
    }

    public static void main(String[] args) {
        // 真正使用的时候建议英文路径名+相对路径
        LabelReader l1 = new LabelReader("D:\\study\\人工智能基础\\mnist\\t10k-labels.idx1-ubyte");
    }
}
