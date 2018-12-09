package kohn.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JComponent implements MouseMotionListener, MouseListener {

    private List<Shape> shapes = new ArrayList<>();
    private Color color = Color.black;

    private Tool tool;
    private BufferedImage buffImage;

    public Canvas() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        buffImage = new BufferedImage(700, 550, BufferedImage.TYPE_INT_ARGB);

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Shape shape : shapes) {
            shape.draw(graphics);
        }

        paintImage(graphics);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        tool.setColor(color);
        tool.mousePressed(e.getPoint());
        shapes.add(tool.getShape());

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        tool.mouseDragged(e.getPoint());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        tool.mouseReleased(e.getPoint());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }


    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    public void setTool(Tool tool){
        this.tool = tool;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void undo(){
        int size = shapes.size()-1;
        if(shapes.size() > 0){
            shapes.remove(size);
            repaint();
        }

    }


    private void paintImage(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        int w = buffImage.getWidth();
        int h = buffImage.getHeight();
        g2.drawImage(buffImage, 0, 0, w, h, null);
        g2.dispose();
        revalidate();
        repaint();

    }


    public void setImage(BufferedImage bufferedImage) {
        buffImage = bufferedImage;
    }

    public void saveObject(){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("shapes.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(shapes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void restoreObject(){

    }


}
