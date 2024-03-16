package comp3350.teachreach.tests.persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import comp3350.teachreach.data.exceptions.DuplicateEmailException;
import comp3350.teachreach.data.hsqldb.AccountHSQLDB;
import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.objects.Account;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.tests.utils.TestUtils;

public class AccessAccountsIT
{
    private AccessAccounts accessAccounts;
    private File           tempDB;

    @Before
    public void setUp() throws IOException
    {
        this.tempDB = TestUtils.copyDB();
        final IAccountPersistence persistence = new AccountHSQLDB(this.tempDB
                                                                          .getAbsolutePath()
                                                                          .replace(
                                                                                  ".script",
                                                                                  ""));
        this.accessAccounts = new AccessAccounts(persistence);
    }

    @Test
    public void testGetAccounts()
    {
        final Map<Integer, IAccount> accounts = accessAccounts.getAccounts();
        assertEquals(2, accounts.size());
    }

    @Test
    public void testGetAccountByEmail()
    {//NO EMAIL ANYMORE
        final Optional<IAccount> account = accessAccounts.getAccountByEmail(
                "pankratz25@myumanitoba.ca");
        assertTrue(account.isPresent());
    }

    @Test
    public void testGetAccountByEmailBad()
    {
        final Optional<IAccount> account = accessAccounts.getAccountByEmail(
                "a@myumanitoba.ca");
        assertFalse(account.isPresent());
    }

    @Test
    public void testStoreAccount() throws DuplicateEmailException
    {
        final IAccount a = new Account("guderr@myumanitoba.ca",
                                       "$2a$12$xeTxmBShbtIWsT/kdxVD8.k2LI",
                                       "Robert Guderian",
                                       "He/Him",
                                       "Computer Science");
        accessAccounts.insertAccount(a);
        assertEquals(3,
                     accessAccounts
                             .getAccounts()
                             .size()); //Change number base on database
    }
/*
    @Test
    public
    void testUpdateAccount()
    {
        final Optional<IAccount> a = accessAccounts.getAccountByEmail(
                "pankratz25@myumanitoba.ca");
        final IAccount u = new Account(a.get().getAccountEmail(),
                                       "$2a$12$i" +
                                       "/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg" +
                                       ".PXVGNnjF4ld46hJe");
        accessAccounts.updateAccount(u);
        assertEquals(1, accessAccounts.getAccounts().size());
        assertEquals(
                "$2a$12$i/QZJZjGQ7leHCtg5Ttx2O3yWfmtkplQYMLg.PXVGNnjF4ld46hJe",
                accessAccounts
                        .getAccountByEmail("pankratz25@myumanitoba.ca")
                        .get()
                        .getAccountPassword());
    }

 */

    @After
    public void tearDown()
    {
        this.tempDB.delete();
    }
}
