package ISA.Team54.drugAndRecipe.service.impl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


import ISA.Team54.drugAndRecipe.service.interfaces.QrCodeService;

@Service
public class QrCodeServiceImpl implements QrCodeService{

	@Override
	public String decodeQRCode(String qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(qrCodeimage));
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
       
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
       
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image ------------ ");
            return null;
        }
    }


	 
}
