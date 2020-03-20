import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class SpitTest {
    @Test
    public  void  uuid(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());
    }
}
/**/