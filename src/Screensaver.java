import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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

        ScreenText text = new ScreenText();
        add(text);

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.exit(0);
            }
        });

        // Set up a timer to update the text every 5 seconds
        Timer timer = new Timer(7500, e -> {
            text.updateText();
            // text.blurText();
        });
        timer.start();
    }

    private static class ScreenText extends JPanel {

        protected void blurText() {
            // Get the size of the component
            int width = getWidth();
            System.out.println(width);
            int height = getHeight();
            System.out.println(height);

            // Create a BufferedImage to draw the current content
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D imageGraphics = image.createGraphics();

            // Double buffering: draw the current content onto the BufferedImage
            paintComponent(this.getGraphics());

            // Create a blurred version of the BufferedImage
            BufferedImage blurredImage = blurImage(image);

            // Get the Graphics2D context of the component
            Graphics2D g2d = (Graphics2D) getGraphics();

            // Draw the blurred image onto the component
            g2d.drawImage(blurredImage, 0, 0, null);
            g2d.dispose(); // Dispose to release resources
        }

        private BufferedImage blurImage(BufferedImage image) {
            // Define a simple 3x3 blur kernel
            float[] kernel = {
                    1f/9f, 1f/9f, 1f/9f,
                    1f/9f, 1f/9f, 1f/9f,
                    1f/9f, 1f/9f, 1f/9f
            };

            // Create the convolution filter with the kernel
            Kernel blurKernel = new Kernel(3, 3, kernel);
            ConvolveOp blurOp = new ConvolveOp(blurKernel, ConvolveOp.EDGE_NO_OP, null);

            // Apply the blur filter to the image
            return blurOp.filter(image, null);
        }


        protected void updateText() {
            Random random = new Random();
            Graphics2D g2d = (Graphics2D) this.getGraphics();
            int x = random.nextInt((int)((float) getWidth() * 0.4));
            int y = random.nextInt(getHeight());

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