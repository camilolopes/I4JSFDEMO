package com.camilolopes.readerweb.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.camilolopes.readerweb.enums.StatusUser;
import com.camilolopes.readerweb.exception.EmailException;
import com.camilolopes.readerweb.model.bean.Type;
import com.camilolopes.readerweb.model.bean.User;
import com.camilolopes.readerweb.services.interfaces.TypeService;
import com.camilolopes.readerweb.services.interfaces.UserService;

@Controller
public class UserController {
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userServiceImpl; 
	@Autowired
	@Qualifier("typeServiceImpl")
	private TypeService typeServiceImpl;

	private User user;
	private String description;
	private Long id;
	private Date registerDate; 
	private String selectedStatusUser;
	private List<User> listUsers;
	private boolean result;
	private Type selectedType;
	public UserController() {
		init();
	}
	
	public void init(){
		user = new User();
		registerDate = new Date();
		user.setRegisterDate(registerDate);
	}
	
	public void addEditUser(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
			user.setStatus(StatusUser.valueOf(selectedStatusUser));
			Type t = typeServiceImpl.searchById(selectedType.getId());
			user.setType(t);
			try {
				userServiceImpl.saveOrUpdate(user);
				String notificationSucess = "Usuário " + user.getName()	+ " salvo com Sucesso";
				FacesMessage facesMessage = new FacesMessage(notificationSucess);
				facesContext.addMessage("msg", facesMessage);
				init();
			} catch (EmailException e) {
				String msgError = "E-mail já cadastrado";
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msgError, ""));
			}
			
	}

	public List<User> listAllUsers(){
		
		return userServiceImpl.readAll();
	
	}
	
	public List<User> searchUser(){
		
		return userServiceImpl.searchUser(description);
	}
	
	public void deleteUser(){
		userServiceImpl.delete(user);
		init();
	}
	
	public User searchUserById(){
		
		return userServiceImpl.searchById(id);
	}
	
	public String editar(){
		
		return "/pages/cadusuario";
	}
	
	public List<Type> getListTypes(){
		return typeServiceImpl.readAll();
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSelectedStatusUser() {
		return selectedStatusUser;
	}

	public void setSelectedStatusUser(String selectedStatusUser) {
		this.selectedStatusUser = selectedStatusUser;
	}

	public List<User> getListUsers() {
		listUsers = listAllUsers();
		return listUsers;
	}
	public boolean getResult(){
	return result;
	}

	public Type getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(Type selectedType) {
		this.selectedType = selectedType;
	}


	public TypeService getTypeServiceImpl() {
		return typeServiceImpl;
	}

	public void setTypeServiceImpl(TypeService typeServiceImpl) {
		this.typeServiceImpl = typeServiceImpl;
	}

	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

}
