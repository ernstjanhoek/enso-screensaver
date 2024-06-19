import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
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

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        setCursor(blankCursor);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.exit(0);
            }
        });

        // ScreenText JPanel with initial BufferedImage object
        ScreenText screen = new ScreenText(
                new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB)
        );
        add(screen);

        Timer timer = new Timer(10, e -> {
            screen.updateImg(
                    GaussianEffects.gaussianBlur3x3(
                            screen.getImg()
                    )
            );
            screen.updateImg(
                    PrintText.addText(
                            screen.getImg(),
                            stringArray[new Random().nextInt(stringArray.length -1)]
                    )
            );
        });
        timer.start();
    }

    private static class ScreenText extends JPanel {
        BufferedImage img;

        ScreenText(BufferedImage image) {
            this.img = image;
            setBackground(new Color(0, 0, 0));
            setVisible(true);
        }

        protected BufferedImage getImg() {
            return img;
        }

        protected void updateImg(BufferedImage image) {
            img = image;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                g.drawImage(img, 0, 0, this);
            }
        }
    }
}