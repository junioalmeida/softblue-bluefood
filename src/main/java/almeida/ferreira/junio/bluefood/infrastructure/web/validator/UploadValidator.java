package almeida.ferreira.junio.bluefood.infrastructure.web.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import almeida.ferreira.junio.bluefood.utils.FileType;

public class UploadValidator implements ConstraintValidator<UploadConstraint, MultipartFile>{

	private List<FileType> acceptedFiles;
	
	@Override
	public void initialize(UploadConstraint constraintAnnotation) {
		acceptedFiles = Arrays.asList( constraintAnnotation.acceptedFileTypes());
	}
	
	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		if(multipartFile == null) {
			return true;
		}
		
		for(FileType type : acceptedFiles) {
			if(type.sameOf(multipartFile.getContentType())) {
				return true;
			}
		}
		
		return false;
	}

}
