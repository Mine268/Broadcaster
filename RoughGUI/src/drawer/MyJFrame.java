package drawer;

import m2.nbb.data.DataLoader;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MyJFrame extends JFrame {

    BufferedImage image = new BufferedImage(560, 560, BufferedImage.TYPE_INT_BGR);
    Graphics gs = image.getGraphics();
    Graphics2D g = (Graphics2D) gs;
    MyCanvas canvas = new MyCanvas();
    Color foreColor = Color.black;
    Color backgroundColor = Color.white;

    int x = -1;int y = -1;
    boolean rubber = false;
    private JToolBar toolBar;
    private JButton clearButton;
    private JButton saveButton;

    public MyJFrame() {
        setResizable(false);//设为不可更改大小
        setTitle("朴素贝叶斯手写数字识别");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 100, 560, 560);
        init();//初始化画板
        addListener();//添加监听事件
    }

    private void init() {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 560, 560);//矩形覆盖填充
        g.setColor(foreColor);
        canvas.setImage(image);
        getContentPane().add(canvas);
        g.setStroke(new BasicStroke(30, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));//画笔粗细
        toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);//将工具栏放在窗体上边

        /*
         * 设置各组件位置
         */
        saveButton = new JButton("识别");
        toolBar.add(saveButton);
        toolBar.addSeparator();

        clearButton = new JButton("清除");
        toolBar.add(clearButton);
    }

    private void addListener() {
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(final MouseEvent e) {
                if (x > 0 && y > 0) {
                    if (rubber) {
                        g.setColor(backgroundColor);
                        g.fillRect(x, y, 20, 20);
                    } else {
                        g.drawLine(x, y, e.getX(), e.getY());
                    }
                }
                x = e.getX();
                y = e.getY();
                canvas.repaint();
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            public void mouseReleased(final MouseEvent arg0) {
                x = -1;
                y = -1;
            }
        });

        clearButton.addActionListener(e -> {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, 560, 560);
            g.setColor(foreColor);
            canvas.repaint();

        });

        saveButton.addActionListener(e -> {
            new ImageHandler(image);
        });
    }
    public static void main(String[] args) {

        MyJFrame frame = new MyJFrame();
        frame.setVisible(true);

//        DataLoader dl = new DataLoader("./train-labels.idx1-ubyte", "./train-images.idx3-ubyte", 10);
//        int h = dl.getResult(new byte[]{2, 3});
    }

}