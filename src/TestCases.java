import java.sql.Date;
import java.text.SimpleDateFormat;

public class TestCases {
	String URL = "https://practicesoftwaretesting.com/";
	String ExpectedSignInRedirectedLink= "https://practicesoftwaretesting.com/auth/login";
	
	String ExpectedGitHubRedirectedLink= "https://github.com/testsmith-io/practice-software-testing";
	
	String ExpectedSupportRedirectedLink= "https://testwithroy.com/b/support";
	
	String ExpectedPrivacyRedirectedLink = "https://practicesoftwaretesting.com/privacy";
	
	String ExpectedBarnRedirectedLink= "https://unsplash.com/@barnimages";
	
	String ExpectedUnsplashRedirectedLink= "https://unsplash.com/photos/t5YUoHW6zRo";
	
	boolean ExpectedCheckOutRedirectedLink = true;
	
	boolean ExpectedLogOutResult=true;

	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	String FirstName;
	String Lastname;
	Date dob;
	String DateOfBirth;
	String Street;
	String PostalCode;
	String City;
	String State;
	String Country;
	String Phone;
	String Email;
	String Password;
	String  expectedName ;
	Boolean ExpectedInvalidMessage=true;
	Boolean ExpectedIncorrectDataMessage=true;
	Boolean ExpectedErrorMsg = true;


}
