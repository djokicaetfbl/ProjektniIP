package org.unibl.etf.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("org.unibl.etf.validator.DateValidator")
public class DateValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (value instanceof Date) {
			Date datum = (Date) value;
			if (datum.before(new Date())) {
				FacesMessage msg = new FacesMessage("Invalid date!", "Invalid date!");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}

	}
}
