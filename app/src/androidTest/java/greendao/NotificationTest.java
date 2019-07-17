package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.Notification;
import greendao.NotificationDao;

public class NotificationTest extends AbstractDaoTestLongPk<NotificationDao, Notification> {

    public NotificationTest() {
        super(NotificationDao.class);
    }

    @Override
    protected Notification createEntity(Long key) {
        Notification entity = new Notification();
        entity.setId(key);
        return entity;
    }

}
