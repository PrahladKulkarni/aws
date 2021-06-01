package com.aws.vokunev.catalog.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.aws.vokunev.catalog.model.AccessToken;
import com.aws.vokunev.catalog.model.dao.AccessTokenDataAccessor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for UserDataAccessorTest")
public class AccessTokenDataAccessorTest {

    /**
     * Initializing variables before we run the tests. Use @BeforeAll for
     * initializing static variables at the start of the test class execution.
     * Use @BeforeEach for initializing variables before each test is run.
     */
    @BeforeAll
    static void setup() {
    }

    /**
     * De-initializing variables after we run the tests. Use @AfterAll for
     * de-initializing static variables at the end of the test class execution.
     * Use @AfterEach for de-initializing variables at the end of each test.
     */
    @AfterAll
    static void tearDown() {
    }

    @Test
    @DisplayName("Test for retrieving user object")
    void testParseAccessToken() {
        String access_token_encoded = "eyJraWQiOiJsNENVeHFuU2lCYmVrYmJqTG1OSUhpVXdLcCtGODNxV3FqaEJXUnNoRDBJPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NzY3Njk1Zi1kY2JmLTQyMzYtYjljNS1lMGY5N2FlNjM5ZWIiLCJjb2duaXRvOmdyb3VwcyI6WyJlbmdpbmVlcnMiLCJtYW5hZ2VycyJdLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTYyMjUwNTI3NSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMi5hbWF6b25hd3MuY29tXC91cy13ZXN0LTJfQUZXZ0F5cWlJIiwiZXhwIjoxNjIyNTA4ODc1LCJpYXQiOjE2MjI1MDUyNzUsInZlcnNpb24iOjIsImp0aSI6ImE1MTRjODg5LWU4ZDItNGE5Ni1hYTJiLTY3MjIwNmQ4MjExMyIsImNsaWVudF9pZCI6IjM0ZmFrOTdqcnQyNWY0YnFkdmFkMjhyc2RkIiwidXNlcm5hbWUiOiJpbnN0cnVjdG9yIn0.VMydxjk8gAjPjwS9dzRSX3xhBTe3Glc3EuFU6ZhHG1qn_4VSMzb6u083DwpplqPu_IOzOpLWnWVV4hHrYQOGerjVhxJgpMrrUh8MoAaOzSsUL2OGPFfUKR23VEgXJejZmvmhN-rLOgnAh2f852W1-ZS9MT4xFouG6ChCH3JOG864pfTqIYtChq1D7GoelsY0d1xlO46RxyYVozkBMdYUk6BSsryoazEQztf3slF27FhVO7TIlRzFwGwwZ0JKjYqbShLEKklf1cS3AO0VxddOVBwQ0XA_S82L9d6Ah0wrzugTOjv5MD7hTXaFjr7tfrQ56eg_wvzscTykq5yuxaS9MQ";
        AccessToken token = AccessTokenDataAccessor.getToken(access_token_encoded);
        assertNotNull(token);
        assertNotNull(token.getUsername());
        assertNotNull(token.getGroups());
        assertTrue(token.isManagers());
    }        
}
