# Java Test Automation Framework

A modern Java test automation framework supporting both API and UI testing with Allure Reporting and Jenkins CI integration.

## 🚀 Key Features

- **API Testing** using REST Assured
- **UI Testing** using Selenide
- **Allure Reports** for detailed result analysis
- **Page Object Model** for UI tests
- **Configuration Management** through Owner library
- **Automatic Test Data Cleanup**
- **Parallel Execution Support**
- **Jenkins Integration**

## 📋 Requirements

- Java 21+
- Maven 3.6+
- Chrome/Firefox browser (for UI tests)

## 🛠 Installation and Setup

### Clone the project
```bash
git clone <repository-url>
cd java-jenkins-demo
```

### Running Tests

#### API Tests
```bash
# All API tests
mvn test -Dgroups=api

# Smoke tests
mvn test -Dgroups=smoke

# Specific test
mvn test -Dtest=PetStoreApiTests
```

#### UI Tests
```bash
# All UI tests
mvn test -Dgroups=ui

# Specific UI test
mvn test -Dtest=LoginTest
```

#### All Tests
```bash
mvn clean test
```

### Generate Allure Reports
```bash
# After running tests
mvn allure:serve
```

## 🏗 Framework Architecture

### Project Structure
```
src/
├── main/java/
│   ├── api/
│   │   ├── client/          # API clients
│   │   ├── models/          # Data models
│   │   └── testdata/        # Test data
│   ├── config/              # Configuration
│   └── pages/               # Page Object models
└── test/java/
    ├── api/tests/           # API tests
    └── ui/tests/            # UI tests
```

### Core Components

#### 1. API Layer
- **BaseApiClient** - base class for all API clients
- **PetApiClient** - specialized client for Pet Store API
- **Pet** - data model for pets

#### 2. UI Layer
- **BaseWebTest** - base class for UI tests
- **LoginPage** - Page Object for login page
- **MainPage** - Page Object for main page

#### 3. Configuration
- **ConfigProvider** - centralized configuration management
- **ApiConfig** - settings for API tests
- **WebConfig** - settings for UI tests

## ⚙️ Configuration

### API Configuration (`api.properties`)
```properties
api.base.url=https://petstore.swagger.io/v2
api.timeout.connection=10000
api.timeout.read=30000
api.timeout.write=30000
api.log.requests=true
api.log.responses=true
```

### Web Configuration (`web.properties`)
```properties
web.base.url=https://www.saucedemo.com
browser.name=chrome
browser.headless=true
browser.window.width=1920
browser.window.height=1080
timeout.default=10000
timeout.page.load=30000
```

### Override via System Properties
```bash
mvn test -Dbrowser.headless=false -Dweb.base.url=https://staging.example.com
```

## 📝 Usage Examples

### API Testing

```java
public class PetStoreApiTests extends BaseApiTest {
    
    @Test
    void testCreateNewPet() {
        // Create test pet
        Pet newPet = PetTestDataBuilder.createDefaultPet();
        
        // Execute API request
        Pet createdPet = createTestPet(newPet);
        
        // Assertions
        assertNotNull(createdPet.getId());
        assertEquals(newPet.getName(), createdPet.getName());
    }
}
```

### UI Testing

```java
public class LoginTest extends BaseWebTest {
    
    @Test
    void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage();
        
        loginPage
            .openPage()
            .enterUsername("standard_user")
            .enterPassword("secret_sauce")
            .clickLoginButton();
            
        // Verify successful login
        assertTrue(MainPage.isUserLoggedIn());
    }
}
```

## 🏷 Tags and Categorization

The framework supports tags for test categorization:

- `@Tag("smoke")` - smoke tests
- `@Tag("api")` - API tests
- `@Tag("ui")` - UI tests
- `@Tag("regression")` - regression tests

## 📊 Reporting

### Allure Reports
- Automatic screenshot generation for UI tests
- Detailed logging of API requests/responses
- Test grouping by functionality
- Test execution history

### Running Reports
```bash
# Local viewing
mvn allure:serve

# Generate static reports
mvn allure:report
```

## 🔧 Jenkins Integration

### Pipeline Example
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}
```

## 🧪 Test Data

### PetTestDataBuilder
```java
// Create pet with default values
Pet pet = PetTestDataBuilder.createDefaultPet();

// Create pet with specific name
Pet pet = PetTestDataBuilder.createPetWithName("Buddy");

// Create pet with specific status
Pet pet = PetTestDataBuilder.createPetWithStatus("available");
```

## 🔄 Automatic Cleanup

The framework automatically:
- Creates test data before each test
- Cleans up created data after test execution
- Manages browser lifecycle for UI tests

## 📚 Dependencies

### Core Libraries
- **JUnit 5** - testing framework
- **REST Assured** - API testing
- **Selenide** - UI testing
- **Allure** - reporting
- **Owner** - configuration management
- **AssertJ** - fluent assertions
- **Lombok** - reducing boilerplate code

## 🐛 Troubleshooting

### Common Issues

1. **Chrome won't start**
   - Check Chrome browser installation
   - Ensure ChromeDriver is in PATH

2. **API tests failing**
   - Check API server availability
   - Verify URL configuration is correct

3. **Allure reports not generating**
   - Ensure tests ran successfully
   - Check for `allure-results` folder

## 🤝 Contributing

1. Fork the project
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- **Your Name** - *Initial work* - [GitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- Selenide team for the excellent framework
- REST Assured team for convenient API testing
- Allure team for beautiful reports