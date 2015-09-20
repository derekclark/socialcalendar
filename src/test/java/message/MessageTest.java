package message;

import org.junit.Test;
import uk.co.socialcalendar.message.Message;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void testEquality(){
        assertEquals(new Message(), new Message());
    }

    @Test
    public void canSetText(){
        Message message = new Message();
        message.setText("text");
        assertEquals("text", message.getText());
    }
}
