import com.olivine.user.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author jphao
 * @Date 2021/11/27 21:45
 * @Description
 */
//@SpringBootTest
public class MyEnumTypeHandlerTest {

    @Test
    public void enumNameTest(){
        final Gender female = Gender.FEMALE;
        System.out.println(female.name());
        System.out.println(female.ordinal());
        System.out.println(Gender.MALE.ordinal());
    }
}
