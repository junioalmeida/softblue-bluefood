package almeida.ferreira.junio.bluefood.application.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import almeida.ferreira.junio.bluefood.utils.IOUtils;

@Service
public class ImageService {

	@Value("${bluefood.files.restaurant.logotipo}")
	private String logotipoDirectory;

	@Value("${bluefood.files.restaurant.category}")
	private String categoriesDirectory;

	@Value("${bluefood.files.restaurant.food}")
	private String foodsDirectory;

	public void uploadLogotipo(MultipartFile file, String fileName) {
		try {
			IOUtils.copyFile(file.getInputStream(), fileName, logotipoDirectory);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public void uploadFoods(MultipartFile file, String fileName) {
		try {
			IOUtils.copyFile(file.getInputStream(), fileName, foodsDirectory);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}
	}
	
	public boolean deleteFoods(String fileName) {
		try {
			return IOUtils.deleteFile(fileName, foodsDirectory);
		} catch (IOException e) {
			throw new ApplicationServiceException(e);
		}	
	}

	public byte[] getFileBytes(String type, String fileName) {

		try {
			String dir;

			if ("logotipo".equals(type)) {
				dir = logotipoDirectory;
			} else if ("category".equals(type)) {
				dir = categoriesDirectory;
			} else if ("food".equals(type)) {
				dir = foodsDirectory;
			} else {
				throw new Exception("O tipo " + type + " não existe no servidor.");
			}

			return IOUtils.getBytesFile(Paths.get(dir, fileName));
		} catch (Exception e) {
			throw new ApplicationServiceException(e);
		}

	}
}
