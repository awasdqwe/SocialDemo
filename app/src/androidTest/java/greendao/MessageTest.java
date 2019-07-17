package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.Message;
import greendao.MessageDao;

public class MessageTest extends AbstractDaoTestLongPk<MessageDao, Message> {

    public MessageTest() {
        super(MessageDao.class);
    }

    @Override
    protected Message createEntity(Long key) {
        Message entity = new Message();
        entity.setId(key);
        return entity;
    }

}
