##JEE Project Example using I4JSF API

###How to Run I4JSFDEMO application locally?

1. download project ;
2. unzip project;
3. via command line go to the folder extracted;
4. in root directory type mvn jetty:run 
5. open browser e type http://localhost:8080/I4JSFDEMO/
6. add a type of user, for example: Manager, Developer, Admin 
7. Click back; 
8. add new User in application 

###Testing Internationalization

Support by Default in example: only Portuguese (BR) and English (US)

- Try add new user without e-mail and see notification in idiom of your browser.
- try add duplicated user;
- delete an user recorded; 

###Checking code using I4JSF
We let comment code using natural JSF, like this it is possible compare with I4JSF code. 

* UserController.java 
```java
/*
   * Natural JSF Code
	 */
	public void addFacesContext(String notification){
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "language");
//		String msgBundle = bundle.getString(notification);
//		addFacesMessage(facesContext, msgBundle);
/* I4JSF API Code */
		new I4JSF().addTranslateContext("language", notification);
	}
  ```

* TypeController.java
```java
/* Natural JSF Code */
//  		FacesContext context = FacesContext.getCurrentInstance();
//			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "language");
//			String msgTypeDuplicated =	bundle.getString("msg.error.type.duplicated");
//			FacesMessage facesMessage = new FacesMessage(msgTypeDuplicated);
//			context.addMessage(null, facesMessage);
			/*
			 * I4JSF API
			 */
			 I4JSF i4Jsf = new I4JSF();
			 i4Jsf.addTranslateContext("language", "msg.error.type.duplicated");
```


