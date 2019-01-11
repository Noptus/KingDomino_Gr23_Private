import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Recognition {

	static String Folder = "Recognition";
	static int[][] plateau = new int[5][5];
	static Parametres para = new Parametres();
	static Plateau p = new Plateau(1, para);

	public static void main(String args[]) throws IOException {

		int Colonnes = 5;
		int Lignes = 5;
		String FileName = "capture";

		// 1 - Montagne
		// 2 - Plaine
		// 3 - Ruine
		// 4 - Foret
		// 5 - Lac
		// 6 - Champ

		System.out.println("Estimated time : " + (Lignes * Colonnes) + " sec");
		long startTime = System.currentTimeMillis();

		System.out.println("");

		BufferedImage img1 = ImageIO.read(new File(FileName + ".png"));
		int Hcase = (int) Math.floor(img1.getHeight() / Colonnes);
		int Wcase = (int) Math.floor(img1.getWidth() / Lignes);
		int[][] Matrix = new int[Lignes][Colonnes];
		p.print();

		for (int x = 0; x < Lignes; x++) {
			for (int y = 0; y < Colonnes; y++) {

				BufferedImage croppedImage = img1.getSubimage(Wcase * x, Hcase * y, Wcase - 1, Hcase - 1);

				int Name = x * 5 + y;
				File outputfile = new File("capture" + Name + ".png");
				ImageIO.write(croppedImage, "png", outputfile);
				int Case = TestMatchingMultiple("capture" + Name);
				Matrix[x][y] = Case;
				p.placerSingle(x + 2, y + 2, Case);
				p.print();

			}
		}
		long estimatedTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println("Time : " + estimatedTime + " sec");

		p.print();

		int Score = p.getScore(true);
		System.out.println("SCORE : " + Score);

	}

	public static double similarity(BufferedImage image1, BufferedImage image2) throws IOException {
		int total_no_ofPixels = 0;
		int image1_PixelColor, red, blue, green;
		int image2_PixelColor, red2, blue2, green2;
		float differenceRed, differenceGreen, differenceBlue, differenceForThisPixel;
		double nonSimilarPixels = 0l, non_Similarity = 0l;

		System.nanoTime();
		for (int row = 0; row < image1.getWidth(); row++) {
			for (int column = 0; column < image1.getHeight(); column++) {
				image1_PixelColor = image1.getRGB(row, column);
				red = (image1_PixelColor & 0x00ff0000) >> 16;
				green = (image1_PixelColor & 0x0000ff00) >> 8;
				blue = image1_PixelColor & 0x000000ff;

				image2_PixelColor = image2.getRGB(row, column);
				red2 = (image2_PixelColor & 0x00ff0000) >> 16;
				green2 = (image2_PixelColor & 0x0000ff00) >> 8;
				blue2 = image2_PixelColor & 0x000000ff;

				if (red != red2 || green != green2 || blue != blue2) {
					differenceRed = red - red2 / 255;
					differenceGreen = (green - green2) / 255;
					differenceBlue = (blue - blue2) / 255;
					differenceForThisPixel = (differenceRed + differenceGreen + differenceBlue) / 3;
					nonSimilarPixels += differenceForThisPixel;
				}
				total_no_ofPixels++;

				if (image1_PixelColor != image2_PixelColor) {
					image2.setRGB(row, column, Color.green.getGreen());
				}
			}
		}
		System.nanoTime();

		System.out.println(" Writing the difference of first_Image to Second_Image ");
		ImageIO.write(image2, "jpeg", new File("D:\\image2.png"));

		non_Similarity = (nonSimilarPixels / total_no_ofPixels);	
		System.out.println(
				"Total No of pixels : " + total_no_ofPixels + "\t Non Similarity is : " + non_Similarity + "%");

		return non_Similarity;
	}
	
	@SuppressWarnings("unused")
	private static int TestMatchingMultiple(String Sujet) throws IOException {

		String Model = "";
		int Seuil = 80;

		BufferedImage img1 = null;
		BufferedImage img2 = null;

		int[] HM = new int[2];
		HM[0] = 0;
		HM[0] = 1;
		long startTime = System.currentTimeMillis();

		// Pour toute notre base de données
		BiblioLoop: for (int i = 1; i <= 50; i++) {
			// En faisant tourner dans les sens
			for (int j = 0; j < 4; j++) {

				Model = Integer.toString(i);
				try {
					img1 = ImageIO.read(new File(Folder + "//" + Model + ".png"));
					img1 = Rotate(img1, j);
					img2 = ImageIO.read(new File(Sujet + ".png"));

				} catch (Exception e) {
				}

				double p1 = GetMatching(img1, img2);
				int p = (int) p1;

				if (p < (100 - Seuil) && p != 0) {

					if (100 - p > HM[0] && p != 0) {
						HM[0] = (100 - p);
						HM[1] = Integer.valueOf(Model);
					}

					// if (p < (100 - 80)) {
					// break BiblioLoop;
					// }

					// System.out.println("Similitude des images : " + (100 - p) + " % tourné " +
					// j);
					// System.out.println(Model + ".png");
					// System.out.println(Sujet + ".png");
					// System.out.println("");
				}

			}

		}

		// Pas de match
		if (HM[0] < Seuil) {
			HM[1] = 0;
		}
		long estimatedTime = (System.currentTimeMillis() - startTime);
		return GetNature(HM, Sujet, estimatedTime);
	}

	private static BufferedImage Rotate(BufferedImage img, int C) throws IOException {

		AffineTransform transform = new AffineTransform();
		double Pi = Math.PI;
		double Fraction = (C * 90 * Pi) / 4;
		transform.rotate(Fraction, img.getWidth() / 2, img.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
		return img;

	}

	private static int GetNature(int[] N, String S, long L) {

		System.out.println("Highest Match : " + N[0] + " %");
		System.out.println("Nature  : " + N[1]);
		System.out.println("Observé : " + S);
		System.out.println("");
		return N[1];

	}

	private static double GetMatching(BufferedImage img1, BufferedImage img2) throws IOException {

		int width_B = img1.getWidth();
		int height_B = img1.getHeight();

		img2 = resize(img2, width_B, height_B);

		double p = Math.round(getDifferencePercent(img1, img2));

		return p;
	}

	private static BufferedImage resize(BufferedImage img, int newW, int newH) {

		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	private static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
		int width = img1.getWidth()/2;
		int height = img1.getHeight()/2;
		int width2 = img2.getWidth()/2;
		int height2 = img2.getHeight()/2;
		if (width != width2 || height != height2) {
			throw new IllegalArgumentException(String.format(
					"Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
		}

		long diff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
			}
		}
		long maxDiff = 3L * 255 * width * height;

		return 100.0 * diff / maxDiff;
	}

	private static int pixelDiff(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}

}