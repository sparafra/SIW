package serviceHibernate;

import java.util.List;

import databaseHibernate.NoticeDao;
import modelHibernate.Notice;

public class NoticeService {
	
	private static NoticeDao noticeDao;
	
	public NoticeService()
	{
		noticeDao = new NoticeDao();
	}
	
	public void persist(Notice entity) {
		noticeDao.openCurrentSessionwithTransaction();
		noticeDao.persist(entity);
		noticeDao.closeCurrentSessionwithTransaction();
    }
 
    public void update(Notice entity) {
    	noticeDao.openCurrentSessionwithTransaction();
    	noticeDao.update(entity);
    	noticeDao.closeCurrentSessionwithTransaction();
    }
 
    public Notice findById(Long id) {
    	noticeDao.openCurrentSession();
    	Notice notice = noticeDao.findByPrimaryKey(id);
    	noticeDao.closeCurrentSession();
        return notice;
    }
 
    public void delete(Long id) {
    	noticeDao.openCurrentSessionwithTransaction();
    	Notice notice = noticeDao.findByPrimaryKey(id);
    	noticeDao.delete(notice);
    	noticeDao.closeCurrentSessionwithTransaction();
    }
 
    public List<Notice> findAll() {
    	noticeDao.openCurrentSession();
        List<Notice> notices = noticeDao.findAll();
        noticeDao.closeCurrentSession();
        return notices;
    }
 
    public NoticeDao noticeDao() {
        return noticeDao;
    }
}
