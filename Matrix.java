import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Matrix extends JPanel implements ActionListener{

    private final int ROWS = 6; // rows in grid
    private final int COLUMNS = 7; // columns in grid
    JButton clear = new JButton("Clear"); // clear grid button
    ArrayList<JButton> Buttons = new ArrayList<>(); // column buttons from 1 to 7
    private final Color P1 = Color.RED;
    private final Color P2 = Color.YELLOW;
    private Color currentColor = P1; // current player, starts with player 1
    private Cell [][] grid = new Cell[ROWS+2][COLUMNS]; // checker's grid
    private JLabel title = new JLabel("Player 1's turn");

    public Matrix()
    {
        setLayout(new BorderLayout());
        Cell c;
        JPanel buttonsPanel = new JPanel(); // column buttons panel
        JPanel gridPanel = new JPanel(); // grid panel
        JPanel clearPanel = new JPanel(); // clear button panel
        JPanel allButtons = new JPanel(); // column + clear buttons panel
        clear.addActionListener(this);
        buttonsPanel.setLayout(new GridLayout(1,7));
        gridPanel.setLayout(new GridLayout(ROWS,COLUMNS));
        allButtons.setLayout(new BorderLayout());

        for(int i=0; i<ROWS; i++) // generates matrix and adds to panel
        {
            for(int j =0; j<COLUMNS ; j++) {
                c = new Cell();
                grid[i][j] = c;
                gridPanel.add(c);
            }
        }

        for(int i=0; i<COLUMNS; i++) // generates buttons and adds to array list
        {
            Buttons.add(new JButton(String.valueOf(i+1)));
            Buttons.get(i).addActionListener(this);
            buttonsPanel.add(Buttons.get(i));
        }

        clearPanel.add(clear); // adds clear button to panel
        allButtons.add(buttonsPanel,BorderLayout.NORTH);  // adds column buttons panel to all buttons panel
        allButtons.add(clearPanel,BorderLayout.SOUTH); // adds clear buttons panel to all buttons panel
        add(title,BorderLayout.NORTH);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(gridPanel,BorderLayout.CENTER); // adds grid to panel
        add(allButtons,BorderLayout.SOUTH); // adds all buttons to panel
    }

    public void actionPerformed(ActionEvent e)
    {
        int k=-5;

        for(int i=0;i<Buttons.size();i++) // checking which button was pressed
        {
            if(e.getSource()==Buttons.get(i)) {
                k = i;
                break;
            }
        }
        if(k>=0) // adding a checker in this column
        {
            addChecker(k);
        }
        else if(e.getSource()==clear) // clear grid was pressed
        {
            clearGrid();
        }
    }

    public void addChecker(int column)
    {
        boolean won;
        int row = ROWS-1;
        int option;
        String winner ="";

        while (row>=0)
        {
            if(grid[row][column].getColor() == Color.WHITE)
            {
                grid[row][column].setColor(currentColor);
                break;
            }
            else row--;
        }

        won = checkWin(row,column);

        if(won)
        {
            if(currentColor == P1) // checking who won
            {
                winner = "Player 1 WON";
            }
            else
                winner = "Player 2 WON";

            option = JOptionPane.showConfirmDialog(null, "Do you want to play" +
                    " again?",winner,JOptionPane.YES_NO_OPTION);

            if(option == 0) // user wants to play again
                clearGrid();

            else System.exit(0);
        }

        if(row == 0) // we filled the last place in this column, disable the button
            blockButton(column);

        if(!checkEnabled() && !won) // all buttons are disabled = grid is full and there's no win = Draw
        {
            option = JOptionPane.showConfirmDialog(null, "Do you want to play" +
                    " again?","It's a DRAW",JOptionPane.YES_NO_OPTION);

            if(option == 0) // wants to play again
                clearGrid();
            else System.exit(0);
        }
        switchPlayer();
    }

    public void clearGrid()
    {
        for(int i=0; i<ROWS; i++)
        {
            for(int j=0; j<COLUMNS; j++)
            {
                grid[i][j].setColor(Color.WHITE); // initializing all cells
            }
        }
        enableButtons();
    }

    private void switchPlayer() // switching between colors + title at the end of a turn
    {
        if(currentColor == P1) {
            title.setText("Player 2's turn");
            currentColor = P2;
        }
        else {
            title.setText("Player 1's turn");
            currentColor = P1;
        }
    }

    private boolean checkWin(int row, int column) // checking if there's a strike
    {
        if(checkRow(row, column)) // check rows win
        {
            return true;
        }
        if(checkColumn(row, column)) // check column win
        {
            return true;
        }
        if(checkDiagonal(row, column)) // check diagonal win
        {
            return true;
        }
        return false;
    }

    private void blockButton(int i)
    {
        Buttons.get(i).setEnabled(false); // when a column is full, blocking the option to fill it
    }

    private boolean checkRow(int row, int column) // checking row strike
    {
        int count = 0;
        int checkFrom = column-3;
        int checkTo = column+3;
        if(checkFrom<0)
            checkFrom = 0;
        if(checkTo>=COLUMNS)
            checkTo= COLUMNS-1;
        for(int i = checkFrom; i<=checkTo ; i++)
        {
            if(grid[row][i].getColor()==currentColor)
            {
                count++;
                if (count == 4)
                    return true;
            }
            else count = 0;
        }
        return false;
    }

    private boolean checkColumn(int row, int column) // checking column strike
    {
        int count = 0;
        int checkFrom = row-3;
        int checkTo = row+3;
        if(checkFrom<0)
            checkFrom = 0;
        if(checkTo>=ROWS)
            checkTo= ROWS-1;
        for(int i = checkFrom; i<=checkTo ; i++)
        {
            if(grid[i][column].getColor()==currentColor)
            {
                count++;
                if (count == 4)
                    return true;
            }
            else count = 0;
        }
        return false;
    }

    private boolean checkDiagonal(int row, int column) // checking diagonal strike
    {
        int c = column; // column to check from
        int r = row; // row to check from
        int count = 0;
        while(c>0 && r>0)
        {
            c--;
            r--;
        }

        while(c<COLUMNS && r<ROWS)
        {
            if(grid[r][c].getColor()==currentColor)
            {
                count++;
                if (count == 4)
                    return true;
            }
            else count = 0;
            c++;
            r++;
        }

        c = column;
        r = row;
        count = 0;

        while(c>0 && r<ROWS-1)
        {
            c--;
            r++;
        }

        while(c<COLUMNS && r>=0)
        {
            if(grid[r][c].getColor()==currentColor)
            {
                count++;
                if (count == 4)
                    return true;
            }
            else count = 0;
            c++;
            r--;
        }

        return false;
    }

    private boolean checkEnabled() // checking if some buttons are enabled
    {
        for(int i=0 ; i<Buttons.size() ; i++)
        {
            if(Buttons.get(i).isEnabled())
                return true;
        }
        return false; // all buttons are disabled
    }

    private void enableButtons() // enabling all buttons after draw
    {
        for(int i=0 ; i<Buttons.size() ; i++)
        {
            Buttons.get(i).setEnabled(true);
        }
    }
}
