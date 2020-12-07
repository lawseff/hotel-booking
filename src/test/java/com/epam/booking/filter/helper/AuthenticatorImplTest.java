package com.epam.booking.filter.helper;

import static org.mockito.Mockito.*;
import com.epam.booking.command.factory.CommandFactoryImpl;
import com.epam.booking.entity.User;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class AuthenticatorImplTest {

    private static final User EMPTY_USER = null;
    private static final User USER = mock(User.class);
    private static final User ADMIN = mock(User.class);
    private Authenticator authenticator = new AuthenticatorImpl();

    @BeforeClass
    public static void createMocks() {
        when(USER.isAdmin()).thenReturn(false);
        when(ADMIN.isAdmin()).thenReturn(true);
    }

    @DataProvider
    public static Object[][] adminCommandsDataProvider() {
        return new Object[][] {
                { CommandFactoryImpl.SAVE_PRICES_COMMAND },
                { CommandFactoryImpl.CHANGE_ROOM_STATUS_COMMAND },
                { CommandFactoryImpl.APPROVE_COMMAND },
                { CommandFactoryImpl.CHECK_IN_COMMAND },
                { CommandFactoryImpl.CHECK_OUT_COMMAND },
                { CommandFactoryImpl.SHOW_ROOMS_PAGE_COMMAND }
        };
    }

    @DataProvider
    public static Object[][] userCommandsDataProvider() {
        return new Object[][] {
                { CommandFactoryImpl.BOOK_COMMAND },
                { CommandFactoryImpl.PAY_COMMAND },
                { CommandFactoryImpl.CANCEL_RESERVATION_COMMAND },
                { CommandFactoryImpl.SIGN_OUT_COMMAND },
                { CommandFactoryImpl.SHOW_BOOK_PAGE_COMMAND },
                { CommandFactoryImpl.SHOW_RESERVATIONS_PAGE_COMMAND }
        };
    }

    @DataProvider
    public static Object[][] anyRoleCommandsDataProvider() {
        return new Object[][] {
                { CommandFactoryImpl.SHOW_HOME_PAGE_COMMAND },
                { CommandFactoryImpl.SHOW_LOGIN_PAGE_COMMAND },
                { CommandFactoryImpl.LOGIN_COMMAND },
                { CommandFactoryImpl.CHANGE_LANGUAGE_COMMAND },
                { CommandFactoryImpl.UPDATE_PAGE_COMMAND },
                { CommandFactoryImpl.SHOW_REGISTER_PAGE_COMMAND },
        };
    }

    @Test
    @UseDataProvider("adminCommandsDataProvider")
    public void hasAuthority_Admin_True(String adminCommand) {
        // given
        /* ADMIN */

        // when
        boolean hasAuthority = authenticator.hasAuthority(ADMIN, adminCommand);

        // then
        Assert.assertTrue(hasAuthority);
    }

    @Test
    @UseDataProvider("userCommandsDataProvider")
    public void hasAuthority_User_True(String userCommand) {
        // given
        /* USER */

        // when
        boolean hasAuthority = authenticator.hasAuthority(USER, userCommand);

        // then
        Assert.assertTrue(hasAuthority);
    }

    @Test
    @UseDataProvider("adminCommandsDataProvider")
    public void hasAuthority_User_False(String adminCommand) {
        // given
        /* USER */

        // when
        boolean hasAuthority = authenticator.hasAuthority(USER, adminCommand);

        // then
        Assert.assertFalse(hasAuthority);
    }

    @Test
    @UseDataProvider("anyRoleCommandsDataProvider")
    public void hasAuthority_NotRegistered_True(String anyCredentialsCommand) {
        // given
        /* EMPTY_USER */

        // when
        boolean hasAuthority = authenticator.hasAuthority(EMPTY_USER, anyCredentialsCommand);

        // then
        Assert.assertTrue(hasAuthority);
    }

    @Test
    @UseDataProvider("adminCommandsDataProvider")
    public void hasAuthority_NotRegisteredAndAdminCommands_False(String adminCommand) {
        // given
        /* EMPTY_USER */

        // when
        boolean hasAuthority = authenticator.hasAuthority(EMPTY_USER, adminCommand);

        // then
        Assert.assertFalse(hasAuthority);
    }

    @Test
    @UseDataProvider("userCommandsDataProvider")
    public void hasAuthority_NotRegisteredAndUserCommands_False(String adminCommand) {
        // given
        /* EMPTY_USER */

        // when
        boolean hasAuthority = authenticator.hasAuthority(EMPTY_USER, adminCommand);

        // then
        Assert.assertFalse(hasAuthority);
    }

}
