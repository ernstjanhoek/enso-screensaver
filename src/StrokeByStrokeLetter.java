import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

public class StrokeByStrokeLetter extends JPanel implements ActionListener {

    private static final String TEXT = "amalgamation, no assimilation"; // Single letter for MVP
    private static final int TIMER_DELAY = 50; // milliseconds

    private int currentSegmentIndex = 0;
    private Timer timer;
    private final ArrayList<GeneralPath> letterPath = new ArrayList<>();
    private final ArrayList<Integer> segmentCount = new ArrayList<>();

    public StrokeByStrokeLetter() {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        timer = new Timer(TIMER_DELAY, this);
        for (int index = 0; index < TEXT.length(); index++) {
            initializePath(index);
        }
    }

    private void initializePath(int index) {
        Font font = new Font("Serif", Font.BOLD, 200);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        GlyphVector gv = font.createGlyphVector(frc, "" + TEXT.charAt(index));
        System.out.println(index);
        GeneralPath lp = new GeneralPath(gv.getGlyphOutline(0));
        letterPath.add(lp);
        segmentCount.add(countSegments(lp));
    }

    private int countSegments(GeneralPath path) {
        PathIterator pi = path.getPathIterator(null);
        int count = 0;
        while (!pi.isDone()) {
            count++;
            pi.next();
        }
        return count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLetterWithEffect((Graphics2D) g);
    }

    private void drawLetterWithEffect(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Serif", Font.BOLD, 200));

        int x = (getWidth() - 200) / 2;
        int y = (getHeight() + 200) / 2;
        g2d.translate(x, y);


        float[] coords = new float[6];
        for (int i = 0; i < segmentCount.size(); i++) {
            PathIterator pi = letterPath.get(i).getPathIterator(null);
            GeneralPath partialPath = new GeneralPath();

            int count = 0;
            while (!pi.isDone() && count <= currentSegmentIndex) {
                int type = pi.currentSegment(coords);
                switch (type) {
                    case PathIterator.SEG_MOVETO:
                        partialPath.moveTo(coords[0], coords[1]);
                        break;
                    case PathIterator.SEG_LINETO:
                        partialPath.lineTo(coords[0], coords[1]);
                        break;
                    case PathIterator.SEG_QUADTO:
                        partialPath.quadTo(coords[0], coords[1], coords[2], coords[3]);
                        break;
                    case PathIterator.SEG_CUBICTO:
                        partialPath.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                        break;
                    case PathIterator.SEG_CLOSE:
                        partialPath.closePath();
                        break;
                }
                count++;
                pi.next();
            }

            g2d.draw(partialPath);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentSegmentIndex++;
        if (currentSegmentIndex > segmentCount.size()) {
            currentSegmentIndex = 0; // Loop the animation
        }
        repaint();
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stroke By Stroke Letter");
        StrokeByStrokeLetter screenSaver = new StrokeByStrokeLetter();
        frame.add(screenSaver);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        screenSaver.startAnimation();
    }
}