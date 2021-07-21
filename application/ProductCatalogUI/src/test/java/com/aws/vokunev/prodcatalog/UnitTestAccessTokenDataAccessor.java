package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aws.vokunev.prodcatalog.dao.AccessTokenDataAccessor;
import com.aws.vokunev.prodcatalog.model.AccessToken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTestAccessTokenDataAccessor {

	@Autowired
	private AccessTokenDataAccessor accessTokenDataAccessor;        

    @Test
    @DisplayName("Test for retrieving user object")
    void testParseAccessToken() {
        String access_token_encoded = "eyJraWQiOiJsNENVeHFuU2lCYmVrYmJqTG1OSUhpVXdLcCtGODNxV3FqaEJXUnNoRDBJPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI5NzY3Njk1Zi1kY2JmLTQyMzYtYjljNS1lMGY5N2FlNjM5ZWIiLCJjb2duaXRvOmdyb3VwcyI6WyJlbmdpbmVlcnMiLCJtYW5hZ2VycyJdLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6Im9wZW5pZCIsImF1dGhfdGltZSI6MTYyMjUwNTI3NSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMi5hbWF6b25hd3MuY29tXC91cy13ZXN0LTJfQUZXZ0F5cWlJIiwiZXhwIjoxNjIyNTA4ODc1LCJpYXQiOjE2MjI1MDUyNzUsInZlcnNpb24iOjIsImp0aSI6ImE1MTRjODg5LWU4ZDItNGE5Ni1hYTJiLTY3MjIwNmQ4MjExMyIsImNsaWVudF9pZCI6IjM0ZmFrOTdqcnQyNWY0YnFkdmFkMjhyc2RkIiwidXNlcm5hbWUiOiJpbnN0cnVjdG9yIn0.VMydxjk8gAjPjwS9dzRSX3xhBTe3Glc3EuFU6ZhHG1qn_4VSMzb6u083DwpplqPu_IOzOpLWnWVV4hHrYQOGerjVhxJgpMrrUh8MoAaOzSsUL2OGPFfUKR23VEgXJejZmvmhN-rLOgnAh2f852W1-ZS9MT4xFouG6ChCH3JOG864pfTqIYtChq1D7GoelsY0d1xlO46RxyYVozkBMdYUk6BSsryoazEQztf3slF27FhVO7TIlRzFwGwwZ0JKjYqbShLEKklf1cS3AO0VxddOVBwQ0XA_S82L9d6Ah0wrzugTOjv5MD7hTXaFjr7tfrQ56eg_wvzscTykq5yuxaS9MQ";
        AccessToken token = accessTokenDataAccessor.getToken(access_token_encoded);
        assertNotNull(token);
        assertNotNull(token.getUsername());
        assertNotNull(token.getGroups());
        assertTrue(token.isManagers());
    }        
}
