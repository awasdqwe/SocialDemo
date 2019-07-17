package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ljc.socialdemo.db.dao.Discove;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DISCOVE".
*/
public class DiscoveDao extends AbstractDao<Discove, Long> {

    public static final String TABLENAME = "DISCOVE";

    /**
     * Properties of entity Discove.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Content = new Property(0, String.class, "content", false, "CONTENT");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Header = new Property(2, String.class, "header", false, "HEADER");
        public final static Property CreateTime = new Property(3, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Imgs = new Property(4, String.class, "imgs", false, "IMGS");
        public final static Property CommentNum = new Property(5, int.class, "commentNum", false, "COMMENT_NUM");
        public final static Property CommentId = new Property(6, String.class, "commentId", false, "COMMENT_ID");
        public final static Property IsZan = new Property(7, boolean.class, "isZan", false, "IS_ZAN");
        public final static Property ZanNum = new Property(8, int.class, "zanNum", false, "ZAN_NUM");
        public final static Property UserId = new Property(9, Long.class, "userId", false, "USER_ID");
        public final static Property Id = new Property(10, Long.class, "id", true, "_id");
    }


    public DiscoveDao(DaoConfig config) {
        super(config);
    }
    
    public DiscoveDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DISCOVE\" (" + //
                "\"CONTENT\" TEXT NOT NULL ," + // 0: content
                "\"NAME\" TEXT," + // 1: name
                "\"HEADER\" TEXT," + // 2: header
                "\"CREATE_TIME\" TEXT," + // 3: createTime
                "\"IMGS\" TEXT," + // 4: imgs
                "\"COMMENT_NUM\" INTEGER NOT NULL ," + // 5: commentNum
                "\"COMMENT_ID\" TEXT," + // 6: commentId
                "\"IS_ZAN\" INTEGER NOT NULL ," + // 7: isZan
                "\"ZAN_NUM\" INTEGER NOT NULL ," + // 8: zanNum
                "\"USER_ID\" INTEGER," + // 9: userId
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );"); // 10: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DISCOVE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Discove entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getContent());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String header = entity.getHeader();
        if (header != null) {
            stmt.bindString(3, header);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        String imgs = entity.getImgs();
        if (imgs != null) {
            stmt.bindString(5, imgs);
        }
        stmt.bindLong(6, entity.getCommentNum());
 
        String commentId = entity.getCommentId();
        if (commentId != null) {
            stmt.bindString(7, commentId);
        }
        stmt.bindLong(8, entity.getIsZan() ? 1L: 0L);
        stmt.bindLong(9, entity.getZanNum());
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(10, userId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(11, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Discove entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getContent());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String header = entity.getHeader();
        if (header != null) {
            stmt.bindString(3, header);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        String imgs = entity.getImgs();
        if (imgs != null) {
            stmt.bindString(5, imgs);
        }
        stmt.bindLong(6, entity.getCommentNum());
 
        String commentId = entity.getCommentId();
        if (commentId != null) {
            stmt.bindString(7, commentId);
        }
        stmt.bindLong(8, entity.getIsZan() ? 1L: 0L);
        stmt.bindLong(9, entity.getZanNum());
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(10, userId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(11, id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10);
    }    

    @Override
    public Discove readEntity(Cursor cursor, int offset) {
        Discove entity = new Discove( //
            cursor.getString(offset + 0), // content
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // header
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imgs
            cursor.getInt(offset + 5), // commentNum
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // commentId
            cursor.getShort(offset + 7) != 0, // isZan
            cursor.getInt(offset + 8), // zanNum
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // userId
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10) // id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Discove entity, int offset) {
        entity.setContent(cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHeader(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImgs(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCommentNum(cursor.getInt(offset + 5));
        entity.setCommentId(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIsZan(cursor.getShort(offset + 7) != 0);
        entity.setZanNum(cursor.getInt(offset + 8));
        entity.setUserId(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setId(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Discove entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Discove entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Discove entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
