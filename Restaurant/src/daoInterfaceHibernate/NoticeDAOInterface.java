package daoInterfaceHibernate;

import java.util.List;

import modelHibernate.Notice;

public interface NoticeDAOInterface {
	public void save(Notice notice);  // Create
	public Notice findByPrimaryKey(Long id);     // Retrieve
	public List<Notice> findAll();       
	public void update(Notice notice); //Update
	public void delete(Notice notice); //Delete	
}
