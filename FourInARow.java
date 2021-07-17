import javax.swing.*;

public class FourInARow {
    public static void main(String[] args) {
        Matrix m = new Matrix();
        JFrame f = new JFrame("4 IN A ROW");
        f.setSize(600,600);
        f.add(m);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
