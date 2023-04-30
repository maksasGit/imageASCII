import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ImageManager image = new ImageManager("LinkedIn-Logo.jpg");
        image.resize(  158  , 39  );
//        image.toASCII();
        image.toASCIItxt();
        ImageManager.txtToJpg("results/LinkedIn-Logo.txt" , "results/LinkedIn-LogoFromTXT.jpg");
    }
}
