package almeida.ferreira.junio.bluefood.application;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import almeida.ferreira.junio.bluefood.utils.IOUtils;

@Service
public class ImageService {
	
	@Value("${bluefood.files.restaurant.logotipo}")
	private String logotipoDirectory;
	
	public void uploadFile(MultipartFile file, String fileName) {
		try {
			IOUtils.copyFile(file.getInputStream(), fileName, logotipoDirectory);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
}
