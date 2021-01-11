package org.unibl.etf.auth;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dto.User;

public class AccessListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub		
		//System.out.println("PALAVESTRA");
		FacesContext cxt = arg0.getFacesContext();
		HttpServletRequest req = (HttpServletRequest) cxt.getExternalContext().getRequest();
		HttpServletResponse resp = (HttpServletResponse) cxt.getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String address = "";
		User user = null;
		if (session != null) {
			user = (User) session.getAttribute("user");
		} else {
		}
		if (req.getRequestURI().contains(";jsessionid=")) {
			if (user != null) {
				address = "index.xhtml?faces-redirect=true";
			} else {
				address = "login.xhtml?faces-redirect=true";
			}

			if (!resp.isCommitted()) {
				cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,address);
				cxt.responseComplete();
			}

		} else if (req.getRequestURI().endsWith("/") || req.getRequestURI().contains("index.xhtml")) {
			if (user != null) {
				return;
			} else {
				address = "login.xhtml?faces-redirect=true";
			}
			
			if (!resp.isCommitted()) {
				cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,address);
				cxt.responseComplete();
			}
			
		}else if (req.getRequestURI().contains("index.xhtml")) {
			if (user != null) {
				return;
			} else {
				address = "login.xhtml?faces-redirect=true";
			}
			
			if (!resp.isCommitted()) {
				cxt.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,
						address);
				cxt.responseComplete();
			}
			
		}
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RENDER_RESPONSE;
	}

}
