package science.mrcuijt.loh.dbmgr;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManger {

	public static void main(String[] args) {

		InitialContext ctx = null;

		try {

			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql"); // jdbc/mysql是JNDI Name

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
