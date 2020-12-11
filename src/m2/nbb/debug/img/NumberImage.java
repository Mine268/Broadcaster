package m2.nbb.debug.img;

/***
 * NumberImage用于储存单个图像，图像会被储存为
 * 二维数组形式和一维数组形式，同时具有输出图像功能
 */
public class NumberImage {
    private int rows, cols;
    private byte[][] img;
    private byte[] imgVector;

    /***
     * 根据给定的行列宽度，原始的图像数据，和数据的起始位置
     * 初始化这个图形
     * @param rows 图形的行数
     * @param cols 图像的列数
     * @param bytes 原始数据
     */
    NumberImage(int rows, int cols, byte[] bytes) {
        this.rows = rows;
        this.cols = cols;
        this.img = new byte[rows][cols];
        this.imgVector = new byte[rows * cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                this.img[i][j] = bytes[i * cols + j];
                this.imgVector[i * cols + j] = bytes[i * cols + j];
            }
    }

    /***
     * 返回图像相向量
     * @return 图像向量
     */
    public byte[] getImgVector() {
        return this.imgVector;
    }

    /***
     * 用于绘制这一个图形
     */
    public void drawImg() {
        drawImg('X', 'O');
    }

    /***
     * 用于绘制一个图形，指定不同的像素点的绘制方式
     * @param yes 如果像素点大于0的绘制方式
     * @param no 如果像素点小于等于0的绘制方式
     */
    public void drawImg(char yes, char no) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++)
                if (this.img[i][j] != 0) System.out.print(yes);
                else System.out.print(no);
//                System.out.printf("%4s", String.valueOf(this.img[i][j]));
            System.out.println("");
        }
    }
}
