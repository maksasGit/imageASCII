import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageManager {

    public BufferedImage source = null;
    public String sourcePath;
    public String sourceScalePath = null;

    public ImageManager(String path) {
        File file = new File(path);
        try {
            source = ImageIO.read(file);
            sourcePath = path;
        } catch (IOException e) {
            System.out.println("Файл не найден или не удалось сохранить");
        }
    }

    public void resize(int width, int height) {
        try {
            Image scaledImage = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
            File outputFile = new File("results/" + sourcePath);
            ImageIO.write(outputImage, "jpg", outputFile);
            source = outputImage;
            sourceScalePath = "results/" + sourcePath;
            System.out.println("Image resized successfully!");
        } catch (Exception e) {
            System.out.println("Error while resizing image: " + e.getMessage());
        }

    }

    public void toASCII() {
        String gradientSTR = " .:!/r(l1Z4H9W8$@";
        char[] gradient = gradientSTR.toCharArray();
        try {
            File file = new File(sourceScalePath);
            BufferedImage sourceScale = ImageIO.read(file);
            BufferedImage outputImage = new BufferedImage(sourceScale.getWidth() * 2, sourceScale.getHeight() * 2, BufferedImage.TYPE_INT_RGB);
            Graphics g = outputImage.getGraphics();
            for (int i = 0; i < sourceScale.getHeight(); i++) {
                for (int j = 0; j < sourceScale.getWidth(); j++) {
                    Pixel a = new Pixel(new Color(sourceScale.getRGB(j, i)));
                    int grey = (int) (a.red * 0.299 + a.green * 0.587 + a.blue * 0.114);
                    int level = grey / gradientSTR.length();
                    g.setColor(new Color(a.red, a.green, a.blue));
                    g.drawString(String.valueOf(gradient[level]), j * 2, i * 2);
                }
            }
            File outputFile = new File("results/" + sourcePath);
            ImageIO.write(outputImage, "jpg", outputFile);
            System.out.println("Image saved successfully as " + "results/" + sourcePath);
        } catch (IOException e) {
            System.out.println("File not found or could not be saved");
        }

    }


    public void toASCIItxt() {
        String gradientSTR = " .:!/r(l1Z4H9W8$@";
        char[] gradient = gradientSTR.toCharArray();
        try {
            File file = new File(sourceScalePath);
            BufferedImage sourceScale = ImageIO.read(file);
            FileWriter writer = new FileWriter("results/" + sourcePath.split("\\.")[0] + ".txt");
            for (int i = 0; i < sourceScale.getHeight(); i++) {
                for (int j = 0; j < sourceScale.getWidth(); j++) {
                    Pixel a = new Pixel(new Color(sourceScale.getRGB(j, i)));
                    int grey = (int) (a.red * 0.299 + a.green * 0.587 + a.blue * 0.114);
                    int level = grey / gradientSTR.length();
                    writer.write(gradient[level]);
                    writer.write(gradient[level]);
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
            System.out.println("ASCII characters saved successfully to " + "results/" + sourcePath);
        } catch (IOException e) {
            System.out.println("File not found or could not be saved");
        }
    }
    public static void txtToJpg(String textFilePath, String imageFilePath) {
        int imageWidth = 2400;
        int imageHeight = 600;
        int fontSize = 12;
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(textFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading text file: " + e.getMessage());
            return;
        }
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Font font = new Font("Segoe UI", Font.PLAIN, fontSize);
        g.setFont(font);
        g.setColor(Color.WHITE);

        // Draw the text onto the image
        String[] lines = text.toString().split("\n");
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int y = lineHeight;
        int maxLineWidth = 0;
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > maxLineWidth) {
                maxLineWidth = lineWidth;
            }
        }
        int x = (imageWidth - maxLineWidth) / 2;
        for (String line : lines) {
            g.drawString(line, x, y);
            y += lineHeight;
        }
        try {
            File outputfile = new File(imageFilePath);
            ImageIO.write(image, "png", outputfile);
            System.out.println("Image saved successfully to " + imageFilePath);
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

}


