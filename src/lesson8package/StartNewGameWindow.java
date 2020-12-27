package lesson8package;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.RecursiveTask;

public class StartNewGameWindow extends JFrame {

    private final GameWindow gameWindow;

    private static final int WIN_HEIGHT = 230;
    private static final int WIN_WIDTH = 350;
    private static final int WIN_LEN = 4;
    private static final int FIELD_SIZE = 5;

    private static final String STR_WIN_LEN = "Winning Length";
    private static final String STR_FIELD_SIZE = "Field size";

    private JRadioButton jRadioButtonHumanVSAi = new JRadioButton("Human vs Ai", true);
    private JRadioButton jRadioButtonHumanVSHuman = new JRadioButton("Human vs Human");
    private ButtonGroup gameMode = new ButtonGroup();

    public StartNewGameWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow;
        setSize(WIN_WIDTH, WIN_HEIGHT);

        Rectangle gameWindowBounds = gameWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WIN_WIDTH /2;
        int posY = (int) gameWindowBounds.getCenterY() - WIN_HEIGHT /2;
        setLocation(posX, posY);

        setTitle("New Game Parameters");
        setLayout(new GridLayout(10, 1));
        addGameControlsMode();

    }

    void addGameControlsMode(){
        add(new JLabel("Choose gaming mode"));
        gameMode.add(jRadioButtonHumanVSAi);
        gameMode.add(jRadioButtonHumanVSHuman);
        add(jRadioButtonHumanVSAi);
        add(jRadioButtonHumanVSHuman);
    }

}
