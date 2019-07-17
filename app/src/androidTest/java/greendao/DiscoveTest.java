package greendao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.ljc.socialdemo.db.dao.Discove;
import greendao.DiscoveDao;

public class DiscoveTest extends AbstractDaoTestLongPk<DiscoveDao, Discove> {

    public DiscoveTest() {
        super(DiscoveDao.class);
    }

    @Override
    protected Discove createEntity(Long key) {
        Discove entity = new Discove();
        entity.setId(key);
        entity.setContent("我发布了一条说说");
        entity.setCommentNum(0);
        entity.setIsZan(false);
        entity.setZanNum(0);
        return entity;
    }

}
