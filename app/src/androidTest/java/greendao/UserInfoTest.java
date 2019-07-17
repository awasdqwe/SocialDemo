package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.UserInfo;
import greendao.UserInfoDao;

public class UserInfoTest extends AbstractDaoTestLongPk<UserInfoDao, UserInfo> {

    public UserInfoTest() {
        super(UserInfoDao.class);
    }

    @Override
    protected UserInfo createEntity(Long key) {
        UserInfo entity = new UserInfo();
        entity.setUserId(key);
        entity.setName("我是用户");
        entity.setSex("男");
        entity.setAge(25);
        return entity;
    }

}
