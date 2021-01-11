package org.unibl.etf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;

@FacesValidator("org.unibl.etf.validator.PictureURLValidator")
public class PictureURLValidator implements Validator {
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {

		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(value.toString())) {
		} else {

			FacesMessage msg = new FacesMessage("Invalid url!", "Invalid url!");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
