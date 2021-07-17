import javax.swing.*;
import java.awt.*;


public class Cell extends JPanel{

    protected Color color; // refers to the current player's color

    public Cell()
    {
        color = Color.WHITE;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLUE);
        g.setColor(color);
        g.fillOval(0,0,getWidth(),getHeight());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
}
