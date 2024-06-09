import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PrintText {
    public static BufferedImage addText(BufferedImage img, String text) {
        Random random = new Random();
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        int x = random.nextInt((int)((float) img.getWidth() * 0.4));
        int y = random.nextInt(img.getHeight());

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

        g2d.drawString(text,
                x,
                y
        );
        g2d.dispose();
        return img;
    }
}
