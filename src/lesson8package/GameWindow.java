package lesson8package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WIN_HEIGHT = 555;
    private static final int WIN_WIDTH = 500;
    private static final int WIN_POS_X = 500;
    private static final int WIN_POS_Y = 100;

    private static StartNewGameWindow startNewGameWindow;
    private static Map field;

    public GameWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WIN_POS_X, WIN_POS_Y, WIN_WIDTH, WIN_HEIGHT);
        setTitle("Cross'n'Null");

        setResizable(false);

        JPanel bottomPanel = new JPanel(new GridBagLayout());

        JButton buttomNewGame = new JButton("Start New Game");
        JButton buttomExit = new JButton("Exit");

        bottomPanel.add(buttomNewGame);
        bottomPanel.add(buttomExit);

        add(bottomPanel,BorderLayout.SOUTH);

        startNewGameWindow = new StartNewGameWindow(this);

        buttomNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGameWindow = new StartNewGameWindow(GameWindow.this);
                startNewGameWindow.setVisible(true);
            }
        });

        buttomExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

}
