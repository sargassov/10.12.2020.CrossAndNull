package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GameWindow extends JFrame {

    private static final int WIN_HEIGHT = 555;
    private static final int WIN_WIDTH = 508;
    private static Toolkit toolkit;
    private static Dimension dimension;
    private static StartNewGameWindow startNewGameWindow;
    private static Map field;
    private static JButton btnNewGame = new JButton("Start new game");
    private static JButton btnExit = new JButton("Exit");
    private static JPanel bottomPanel;

    public GameWindow() {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(dimension.width / 2 - WIN_WIDTH / 2, dimension.height / 2 - WIN_HEIGHT / 2,
                WIN_WIDTH, WIN_HEIGHT);
        setTitle("Cross'n'Null");

        setResizable(false);

        bottomPanel = new JPanel(new GridLayout(1,2));

        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnExit);

        add(bottomPanel, BorderLayout.SOUTH);

        startNewGameWindow = new StartNewGameWindow(this);
        field = new Map(this);
        add(field, BorderLayout.CENTER);

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomPanel.setVisible(false);
                startNewGameWindow.setVisible(true);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

    }


    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLen) {
        field.startNewGame(mode, fieldSizeX, fieldSizeY, winLen);
    }

    public static JPanel getBottomPanel() {
        return bottomPanel;
    }

    public class GWComponent extends JComponent{

        String winnername;

        public GWComponent(String winnerName){
            this.winnername = winnerName;
        }

        @Override
        protected void printComponent(Graphics g) {
            super.printComponent(g);
            Font f = new Font("Impact",Font.BOLD,35);
            g.setColor(Color.BLACK);
            g.drawString(winnername + " won!!", 100, WIN_HEIGHT / 2);
            bottomPanel.setVisible(true);
        }
    }


}
