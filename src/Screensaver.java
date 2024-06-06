import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Screensaver extends JFrame {
    static String[] stringArray = {
            "We commit to being supportive and inclusive.",
            "We actively listen to each other with respect.",
            "We provide an open and safe space to all, and respect each other's boundaries.",
            "We provide the opportunity for everyone to be heard.",
            "We proactively communicate with our clients about the status of the product.",
            "We aim to create outcomes that benefit everyone.",
            "We utilize each other's strength to improve the product.",
            "We set identifiable goals within set deadlines.",
            "We openly communicate about the progress in our work, even when things don't go as planned.",
            "We prioritize quality over trends.",
            "We meet the expectations of our clients."
    };

    static ArrayList<String> coreValues = new ArrayList<>(Arrays.asList(stringArray));
    public Screensaver() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0));
        getContentPane().setBackground(new Color(0, 0, 0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);

        ScreenText text = new ScreenText();
        add(text);

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.exit(0);
            }
        });

        // Set up a timer to update the text every 5 seconds
        Timer timer = new Timer(7500, e -> text.updateText());
        timer.start();
    }

    private static class ScreenText extends JPanel {
        public ScreenText() {
            setForeground(Color.PINK);
        }

        public void updateText() {
            Random random = new Random();
            Graphics2D g2d = (Graphics2D) this.getGraphics();
            int x = random.nextInt((int)((float) getWidth() * 0.5)); // Get random x within panel width
            int y = random.nextInt(getHeight()); // Get random y within panel height

            int fontSize = 12 + (int) (random.nextFloat() * 62);
            g2d.setFont(
                    new Font(
                            "sans-Serif",
                            Font.PLAIN,
                            fontSize)
            );
            g2d.setColor(
                    new Color(
                            random.nextFloat(), random.nextFloat(), random.nextFloat()
                    )
            );

            g2d.drawString(stringArray[new Random().nextInt(stringArray.length)],
                    x,
                    y
            );
        }

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
            g2d.setFont(new Font("sans-Serif", Font.PLAIN, 35));
            int nextRandomInt = new Random().nextInt(stringArray.length);
            g2d.drawString(
                    coreValues.get(nextRandomInt),
                    300,
                    300
            );
        }
    }
}