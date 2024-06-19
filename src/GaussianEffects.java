import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class GaussianEffects {
    public static BufferedImage gaussianBlur3x3(BufferedImage image) {
        BufferedImage blurImg = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        IntStream.range(1, blurImg.getHeight() - 1).parallel().forEach(y ->
                IntStream.range(1, blurImg.getWidth() - 1).parallel().forEach(x -> {
                    int r = 0, g = 0, b = 0;
                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int weight = getGaussianWeight(kx, ky);

                            int rgb = image.getRGB(x + kx, y + ky);
                            r += weight * ((rgb >> 16) & 0xFF);
                            g += weight * ((rgb >> 8) & 0xFF);
                            b += weight * (rgb & 0xFF);
                        }
                    }

                    r /= 16;
                    g /= 16;
                    b /= 16;

                    int p = (255 << 24) | (r << 16) | (g << 8) | b;
                    blurImg.setRGB(x, y, p);
                }));
        return blurImg;

    }

    private static int getGaussianWeight(int x, int y) {
        int[][] weights = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        return weights[y + 1][x + 1];
    }

}
