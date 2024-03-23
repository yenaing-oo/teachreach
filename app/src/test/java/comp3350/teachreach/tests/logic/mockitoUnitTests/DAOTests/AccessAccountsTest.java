package comp3350.teachreach.tests.logic.mockitoUnitTests.DAOTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


import org.junit.Test;
import org.mockito.*;
import org.mockito.Mockito.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.interfaces.IAccount;

public class AccessAccountsTest {
    @Mock
    IAccountPersistence accountPersistence;

    @InjectMocks
    AccessAccounts accessAccounts;

    @Test
    public void getAccountsTest() {
        Map<Integer, IAccount> accounts = new HashMap<>();
        accounts.put(1, new Account("guderr@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        ".RdOKAHYdRhgiw7Z5YxTd6.beOG",
                "Robert Guderian",
                "He/Him",
                "Computer Science", 1));
        accounts.put(2, new Account("mcmill5@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        "lVkQHj5sVFgrTQF4QpJWVbo9CBie",
                "Camryn Mcmillan",
                "She/Her",
                "Computer Science", 2));
        accounts.put(3, new Account("mcmill5@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        "$2a$12$r9yuopZw8rOLVK",
                "Justin Huang",
                "He/Him",
                "Computer Science", 3));
        when(accountPersistence.getAccounts()).thenReturn(accounts);

        Map<Integer, IAccount> result = accessAccounts.getAccounts();

        assertEquals(result.size(), 3);
        assertEquals("Issue with getAccounts return contents",result.get(1).getUserName(), "Robert Guderian");
        assertEquals("Issue with getAccounts return contents", result.get(2).getUserName(), "Camryn Mcmillan");
        assertEquals("Issue with getAccounts return contents", result.get(3).getUserName(), "Justin Huang");
        assertThrows("Result from getAccounts is not an unmodifiable map", UnsupportedOperationException.class,
                () -> result.put(4, new Account("sharma7@myumanitoba.ca",
                        "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                                "$2a$12$r9yuopZw8rOLVK",
                        "Ashna Sharma",
                        "He/Him",
                        "Computer Science", 4)));

    }

    @Test
    public void getAccountByEmailTest() {
        Map<Integer, IAccount> accounts = new HashMap<>();
        accounts.put(1, new Account("guderr@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        ".RdOKAHYdRhgiw7Z5YxTd6.beOG",
                "Robert Guderian",
                "He/Him",
                "Computer Science", 1));
        accounts.put(2, new Account("mcmill5@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        "lVkQHj5sVFgrTQF4QpJWVbo9CBie",
                "Camryn Mcmillan",
                "She/Her",
                "Computer Science", 2));
        accounts.put(3, new Account("mcmill5@myumanitoba.ca",
                "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI" +
                        "$2a$12$r9yuopZw8rOLVK",
                "Justin Huang",
                "He/Him",
                "Computer Science", 3));
        when(accountPersistence.getAccounts()).thenReturn(accounts);

        Optional<IAccount> result = accessAccounts.getAccountByEmail("mcmill5@myumanitoba.ca");

        assertTrue("Did not pull account correctly from getAccountByEmail", result.isPresent());
        if(result.isPresent()) {
            assertEquals("Incorrect result for getAccountByEmail", result.get().getAccountID(), 3);
        }


    }
}
