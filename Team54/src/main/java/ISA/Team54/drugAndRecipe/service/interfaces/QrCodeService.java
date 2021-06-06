package ISA.Team54.drugAndRecipe.service.interfaces;

import java.io.IOException;

public interface QrCodeService {
	  String decodeQRCode(String qrCodeimage) throws IOException;
}
