package controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String HelloWorld(){
        return "Hello World" ;
    }

    @PostMapping(
            path = "/" ,
            consumes = {MediaType.IMAGE_PNG_VALUE , MediaType.IMAGE_JPEG_VALUE},
            produces = {MediaType.IMAGE_JPEG_VALUE , MediaType.IMAGE_PNG_VALUE} )

    public @ResponseBody byte[] postTest(@RequestBody byte[] image)throws IOException {
        ByteArrayInputStream img = new ByteArrayInputStream(image);
        return makeGray(ImageIO.read(img));
    }

    public static byte[] makeGray(BufferedImage img) throws IOException {
        byte[] bytes = new byte[0];
        for (int x = 0; x < img.getWidth(); ++x)
            for (int y = 0; y < img.getHeight(); ++y)
            {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);

                // Normalize and gamma correct:
                double rr = Math.pow(r / 255.0, 2.2);
                double gg = Math.pow(g / 255.0, 2.2);
                double bb = Math.pow(b / 255.0, 2.2);

                // Calculate luminance:
                double lum = 0.2126 * rr + 0.7152 * gg + 0.0722 * bb;

                // Gamma compand and rescale to byte range:
                int grayLevel = (int) (255.0 * Math.pow(lum, 1.0 / 2.2));
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
                img.setRGB(x, y, gray);
                ByteArrayOutputStream byteImage = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", byteImage);
                 bytes = byteImage.toByteArray() ;
            }
        return bytes ;
    }
}

