package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.Commen;
import greendao.CommenDao;

public class CommenTest extends AbstractDaoTestLongPk<CommenDao, Commen> {

    public CommenTest() {
        super(CommenDao.class);
    }

    @Override
    protected Commen createEntity(Long key) {
        Commen entity = new Commen();
        entity.setId(key);
        entity.setName("张三");
        entity.setContent("张三来评论了");
        return entity;
    }

    @Override
    public void testInsertAndLoad() {
        super.testInsertAndLoad();
    }
}
