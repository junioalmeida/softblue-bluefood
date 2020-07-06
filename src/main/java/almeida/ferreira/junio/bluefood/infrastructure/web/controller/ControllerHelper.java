package almeida.ferreira.junio.bluefood.infrastructure.web.controller;

import org.springframework.ui.Model;

public class ControllerHelper {

	public static void saveEditMode(Model model, boolean isEdit) {
		model.addAttribute("isEditMode", isEdit);
	}
}
