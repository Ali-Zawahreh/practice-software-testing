🧪 PracticeSoftwareTesting UI Automation





This repository contains an automated UI testing framework for Practice Software Testing developed using Selenium WebDriver, TestNG, and Java. The framework fetches user data dynamically from a MySQL database, simulating real-world scenarios for login, registration, and e-commerce features.

💻 Developed and maintained by Ali Zawahreh

🚀 Technologies Used
Java 17+

Selenium WebDriver

TestNG

MySQL (via JDBC)

Maven – for build and dependency management

✅ Test Coverage
✅ Homepage UI verification

✅ User registration with DB data

✅ Login functionality (valid & invalid)

✅ Shopping cart interaction

✅ Product quantity validation

🕒 Checkout and logout tests (can be enabled as needed)

🗃️ Database Integration
Test data is dynamically loaded from a local MySQL database:

bash
Copy
Edit
jdbc:mysql://localhost:3306/final_project
username: root
password: 1234
This eliminates hardcoded test values and allows flexibility in running multiple data-driven test cases.

📂 Project Structure
bash
Copy
Edit
src/
 └── test/
     └── java/
         ├── TestCases.java       # Contains shared variables & expected results
         └── AppTest.java         # Main TestNG test class with all test methods
⚙️ How to Run
Clone the repo:

bash
Copy
Edit
git clone https://github.com/Ali-Zawahreh/your-repo-name.git
cd your-repo-name
Set up your environment:

Install Java 17+, Maven, and MySQL

Add ChromeDriver to your system path

Ensure the database final_project with test data exists

Run the test suite:

bash
Copy
Edit
mvn test
🧩 Notes
Enable/disable specific tests in AppTest.java using the enabled = true/false flag in @Test annotations.

Some tests depend on others (e.g., AddProductToCart should run before CheckOutNavigation).

📬 Contact
Feel free to reach out via GitHub Issues or Ali Zawahreh's GitHub profile for questions or collaboration.

