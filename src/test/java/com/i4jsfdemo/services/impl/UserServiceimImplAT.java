package com.i4jsfdemo.services.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.i4jsfdemo.dbunit.config.DBUnitConfiguration;
import com.i4jsfdemo.enums.StatusUser;
import com.i4jsfdemo.exception.EmailException;
import com.i4jsfdemo.exception.UserException;
import com.i4jsfdemo.model.bean.Type;
import com.i4jsfdemo.model.bean.User;
import com.i4jsfdemo.services.impl.UserServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:**/OrderPersistenceTests-context.xml"})
@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager")
@Transactional
public class UserServiceimImplAT extends DBUnitConfiguration{
	@Autowired
	@Qualifier("userServiceImpl")
	private UserServiceImpl userServiceImpl;

	
	@Before
	public void setUp() throws Exception {
		getSetUpOperation().execute(getConnection(), getDataSet());
	}
	
	private User getUser(long id) {
		User user = (User) getSessionFactory().getCurrentSession().get(User.class, id);
	return user;
	}
	
	private Type getType(long id){
		Type type = (Type) getSessionFactory().getCurrentSession().get(Type.class, id);
		return type;
	}
	
	private User createUser() {
		User user = new User();
		user.setEmail("use12@user.com");
		user.setLastname("lopes");
		user.setName("camilo");
		user.setPassword("123456");
		user.setRegisterDate(new Date());
		user.setType(getType(1));
		user.setStatus(StatusUser.ACTIVE);
		return user;
	}

	@Test
	public void testAddNewUserWithSucess() {
		User user = createUser();
		try{
			int expectedTotalUser = 1 + userServiceImpl.readAll().size();
			userServiceImpl.saveOrUpdate(user);
			assertNotNull(userServiceImpl.readAll());
			assertEquals(expectedTotalUser ,userServiceImpl.readAll().size());
		}catch (Exception e) {
			fail("not expected result " + e.fillInStackTrace());
		}
	}


	@Test
	public void testDeletedUserById() throws Exception {
		assertEquals(userServiceImpl.readAll().size(),getDataSet().getTable("USER").getRowCount());
		User userFound = getUser(1);
		assertNotNull(userFound);
		userServiceImpl.delete(userFound);
		userFound = (User) getSessionFactory().getCurrentSession().get(User.class, 1L);
		assertNull(userFound);
	}

	@Test
	public void testFindUserById() {
		assertNotNull(userServiceImpl.searchById(1L));
	}
	@Test
	public void testUserNotFoundById(){
		assertNull(userServiceImpl.searchById(0L));
	}

	@Test
	public void testReturnAllUsersExists() {
		assertNotNull(userServiceImpl.readAll());
		assertFalse(userServiceImpl.readAll().isEmpty());
	}
	
	@Test
	public void testUpdateEmailOfUser() throws EmailException, UserException{
		User user = getUser(1);
		user.setEmail("novo@email.com.br");
		userServiceImpl.saveOrUpdate(user);
		User userFound = userServiceImpl.searchById(1L);
		assertEquals(user.getEmail(), userFound.getEmail() );
	}
	@Test
	public void testUpdateDataOfTheUser() throws EmailException, UserException{
		User user = getUser(2L);
		String expectedNameUser = "joão";
		assertEquals(expectedNameUser, user.getName());
		user.setName("Pedro"); 
		user.setLastname("Leão"); 
		Date currentDate = new Date();
		user.setRegisterDate(currentDate);
		user.setStatus(StatusUser.INACTIVE);
		user.setExpirationDate(currentDate);
		userServiceImpl.saveOrUpdate(user);
		User userUpdated = userServiceImpl.searchById(2L);
		assertEquals(user.getName(), userUpdated.getName());
		assertFalse(user.getStatus()!= userUpdated.getStatus());
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchUserDescriptionIsEmptyNotValid(){
		String description = "";
		userServiceImpl.searchUser(description);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSearchUserDescriptionNullIsNotValid(){
		userServiceImpl.searchUser(null);
	}
	@Test
	public void testFoundUserByEmailExpectedOneUser(){
		List<User> listUsersFound = userServiceImpl.searchUser("camilo@camilolopes.com");
		assertNotNull(listUsersFound);
		int totalExpectedUser = 1;
		assertEquals(totalExpectedUser,listUsersFound.size());
		String expectedUser = getUser(1).getEmail();
		assertEquals(expectedUser,listUsersFound.get(0).getEmail());
	}
	@Test
	public void testNotfoundUserByEmailNotExist(){
		List<User> listUsersFound = userServiceImpl.searchUser("camilo@camilolopes.net");
		assertTrue(listUsersFound.isEmpty());
	}

	@Test
	public void testFoundUserByName(){
		List<User> listUsers = userServiceImpl.searchUser("camilo");
		int totalUsersExpected = 2;
		assertEquals(totalUsersExpected,listUsers.size());
	}
	@Test
	public void testFoundUserByNameIgnoreCaseSensitive(){
		List<User> listUser = userServiceImpl.searchUser("MARCELo");
		int expectedTotalUsers = 1;
		assertEquals(expectedTotalUsers,listUser.size());
		String expectedUser = "Marcelo Camilo";
		assertEquals(expectedUser,listUser.get(0).getName());
	}
	@Test
	public void testFoundUserByLastName(){
		String expectedLastNameOfUser = "Sangalo";
		List<User> listUsers = userServiceImpl.searchUser(expectedLastNameOfUser);
		int totalUserFound = 1;
		assertTrue(listUsers.size()==totalUserFound);
		assertEquals(expectedLastNameOfUser, listUsers.get(0).getLastname());
	}
	@Test
	public void testFoundUserByParcialDescription(){
		List<User> listUsersFound = userServiceImpl.searchUser("cam");
		assertFalse(listUsersFound.isEmpty());
		int expectedTotalUsersFound = 2;
		assertEquals(expectedTotalUsersFound,listUsersFound.size());
		
	}
	@Test
	public void testUserNotFoundItDoesnotExist(){
		List<User> listUsers = userServiceImpl.searchUser("pateta");
		assertTrue(listUsers.isEmpty());
	}
	@Test
	public void testUserFoundByEmailCaseSensitiveIgnored(){
		List<User> listUsers = userServiceImpl.searchUser("Joao@EMAIL.COM");
		assertFalse(listUsers.isEmpty());
		assertEquals("joao@email.com", listUsers.get(0).getEmail());
	}
	@Test
	public void testUserFoundByLastNameIgnoringCaseSensitive(){
		List<User> listUsers = userServiceImpl.searchUser("MedEiros");
		int totalUsersExpected = 1;
		assertEquals(totalUsersExpected,listUsers.size());
		String lastNameExpected = "medeiros";
		assertEquals(lastNameExpected,listUsers.get(0).getLastname());
	}
	
	@Test
	public void testExpireDateMustBeGreaterThanCurrentDateUserAtive(){
		long id = 1L;
		User user = userServiceImpl.searchById(id);
		Date userExpirationDate = user.getExpirationDate();
		Date currentDate = new Date();
		assertTrue(user.getStatus().equals(StatusUser.ACTIVE));
		assertTrue(userExpirationDate.after(currentDate));
	}
	@Test
	public void testAddNewUserButExpirationDateWasNotInformed() throws EmailException, UserException{
		User user = createUser();
		userServiceImpl.saveOrUpdate(user);
		List<User> listUsers = userServiceImpl.searchUser(user.getEmail());
		User userFound = listUsers.get(0);
		assertEquals(user.getEmail(),userFound.getEmail());
		assertNotNull(userFound.getExpirationDate());
		Date registerDate = userFound.getRegisterDate();
		DateTime dt = new DateTime(registerDate.getTime());
		int numberOfDays = 90;
		DateTime dateTime = dt.plusDays(numberOfDays);
		Date expectedExpirationDate = dateTime.toDate();
		assertEquals(expectedExpirationDate.toString(),userFound.getExpirationDate().toString());
	}
	@Test
	public void testAddingNewUserExpirationDateMustGreaterThanCurrentDate() throws EmailException, UserException{
		User newUser = createUser();
		DateTime dt = new DateTime(new Date().getTime());
		int numberOfDays = 120;
		DateTime dateTime = dt.plusDays(numberOfDays);
		Date expirationDate = dateTime.toDate();
		newUser.setExpirationDate(expirationDate );
		userServiceImpl.saveOrUpdate(newUser);
		assertEquals(expirationDate,newUser.getExpirationDate());
	}
	@Test(expected=UserException.class)
	public void testUserActiveButDateHasExpiredChangedUserStatus() throws UserException{
		int id = 2;
		User user = getUser(id);
		userServiceImpl.validateExpirationDate(user);
		assertEquals(StatusUser.INACTIVE,user.getStatus());
	}
	@Test(expected=UserException.class)
	public void testUserExpirationDateHasExpired() throws UserException{
		int id = 4;
		User userIvete = getUser(id);
		userServiceImpl.validateExpirationDate(userIvete);
		
	}
	@Test
	public void testUserExpirationDateTodayStatusKeeped() throws UserException{
		int id = 3;
		User userMarcelo = getUser(id);
		userMarcelo.setExpirationDate(new Date());
		userServiceImpl.validateExpirationDate(userMarcelo);
		assertEquals(StatusUser.ACTIVE,userMarcelo.getStatus());
	}
	@Test
	public void testUserExpirationDateNotExpiredUserIsActive() throws UserException{
		int id = 3;
		User userMarcelo = getUser(id);
		Date currentDate = new Date();
		DateTime dt = new DateTime(currentDate.getTime());
		int numberOfYears = 10;
		DateTime dateTime = dt.plusYears(numberOfYears);
		currentDate = dateTime.toDate();
		userMarcelo.setExpirationDate(currentDate);
		userServiceImpl.validateExpirationDate(userMarcelo);
		assertEquals(StatusUser.ACTIVE,userMarcelo.getStatus());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterDateCannotBeNull() throws EmailException, UserException{
		User user = new User();
		userServiceImpl.saveOrUpdate(user);
	}
	@Test(expected=EmailException.class)
	public void testIfUserAlreadyExistByEmailThrowException() throws EmailException, UserException{
		User user = new User();
		user.setEmail("camilo@camilolopes.com");
		Date currentDate = new Date();
		user.setExpirationDate(currentDate);
		user.setRegisterDate(currentDate);
		user.setName("Camilo");
		user.setLastname("Neto");
		user.setPassword("1235");
		user.setStatus(StatusUser.ACTIVE);		
		userServiceImpl.saveOrUpdate(user);
	}
	@Test
	public void testSearchUserByEmailExpectedOneUser(){
		User user = userServiceImpl.searchUserByEmail("ivete@sangalo.com");
		assertNotNull(user);
		assertEquals(user,getUser(4));
	}
	@Test
	public void testUserNotFoundByEmail(){
		User user = userServiceImpl.searchUserByEmail("pateta@pateta.com");
		assertNull(user);
	}
}
