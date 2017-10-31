/**
 * 
 * 
 * 创建时间：2017-10-29 下午11:47:07
 * @author：崔旧涛
 */
package science.mrcuijt.hibernate.util;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import science.mrcuijt.hibernate.entity.FileUploadList;

/**
 * 
 * 
 * 创建时间：2017-10-29 下午11:47:07
 * @author 崔旧涛
 * 
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	static {

		//		Configuration config = new AnnotationConfiguration().configure();
		Configuration config = new Configuration().configure();
		sessionFactory = config.buildSessionFactory();
	}

	//创建Session  
	public static Session currentSession() throws HibernateException {
		//通过线程对象.get()方法安全创建Session  
		Session s = session.get();
		// 如果该线程还没有Session,则创建一个新的Session  
		if (s == null) {
			s = sessionFactory.openSession();
			// 将获得的Session变量存储在ThreadLocal变量session里  
			session.set(s);
		}
		return s;
	}

	//关闭Session  
	public static void closeSession() throws HibernateException {
		Session s = session.get();
		if (s != null)
			s.close();
		session.set(null);
	}

	/**
	 * 入口函数值
	 *
	 * 开发时间：2017-10-29 下午11:47:07
	 * @author：崔旧涛
	 * @param：args-传入的参数
	 * @return void
	 * @param args
	 */
	public static void main(String[] args) {

		FileUploadList ful = new FileUploadList();

		ful.setFileMetaType("txt");
		ful.setFileName("test");
		ful.setFilePath("/temp");
		ful.setRequestTime(new Date());
		ful.setUploadTime(new Date());
		ful.setFinishedTime(new Date());
		ful.setFileSize("10 字节");

		Session session = HibernateUtil.currentSession();

		Transaction tran = session.beginTransaction();

		try {

			session.save(ful);

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}

		System.out.println(ful.getId());

		sessionFactory.close();
	}

}
