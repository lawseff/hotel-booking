package com.epam.booking.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class PasswordEncryptorTest {

    @DataProvider
    public static Object[][] dataProviderEncryptPassword() {
        return new Object[][] {
                { "1234", "81dc9bdb52d04dc20036dbd8313ed055" },
                { "admin", "21232f297a57a5a743894a0e4a801fc3" },
                { "Lorem", "db6ff2ffe2df7b8cfc0d9542bdce27dc" }
        };
    }

    @Test
    @UseDataProvider("dataProviderEncryptPassword")
    public void testEncryptPasswordShouldReturnEncryptedPasswordWhenPasswordSupplied(String password,
                                                                                     String expectedPassword) {
        // given

        // when
        String encryptedPassword = PasswordEncryptor.encryptPassword(password);

        // then
        Assert.assertEquals(expectedPassword, encryptedPassword);
    }

}
