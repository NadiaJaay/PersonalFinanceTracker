# Personal Finance Tracker

Overview

Personal Finance Tracker is a web application designed to help users track their expenses, accounts, savings goals, and other financial activities. It provides a simple and intuitive interface to manage your finances.


## Instructions to Build and Run

### Prerequisites

- Java 17 or above
  
- Dynamic Web Module 4.0

- Apache Tomcat (v9.0)

- MySQL Server

- MySQL Workbench for database management

- Eclipse IDE

### Build and Run Steps
#### Clone the Project from Github
1. Open a terminal or command prompt.

2. Navigate to the directory where you want to clone the project.

3. Run the following command:

```bash
  git clone https://github.com/NadiaJaay/PersonalFinanceTracker.git
```
4. Go to File -> Import -> Existing Projects into Workspace.

5. Select the cloned project folder.

### Add Required JARs to the Build Path

1. Right-click on the project in Eclipse and select Properties.

2. Go to Java Build Path -> Libraries.

3. Click on Add JARs > navigate to PersonalFinanceTracker > src > main > webapp > WEB-INF > lib

4. Select all files in the lib folder and click 'ok'

5. Click Apply and Close to save the changes.

### Configure Apache Tomcat 9

1. In Eclipse, go to Window -> Preferences -> Server -> Runtime Environments.

2. Click Add... and select Apache Tomcat v9.0.

3. Browse to the Tomcat installation directory and click Finish.

4. Right-click the project and select Run As -> Run on Server.

5. Choose the configured Tomcat server.

### Configure the Database

1. Update the dbutils file with your MySQL credentials (username, password, and port).

### Start the Application

1. The database will be automatically created on startup.

2. Run the project on Tomcat:

    - Right-click the project in Eclipse and select Run As -> Run on Server.

    - Choose your configured Apache Tomcat server.

### Access the Application

Open your browser and go to http://localhost:8080/PersonalFinanceTracker.
  - or Right-click the project and select Run As -> Run on Server.

## Troubleshooting

If you encounter any issues, ensure that:

- The database credentials in dbutils are correct.

- Apache Tomcat is running on the configured port.

- All required JAR files have been added to the project build path.
  
- Project Facets reflects the correct  Dynamic Web Module

## Note
Upon registration, you will need to verify your account. An email will be sent to you from personalfinanceteam@gmail.com containing your verifiation link.
The email automatically gets sent to junk/spam, please locate it from there.

