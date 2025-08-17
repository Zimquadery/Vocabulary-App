# Vocabulary App

The Vocabulary App is a desktop application built with JavaFX that allows users to search for word definitions and administrators to manage the vocabulary database. The application uses a simple text file as its database, making it lightweight and easy to deploy.

## Architecture

The application follows a Model-View-Controller (MVC) architecture pattern:

- **Model**: `VocabularyManager` handles data storage and retrieval from the text file database
- **View**: FXML files define the user interfaces
- **Controller**: Java controller classes handle user interactions and business logic

### Major Components

1. **Main Application** (`App.java`)
   - Entry point that initializes the JavaFX application
   - Loads the main view and applies styling

2. **Vocabulary Manager** (`VocabularyManager.java`)
   - Manages the vocabulary database (text file)
   - Provides search, add, and update functionality
   - Implements binary search for efficient word lookups

3. **User Interface Controllers**
   - `MainController`: Handles search functionality for regular users
   - `AdminLogin`: Manages admin authentication
   - `AdminDashbord`: Provides admin functionality for managing vocabulary

## Data Structure

The application uses a simple text file (`vocabulary.txt`) as its database with the following format:
```
word#definition
```

Each line contains a word and its definition separated by a `#` character. The vocabulary is sorted alphabetically for efficient binary search operations.

## User Interface

### Main View
- Search field for looking up word definitions
- Results display area showing the word and its definition
- Admin button for accessing administrative functions

### Admin Login
- Username and password fields (default: admin/123)
- Authentication validation

### Admin Dashboard
- Add new words with definitions
- Update existing word definitions
- Search functionality for editing words

## Admin Functionality

Administrators can access additional features by clicking the "Admin" button on the main screen and logging in with the default credentials:
- Username: `admin`
- Password: `123`

Once logged in, administrators can:
1. Add new vocabulary words and definitions
2. Update existing word definitions
3. Search for words to modify them

## How to Run

### Prerequisites
- Java 8 or higher
- JavaFX SDK

### Running the Application
1. Compile all Java files:
   ```
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml *.java
   ```

2. Run the application:
   ```
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml App
   ```

Note: Replace `/path/to/javafx/lib` with the actual path to your JavaFX SDK lib directory.

## Development

### Project Structure
```
src/
├── App.java                 # Main application entry point
├── MainController.java      # Main view controller
├── VocabularyManager.java   # Vocabulary data management
├── AdminLogin.java          # Admin authentication
├── AdminDashbord.java       # Admin dashboard controller
├── vocabulary.txt           # Vocabulary database
└── ui/
    ├── MainView.fxml        # Main user interface
    ├── AdminLogin.fxml      # Admin login interface
    ├── AdminDashboard.fxml  # Admin dashboard interface
    └── styles.css           # Application styling
```

### Key Features
- Binary search implementation for efficient word lookups
- Text file-based database for simple data persistence
- Modular FXML-based UI design
- CSS styling for a modern look and feel
- Data validation for user inputs

### Extending the Application
To add new features:
1. Create new FXML files for UI components
2. Implement corresponding controller classes
3. Update the main application to navigate to new views
4. Add new methods to VocabularyManager for data operations