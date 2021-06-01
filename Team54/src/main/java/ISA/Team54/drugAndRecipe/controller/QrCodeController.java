package ISA.Team54.drugAndRecipe.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ISA.Team54.drugAndRecipe.service.interfaces.QrCodeService;

@RestController
@RequestMapping(value = "/qrCode", produces = MediaType.APPLICATION_JSON_VALUE)
public class QrCodeController {

	@Autowired
	private QrCodeService qrCodeService;
	
	@PostMapping("/decodeQrCode")
	@PreAuthorize("hasRole('PATIENT')")
	public  ResponseEntity<String> decodeQRCode(@RequestBody String qrCodeimage ){
		try {		
			qrCodeimage = qrCodeimage.substring(0, qrCodeimage.length() - 1);
			String qrCodeimageAbsolutePath = "C:\\Users\\HP\\OneDrive\\Radna površina\\ISANajnoviji\\PharmacySystem\\Team54\\src\\main\\resources\\QrCodes\\" + qrCodeimage;
			if(qrCodeService.decodeQRCode(qrCodeimageAbsolutePath) == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
			return new ResponseEntity<>(qrCodeService.decodeQRCode(qrCodeimageAbsolutePath), HttpStatus.OK);	
		} catch (IOException e ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}	
	} 
}
