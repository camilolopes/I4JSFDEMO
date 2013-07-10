package com.i4jsfdemo.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.i4jsf.classes.I4JSF;
import com.i4jsfdemo.model.bean.Type;
import com.i4jsfdemo.services.interfaces.TypeService;

@Component
public class TypeController {
	@Autowired
	@Qualifier("typeServiceImpl")
	private TypeService typeServiceImpl;
	private Type type;
	
	public TypeController() {
		init();
	}
	public void init(){
		type = new Type();
	}		

	public void add(){
		try {
			typeServiceImpl.saveOrUpdate(type);
		} catch (Exception e) {
//			FacesContext context = FacesContext.getCurrentInstance();
//			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "language");
//			String msgTypeDuplicated =	bundle.getString("msg.error.type.duplicated");
//			FacesMessage facesMessage = new FacesMessage(msgTypeDuplicated);
//			context.addMessage(null, facesMessage);
			/*
			 * I4JSF API
			 */
			 I4JSF i4Jsf = new I4JSF();
			 i4Jsf.addTranslateContext("language", "msg.error.type.duplicated");
		}finally{
			init();
		}
		
	}
	
	public void delete(){
		typeServiceImpl.delete(type);
		init();
	}
	
	public String back(){
		
		return "caduser";
	}
	
	public List<Type> getListTypes(){
		
		return typeServiceImpl.readAll();
	}
	
	public TypeService getTypeServiceImpl() {
		return typeServiceImpl;
	}

	public void setTypeServiceImpl(TypeService typeServiceImpl) {
		this.typeServiceImpl = typeServiceImpl;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
