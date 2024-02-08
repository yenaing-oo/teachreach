| Coding Style: | 
| ------ | 
|   Variable names: camelCase      |        
|     Method names: camelCase()   |        
|Class and file names: UpperCamelCase|
|Constants and Globals: ALL_CAPS|

### **Iteration One Architecture**

![iter1Archit](/uploads/c2778bca86a17e1f3004e7f40a5b69dc/iter1Archit.jpg)




### **Presentation Layer**
**AccounCreatetUI**
[](url)
- User Interface for Creating Account

**LoginUI**
[](url)
- User Interface for Login

**SearchUI**
[](https://code.cs.umanitoba.ca/comp3350-winter2024/git-gud-a02-2/-/blob/main/app/src/main/java/comp3350/teachreach/presentation/SearchRecyclerViewAdapter.java?ref_type=heads)
- User Interface for Searching Tutor in Student Perspective

**ProfileUI**
[](url)
- User Interface for user's own profile

### **Logic Layer**
**AccountCreator**
[](url)
- Handle Information for Account Creation

**LoginHandler**
[](url)
- Handle Information for User Login

**SearchSortHandler**
[](url)
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

