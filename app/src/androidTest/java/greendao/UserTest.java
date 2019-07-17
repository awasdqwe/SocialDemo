package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.User;
import greendao.UserDao;

public class UserTest extends AbstractDaoTestLongPk<UserDao, User> {

    public UserTest() {
        super(UserDao.class);
    }

    @Override
    protected User createEntity(Long key) {
        User entity = new User();
        entity.setUserId(key);
        entity.setAccount("123");
        entity.setPassword("123");
        return entity;
    }

}
