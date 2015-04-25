package authentication;

import org.junit.Before;
import org.junit.Test;
import uk.co.socialcalendar.authentication.SecurityFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SecurityFilterTest {
    SecurityFilter securityFilter;
    HttpServletRequest emptyHttpServletRequest;
    HttpServletRequest mockHttpServletRequest;
    HttpServletResponse mockHttpServletResponse;
    HttpSession mockSession;

    @Before
    public void setup(){
        securityFilter = new SecurityFilter();
        mockHttp();
    }

    public void mockHttp(){
        mockHttpServletRequest = mock(HttpServletRequest.class);
        mockHttpServletResponse = mock(HttpServletResponse.class);
        mockSession = mock(HttpSession.class);
        when(mockHttpServletRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("USER_ID")).thenReturn(null);
    }

    @Test
    public void canCreateInstance(){
        assertTrue(securityFilter instanceof SecurityFilter);
    }

    @Test
    public void shouldRedirectToLoginIfUserIdNotSetAndNotRequestingLoginPage() throws IOException, ServletException {
        when(mockHttpServletRequest.getRequestURI()).thenReturn("");
        FilterChain mockFilterChain = mock(FilterChain.class);
        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).sendRedirect(anyString());
    }

    @Test
    public void shouldContinueIfUserIdSet() throws IOException, ServletException {
        when(mockSession.getAttribute("USER_ID")).thenReturn("userId");
        when(mockHttpServletRequest.getRequestURI()).thenReturn("");
        FilterChain mockFilterChain = mock(FilterChain.class);
        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockFilterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void shouldContinueIfPassingResources() throws IOException, ServletException {
        when(mockSession.getAttribute("USER_ID")).thenReturn(null);
        when(mockHttpServletRequest.getRequestURI()).thenReturn("resource");
        FilterChain mockFilterChain = mock(FilterChain.class);
        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockFilterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void shouldContinueIfSelectingLoginPage() throws IOException, ServletException {
        when(mockSession.getAttribute("USER_ID")).thenReturn(null);
        when(mockHttpServletRequest.getRequestURI()).thenReturn("/login");
        FilterChain mockFilterChain = mock(FilterChain.class);
        securityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockFilterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);
    }


}
