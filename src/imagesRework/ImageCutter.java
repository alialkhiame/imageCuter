package imagesRework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCutter {
	static String imagePath = "C:\\Users\\alial\\OneDrive\\Desktop\\erp Ali"; // Replace with the actual image path
	static String outputPath = "C:\\Users\\alial\\OneDrive\\Desktop\\erp Ali\\reworked";
	static int height = 100;// how much of the Header you want to cut
	static int footer = 100;// how much of the footer you want to cut

	public static void main(String[] args) {

		findImages(imagePath);
	}

	public static void findImages(String inputFolderPath) {
		File inputFolder = new File(inputFolderPath);
		File[] imageFiles = inputFolder
				.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg")
						|| name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".gif"));

		if (imageFiles == null) {
			System.out.println("No image files found in the input folder.");
			return;
		}
		for (File imageFile : imageFiles) {
			String inputFilePath = imageFile.getAbsolutePath();
			String outputFilePath = outputPath + File.separator + imageFile.getName();
			cutImage(inputFilePath, 150, 60, outputFilePath);
		}
	}

	public static void cutImage(String imagePath, int headerHeight, int footerHeight, String outputPath) {
		try {
			// Load the image
			BufferedImage originalImage = ImageIO.read(new File(imagePath));

			// Calculate the dimensions for the modified image
			int modifiedHeight = originalImage.getHeight() - (headerHeight + footerHeight);

			// Create a new BufferedImage with the modified dimensions
			BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), modifiedHeight,
					originalImage.getType());

			// Get the Graphics2D object for drawing on the modified image
			Graphics2D g2d = modifiedImage.createGraphics();

			// Copy the desired part of the image to the modified image
			g2d.drawImage(originalImage, 0, 0, originalImage.getWidth(), modifiedHeight, 0, headerHeight,
					originalImage.getWidth(), originalImage.getHeight() - footerHeight, null);

			// Dispose of the Graphics2D object
			g2d.dispose();

			// Save the modified image
			ImageIO.write(modifiedImage, "png", new File(outputPath));

			System.out.println("Image modification completed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
