package drawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class ImageHandler {
    public ImageHandler(BufferedImage srcImage){
        this.image=srcImage;
        convertToByteArray();
        savePNG();
    }
    public byte[] convertToByteArray(){
        int[] dataINT=((DataBufferInt) image.getData().getDataBuffer()).getData();
        //白色：16777215 黑色：0
        int[][] data2D=new int[560][560];
        for(int i=0;i<313600;i++){
            int x=i/560;//确定行数
            int y=i%560;//确定列数
            data2D[x][y]=dataINT[i];
        }
        for(int i=0;i<560;i+=28){
            for(int j=0;j<560;j+=28){
                //(i,j)为右上顶点，分块处理
                int blackCounter=0;
                for(int m=i;m<i+28;m++){
                    for(int n=j;n<j+28;n++){
                        if(data2D[m][n]==0)blackCounter++;
                    }
                }
                int x=i/28,y=j/28;//转化为2D坐标情况
                if(blackCounter>=FILL_RATE) output[x*28+y]=(byte)-255;
                else output[x*28+y]=0;
            }
        }
        for(int x=0;x<28;x++){//打印output
            for(int y=0;y<28;y++){
                System.out.print(output[x*28+y]+" ");
            }System.out.println();
        }
        return output;
    }
    public void savePNG(){
        String filename= String.valueOf(System.currentTimeMillis());
        filename+=".png";
        File outputfile = new File(filename);
        try {
            ImageIO.write(image,"png",outputfile);
            JOptionPane.showMessageDialog(null,"Saved as PNG in the rootDir!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BufferedImage image;
    private byte[] output= new byte[28*28];
    private static int FILL_RATE=10;
}
