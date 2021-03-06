package authentication;


import org.junit.Before;
import org.junit.Test;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import uk.co.socialcalendar.authentication.ScribeAdapter;
import uk.co.socialcalendar.authentication.facebookAuth.AbstractFactory;
import uk.co.socialcalendar.authentication.facebookAuth.FacebookUserData;
import uk.co.socialcalendar.authentication.facebookAuth.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ScribeAdapterTest {
    public static final String ID = "1234";
    public static final String FIRST_NAME = "derek";
    public static final String LAST_NAME = "clark";
    public static final String GENDER = "male";
    public static final String LINK = "link";
    public static final String LOCALE = "locale";
    public static final String NAME = "derek clark";
    public static final int TIMEZONE = 1;
    public static final String UPDATED_TIME = "12:00";
    public static final String EMAIL = "dc@mail.com";
    public static final boolean VERIFIED = true;
    ScribeAdapter scribeAdapter;

    AbstractFactory mockAbstractFactory;
    Resource mockResource;
    OAuthRequest mockOauthRequest;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;

    @Before
    public void setup(){
        scribeAdapter = new ScribeAdapter();
        mockAbstractFactory = mock(AbstractFactory.class);
        mockResource = mock(Resource.class);
        mockOauthRequest = mock(OAuthRequest.class);
        setupHttpSessions();
    }

    public void setupHttpSessions(){
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(scribeAdapter instanceof ScribeAdapter);
    }

    @Test
    public void canSetVerifier(){
        Verifier verifier = new Verifier("code");
        scribeAdapter.setVerifier(verifier);
    }

    @Test
    public void convertFacebookJsonReponseToFacebookObject(){
        String jsonString = "{\"id\":\"" + ID + "\"" +
                ",\"firstName\":\"" + FIRST_NAME + "\"" +
                ",\"lastName\":\"" + LAST_NAME + "\"" +
                ",\"gender\":\"" + GENDER + "\"" +
                ",\"link\":\"" + LINK + "\"" +
                ",\"locale\":\"" + LOCALE + "\"" +
                ",\"name\":\"" + NAME + "\"" +
                ",\"timezone\":\"" + TIMEZONE + "\"" +
                ",\"email\":\"" + EMAIL + "\"" +
                ",\"updatedTime\":\"" + UPDATED_TIME + "\"" +
                ",\"verified\":\"" + VERIFIED + "\"" +
                "}";
        FacebookUserData fb = scribeAdapter.unmarshallToObject(jsonString, FacebookUserData.class);
        assertEquals(ID,fb.getId());
        assertEquals(FIRST_NAME, fb.getFirstName());
        assertEquals(LAST_NAME, fb.getLastName());
        assertEquals(GENDER, fb.getGender());
        assertEquals(LINK, fb.getLink());
        assertEquals(LOCALE, fb.getLocale());
        assertEquals(NAME, fb.getName());
        assertEquals(TIMEZONE, fb.getTimezone());
        assertTrue(fb.isVerified());
        assertEquals(UPDATED_TIME, fb.getUpdatedTime());
        assertEquals(EMAIL, fb.getEmail());
    }

    @Test
    public void testGetOauthRequest(){
        setupOauthRequestMock();
        OAuthRequest actualRequest = scribeAdapter.getOauthRequest("fbResource");
        assertTrue(actualRequest instanceof OAuthRequest);
        assertEquals("fbResource",actualRequest.getUrl());
        assertEquals(Verb.GET, actualRequest.getVerb());
    }

    private void setupOauthRequestMock() {
        scribeAdapter.setAbstractFactory(mockAbstractFactory);
//        when(mockResource.getFacebookResource(anyString())).thenReturn(new OAuthRequest(Verb.GET, "fbResource"));
        OAuthRequest request = new OAuthRequest(Verb.GET, "fbResource");
        when(mockResource.getFacebookResource(anyString())).thenReturn(request);
        when(mockAbstractFactory.getOauthRequestResource()).thenReturn(mockResource);
    }

    private void setupOauthRequestMockOld() {
        scribeAdapter.setAbstractFactory(mockAbstractFactory);
//        when(mockResource.getFacebookResource(anyString())).thenReturn(new OAuthRequest(Verb.GET, "fbResource"));
        when(mockResource.getFacebookResource(anyString())).thenReturn(mockOauthRequest);
        when(mockAbstractFactory.getOauthRequestResource()).thenReturn(mockResource);
    }

//    @Test
    public void getApiResponse() throws IOException {
        setupOauthRequestMock();
        OAuthRequest actualRequest = scribeAdapter.getOauthRequest("fbResource");
        OAuthRequest spyRequest = spy(actualRequest);
        Response mockResponse = mock(Response.class);
//        doNothing().when(spyRequest).send();

        scribeAdapter.getApiResponse(mockHttpServletResponse, actualRequest);
    }

//    @Test
//    public void temp() throws FileNotFoundException {
//        List list = new LinkedList();
//        list.add("1");
//        list.add("2");
//        List spy = spy(list);
//        doReturn("foo").when(spy).get(0);
//
//        System.out.println(spy.get(1));
//
//        PrintWriter realWriter = new PrintWriter("temp.txt");
//        PrintWriter spyWriter = spy(realWriter);
//
//        doNothing().when(spyWriter).write(anyString());
//
//        realWriter.write("hi");
//        spyWriter.write("hello");
//        realWriter.close();
//
//    }
}
