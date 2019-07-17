package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.Zan;
import greendao.ZanDao;

public class ZanTest extends AbstractDaoTestLongPk<ZanDao, Zan> {

    public ZanTest() {
        super(ZanDao.class);
    }

    @Override
    protected Zan createEntity(Long key) {
        Zan entity = new Zan();
        entity.setId(key);
        entity.setName("张三");
        return entity;
    }

}
