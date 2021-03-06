package controllers;

import java.util.Date;
import java.util.List;

import play.cache.Cache;

import models.Request;
import models.User;

public class Requests extends Application {

	public static void show(Long id) {
		Request req = Request.findById(id);
		User to = req.to;
		//if ((connectedUser().id == to.id && req.opened == false) || (connectedUser().id == req.from.id && req.opened == true)) {
			int validationPage = 1;
			if (req.opened == false) {
				req.opened = true;
			} else {
				req.archived = true;
				validationPage = 2;
			}
			req.update();
			User from = req.from;
			String leavingWhen = (String) Cache.get("leavingWhen::"+from.id);
			String leavingFrom = (String) Cache.get("leavingFrom::"+from.id);
			render(req, from, leavingWhen, leavingFrom, validationPage);
		//}
	}
	
    public static void markAsRead(Long id) {
    	Request req = Request.findById(id);
    	//if (req != null && connectedUser() != null) {
    		req.opened = true;
    		req.update();
    	//}
    	Application.index();
    }
    
    public static void markAsAccepted(Long id) {
    	Request req = Request.findById(id);
    	//if (req != null && connectedUser() != null) {
    		req.accepted = true;
    		req.update();
    	//}
    	Application.index();
    }

}
