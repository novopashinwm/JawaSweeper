import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

/**
 * Created by Владимир on 15.12.2017.
 */
public class JavaSweeper extends JFrame {

    private Game game;
    private final int COLS = 16;
    private final int ROWS = 16;
    private final int BOMBS = 40;
    private final int IMAGE_SIZE = 32;

    private JPanel panel;
    private JLabel label;

    public static void main(String[] args) {
        new JavaSweeper();
    }

    private JavaSweeper()
    {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initLabel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel(getMessage());
        Font font = new Font("Tahoma", Font.BOLD, 20);
        label.setFont(font);
        add(label,BorderLayout.SOUTH);
    }

    private void initPanel() {

        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCoords())
                    g.drawImage( game.getBox(coord).image,
                            coord.x* IMAGE_SIZE,
                            coord.y * IMAGE_SIZE,this);

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1: game.pressLeftButton(coord); break;
                    case MouseEvent.BUTTON3: game.pressRightButton(coord); break;
                    case MouseEvent.BUTTON2: game.start(); break;
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE
                                           ,Ranges.getSize().y*IMAGE_SIZE));

        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image= getImage(box.name().toLowerCase());
        setIconImage(getImage("icon"));
    }

    private Image getImage(String name)
    {
        String filename = "img/" + name +".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way


        return newimg;
    }

    private String getMessage() {
        switch (game.getState()){
            case BOMBED: return "Ba-Da-Boom! You Lose!";
            case WINNER: return "Congratulations! All bombs marked!";
            case PLAYED:
            default:
                if (game.getTotalFlaged()==0)
                    return "Welcome!";
                else
                    return "Think twice! Flagged " +
                            game.getTotalFlaged() + " of " +
                            game.getTotalBombs() + " bombs.";
        }
    }


}
