package xbl.http;

import org.junit.Before;
import org.junit.Test;
import xbl.Friend;
import xbl.Session;

import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: A018189
 * Date: Oct 29, 2009
 * Time: 3:26:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionTest {
    private String username;
    private String password;
    private String proxyHost;
    private int proxyPort;

    @Before
    public void loadEnvironment()
    {
        username = System.getenv("xbl.user");
        password = System.getenv("xbl.pass");
        proxyHost = System.getenv("proxy.host");
        proxyPort = Integer.parseInt(System.getenv("proxy.port"));
    }

    @Test
    public void signIn()
    {
        Session session = new Session(proxyHost, proxyPort);
        session.signIn(username, password);
        Profile profile = session.getProfile("Jabbslad");
        Friend friend = profile.toFriend();
        assertNotNull(friend);
    }

}
