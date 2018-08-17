package pub.toki.annotation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserServiceIMPL implements UserService {
    //todo 实现该类
    public String[] getUserGroupsByName(String userName) {
        return new String[]{"user","writer","admin"};
    }
}
