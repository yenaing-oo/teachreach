## Git rules

Branch name should follow the format of `iteration<num>/(feat|refactor|fix|release|test)/<short_description>`

feat: Introduce a new feature or functionality to the project
refactor: Restructure existing code to improve readability, maintainability, or performance
fix: Address and resolve bugs or issues in the codebase
release: Prepare the codebase for a new version or release of the software
test: Involves activities related to testing, such as writing or updating tests, setting up testing environments, or running tests

| Coding Style: | 
| ------ | 
|   Variable names: camelCase      |        
|     Method names: camelCase()   |        
|Class and file names: UpperCamelCase|
|Constants and Globals: ALL_CAPS|

### **Iteration One Architecture**


![iter1At](/uploads/6433922d23cc5828ad32d8f54d9fdba6/iter1At.jpg)



### **Presentation Layer**
**AccounCreatetUI**
[link-StudentSignup](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/StudentSignUpActivity.java?ref_type=heads)
[link-TutorSignup](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/TutorSignUpActivity.java?ref_type=heads)
- User Interface for Creating Account

**LoginUI**
[link-StudentLogin](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/StudentLoginActivity.java?ref_type=heads)
[link-TutorLogin](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/TutorLoginActivity.java?ref_type=heads)
- User Interface for Login

**SearchUI**
[link-Activity](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/SearchActivity.java?ref_type=heads)
[link-ViewInterface](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/main/app/src/main/java/comp3350/teachreach/presentation/SearchRecyclerViewAdapter.java?ref_type=heads)
[link-ViewAdapter](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/SearchRecyclerViewAdapter.java?ref_type=heads)
- User Interface for Searching Tutor in Student Perspective

**ProfileUI**
[link-StudentProfile](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/StudentProfileActivity.java?ref_type=heads)
[link-TutorProfile](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/presentation/TutorProfileActivity.java?ref_type=heads)
- User Interface for user's own profile

### **Logic Layer**
**AccountCreator**
[link-Inferface](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/logic/IAccountCreator.java?ref_type=heads)
[link-Implementation](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/logic/AccountCreator.java?ref_type=heads)
- Handle Information for Account Creation, function implementation

**LoginHandler**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/logic/LoginHandler.java?ref_type=heads)
- Handle Information for User Login

**SearchSortHandler**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/logic/SearchSortHandler.java?ref_type=heads)
- Handle Information for Searching Tutor in SearchUI

### **Data Layer**
**IAccountStub**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/data/IAccountPersistence.java?ref_type=heads)
- Interface for AccountStub, contain functions header for send and retrieve data about Account

**CourseStub**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/data/CourseStub.java?ref_type=heads)
- Database for Course, contain function for send and retrieve data about Course

**AccountStub**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/data/AccountStub.java?ref_type=heads)
- Database for Account, contain  contain functions implementation for send and retrieve data about Account

**SessionStub**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/data/SessionStub.java?ref_type=heads)
- Database for Session, contain function for send and retrieve data about Session

### **Domain Specific Object**
**Account**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/Account.java?ref_type=heads)
- Contain Information for Account Data and User Objectss

**Course**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/Course.java?ref_type=heads)
- Contain Information about a Course

**User**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/User.java?ref_type=heads)
- Contain Abstract or General Information about a User

**Tutor**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/Tutor.java?ref_type=heads)
- Contain Information about a Tutor

**Student**
[link](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/Student.java?ref_type=heads)
- Contain Information about a Student

**Session**
[lnik](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/dev/app/src/main/java/comp3350/teachreach/objects/Session.java?ref_type=heads)
- Contain Information about a Session

