import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest extends TestCases {
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	Connection con;
	Statement stmt;
	ResultSet rs;
	Random rand = new Random();

	@BeforeTest
	public void mySetup() throws SQLException {

		driver.get(URL);
		driver.manage().window().maximize();
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/final_project", "root", "1234");
		String query = "SELECT * FROM user_info WHERE id = 1;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		while (rs.next()) {

			FirstName = rs.getString("first_name");
			Lastname = rs.getString("last_name");
			Email = rs.getString("Email");
			Password = rs.getString("password");
			dob = rs.getDate("date_of_birth");
			Street = rs.getString("street");
			PostalCode = rs.getString("postal_code");
			City = rs.getString("city");
			State = rs.getString("state");
			Country = rs.getString("country");
			Phone = rs.getString("phone");
			expectedName = rs.getString("first_name") + " " + rs.getString("last_name");

		}

	}

	@Test(priority = 1, enabled = true)
	public void HomepageLoadsSuccessfully() throws InterruptedException {
		driver.get(URL);
		Thread.sleep(2000);
		assertTrue(driver.findElement(By.className("navbar-brand")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//a[@href='/auth/login']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//a[@href='/contact']")).isDisplayed());
		assertTrue(driver.findElement(By.cssSelector(".nav-link.dropdown-toggle")).isDisplayed());
	}
	
	@Test(priority = 2, enabled = true)
	public void FooterLinks() throws InterruptedException {
		List<WebElement> ActualLinks = driver.findElements(By.xpath("//a[@target='_blank']"));
		driver.findElement(By.xpath("//a[@routerlink='privacy']")).click();
		Thread.sleep(2000);
		String ActualPrivacyLink = driver.getCurrentUrl();
		Assert.assertEquals(ActualPrivacyLink, ExpectedPrivacyRedirectedLink);

		Assert.assertEquals(ActualLinks.get(0).getDomAttribute("href"), ExpectedGitHubRedirectedLink);
		Assert.assertEquals(ActualLinks.get(1).getDomAttribute("href"), ExpectedSupportRedirectedLink);
		Assert.assertEquals(ActualLinks.get(2).getDomAttribute("href"), ExpectedBarnRedirectedLink);
		Assert.assertEquals(ActualLinks.get(3).getDomAttribute("href"), ExpectedUnsplashRedirectedLink);

	}
	
	@Test(priority = 3, enabled = true)
	public void RegistrationFormValidation() throws InterruptedException {
		WebElement Signin = driver.findElement(By.xpath("//a[@href='/auth/login']"));
		Signin.click();
		Thread.sleep(1000);
		WebElement Register = driver.findElement(By.xpath("//a[@href='/auth/register']"));
		Register.click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		Thread.sleep(2000);
		WebElement errorElement = driver.findElement(By.cssSelector(".alert.alert-danger.mt-3"));
		Boolean actualErrorMsg = errorElement.isDisplayed();

		Assert.assertEquals(actualErrorMsg, ExpectedErrorMsg,
				"Expected validation error message to mention 'required'. Actual message: ");
	}

	@Test(priority = 4, enabled = true)
	public void SignIn() throws SQLException, InterruptedException {
		
		driver.findElement(By.id("first_name")).sendKeys(FirstName);
		driver.findElement(By.id("last_name")).sendKeys(Lastname);
		DateOfBirth = formatter.format(dob);
		System.out.println(DateOfBirth);
		driver.findElement(By.id("dob")).sendKeys(DateOfBirth);
		driver.findElement(By.id("street")).sendKeys(Street);
		driver.findElement(By.id("postal_code")).sendKeys(PostalCode);
		driver.findElement(By.id("city")).sendKeys(City);
		driver.findElement(By.id("state")).sendKeys(State);
		driver.findElement(By.id("country")).sendKeys(Country);
		driver.findElement(By.id("phone")).sendKeys(Phone);

		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("password")).sendKeys(Password);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		Thread.sleep(2000);
		String ActualLink = driver.getCurrentUrl();
		System.out.println(ActualLink);
		Assert.assertEquals(ActualLink, ExpectedSignInRedirectedLink);

	}

	

	@Test(priority = 5, enabled = true)
	public void LoginWithInvalidData() throws InterruptedException {
		WebElement Signin = driver.findElement(By.xpath("//a[@href='/auth/login']"));
		Signin.click();
		Thread.sleep(2000);
		driver.findElement(By.id("email")).sendKeys("123axsd");
		driver.findElement(By.id("password")).sendKeys("12345678");
		WebElement loginBtn = driver.findElement(By.className("btnSubmit"));
		loginBtn.click();
		Boolean ActualInvalidErrorMessage = driver.findElement(By.id("email-error")).isDisplayed();
		Assert.assertEquals(ActualInvalidErrorMessage, ExpectedInvalidMessage);

	}

	@Test(priority = 6, enabled = true)
	public void LoginWithIncorrectData() throws InterruptedException, SQLException {
		WebElement Signin = driver.findElement(By.xpath("//a[@href='/auth/login']"));
		Signin.click();
		WebElement EmailField = driver.findElement(By.id("email"));
		EmailField.clear();
		driver.findElement(By.id("email")).sendKeys("InnCorrectData@gmail.com");
		
		WebElement PasswordField = driver.findElement(By.id("password"));
		PasswordField.clear();
		driver.findElement(By.id("password")).sendKeys("12345678@");
		
		WebElement loginBtn = driver.findElement(By.className("btnSubmit"));
		loginBtn.click();
		Thread.sleep(1000);
		// alert alert-danger
		Boolean ActualMessage = driver.findElement(By.cssSelector(".alert.alert-danger")).isDisplayed();
		Assert.assertEquals(ActualMessage, ExpectedIncorrectDataMessage);

	}

	@Test(priority = 7, enabled = true)
	public void Login() throws InterruptedException {
		WebElement Signin = driver.findElement(By.xpath("//a[@href='/auth/login']"));
		Signin.click();
		Thread.sleep(2000);
		WebElement EmailField = driver.findElement(By.id("email"));
		EmailField.clear();
		driver.findElement(By.id("email")).sendKeys(Email);
		
		WebElement PasswordField = driver.findElement(By.id("password"));
		PasswordField.clear();
		driver.findElement(By.id("password")).sendKeys(Password);
		
		WebElement loginBtn = driver.findElement(By.className("btnSubmit"));
		loginBtn.click();
		Thread.sleep(3000);

		String actualName = driver.findElement(By.id("menu")).getText();
		System.out.println(expectedName);
		Assert.assertEquals(actualName.trim(), expectedName, "Username does not match.");

	}
	
	// Note :if you want to run this test you must run the login test first
		@Test(priority = 8, enabled = true)
		public void LogOut() throws InterruptedException {
			driver.findElement(By.id("menu")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//a[@data-test='nav-sign-out']")).click();
			Thread.sleep(3000);
			String ActualLink = driver.getCurrentUrl();
			Assert.assertEquals(ActualLink, ExpectedRedirectedLink);

		}

	@Test(priority = 9, enabled = true)
	public void AddProductToCart() throws InterruptedException {
		driver.get(URL);
		Thread.sleep(2000);
		List<WebElement> pageLinks = driver.findElements(By.cssSelector("ul.pagination li.page-item a.page-link"));
		int totalPages = pageLinks.size() - 2;

		Random random = new Random();
		int randomPage = random.nextInt(totalPages) + 1;
		int RandomQuantity = rand.nextInt(10) + 1;
		String quanitiy = String.valueOf(RandomQuantity);

		pageLinks.get(randomPage).click();
		System.out.println("Clicked on page number: " + pageLinks.get(randomPage).getText());
		Thread.sleep(2000);

		if (randomPage == 1) {

			List<WebElement> productCards = driver.findElements(By.cssSelector("div.card-body"));
			if (productCards.size() > 0) {
				productCards.get(0).findElement(By.className("card-img-wrapper")).click();
			}
			Thread.sleep(2000);
			WebElement QuantityField = driver.findElement(By.id("quantity-input"));
			QuantityField.clear();
			QuantityField.sendKeys(quanitiy);
			driver.findElement(By.id("btn-success btn")).click();

		} else if (randomPage == 2) {

			driver.findElement(By.xpath("//img[@alt='Open-end Spanners (Set)']")).click();
			Thread.sleep(2000);
			WebElement QuantityField = driver.findElement(By.id("quantity-input"));
			QuantityField.clear();
			QuantityField.sendKeys(quanitiy);
			driver.findElement(By.id("btn-add-to-cart")).click();

		} else if (randomPage == 3) {

			driver.findElement(By.xpath("//img[@src='assets/img/products/goggles01.avif']")).click();
			Thread.sleep(2000);
			WebElement QuantityField = driver.findElement(By.id("quantity-input"));
			QuantityField.clear();
			QuantityField.sendKeys(quanitiy);
			driver.findElement(By.id("btn-add-to-cart")).click(); // Only one cart icon here

		} else if (randomPage == 4) {

			driver.findElement(By.xpath("//img[@alt='Screws']")).click();
			Thread.sleep(2000);
			WebElement QuantityField = driver.findElement(By.id("quantity-input"));
			QuantityField.clear();
			QuantityField.sendKeys(quanitiy);
			driver.findElement(By.id("btn-add-to-cart")).click();

		} else if (randomPage == 5) {

			driver.findElement(By.xpath("//img[@alt='Drawer Tool Cabinet']")).click();
			Thread.sleep(2000);
			WebElement QuantityField = driver.findElement(By.id("quantity-input"));
			QuantityField.clear();
			QuantityField.sendKeys(quanitiy);
			driver.findElement(By.id("btn-add-to-cart")).click();

		} else {
			System.out.println("No product action defined for this page.");
			return;
		}

		System.out.println("Product added to cart.");
		Thread.sleep(2000);
		WebElement cartBadge = driver.findElement(By.cssSelector("span.badge"));
		String cartCountText = cartBadge.getText().trim();
		int cartCount = Integer.parseInt(cartCountText);
		Assert.assertEquals(cartCountText, quanitiy);
		System.out.println("Cart has " + cartCount + " item(s).");
	}

	// ==> Note : if you want to turn on this test you must turn on (Enabled = true
	// ) Add product to cart test first
	@Test(priority = 10, enabled = true)
	public void CheckOutNavigation() throws InterruptedException {
		// driver.get(URL + "products/tools");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the toast to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".toast-message")));
		driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();
		Thread.sleep(2000);
		String ActualLink = driver.getCurrentUrl();
		Assert.assertEquals(ActualLink, ExpectedCheckOutRedirectedLink);

	}

	// ==> Note : if you want to turn this test you must turn Add product to cart
	@Test(priority = 11, enabled = true)
	public void RemoveItemFromCart() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Wait for the toast to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".toast-message")));
		driver.findElement(By.xpath("//a[@data-test='nav-cart']")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".btn.btn-danger")).click();
		Thread.sleep(2000);
		String ActualTotal = driver.findElement(By.xpath("//td[@data-test='cart-total']")).getText();
		Assert.assertEquals(ActualTotal, "$0.00");

	}
	@Test(priority = 12, enabled = true)
	public void ProductNavigationviaHeaderMenu() throws InterruptedException {
		driver.get(URL);
		Thread.sleep(3000);
		driver.findElement(By.cssSelector(".nav-link.dropdown-toggle")).click();
		Thread.sleep(2000);
		List<WebElement> categories = driver.findElements(By.cssSelector(".dropdown-menu.show .dropdown-item"));
		int RandomCategories = rand.nextInt(categories.size());
		categories.get(RandomCategories).click();

	}

	

}
