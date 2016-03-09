package net.m4.netcourier.spring;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.metafour.jdbc.ds.MfDataSource;

public class MockJNDISupport {
	private static final String PROP = "database.properties";

	private static final String TYPE = "database.type", PORT = "database.port";
	private static final String HOST = "database.host", NAME = "database.name";
	private static final String USER = "database.user", PASS = "database.pass";

	private static final String PG_H = "pgsql.host", PG_N = "pgsql.name", PG_U = "pgsql.user", PG_P = "pgsql.pass";
	private static final String MF_H = "mfsql.host", MF_N = "mfsql.name";
	
	public static final void initialize() {
		Properties props = new Properties();
		try {
			props.load(getContextClassLoader().getResourceAsStream(PROP));
		} catch (IOException e) {
			throw new Error("Failed to load test properties", e);
		}

		DataSource pgds = pgDataSource(props);
		DataSource mfds = mfDataSource(props);
		DataSource ds = props.getProperty(TYPE).equals("PG") ? pgds : mfds;
		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		builder.bind("java:comp/env/jdbc/appDS", ds);
		builder.bind("java:comp/env/jdbc/pgDS", pgds);
		builder.bind("java:comp/env/jdbc/mfDS", mfds);
		try {
			builder.activate();
		} catch (IllegalStateException e) {
			throw new Error("Unable to configure data source", e);
		} catch (NamingException e) {
			throw new Error("Unable to configure data source", e);
		}
	}

	public static DataSource pgDataSource(Properties pr) {
		PGSimpleDataSource pgds = new PGSimpleDataSource();
		pgds.setServerName(pr.getProperty(PG_H) == null ? pr.getProperty(HOST) : pr.getProperty(PG_H));
		pgds.setDatabaseName(pr.getProperty(PG_N) == null ? pr.getProperty(NAME) : pr.getProperty(PG_N));
		pgds.setUser(pr.getProperty(PG_U) == null ? pr.getProperty(USER) : pr.getProperty(PG_U));
		pgds.setPassword(pr.getProperty(PG_P) == null ? pr.getProperty(PASS) : pr.getProperty(PG_P));
		return pgds;
	}

	public static DataSource mfDataSource(Properties pr) {
		MfDataSource ds = new MfDataSource();
		ds.setServerName(pr.getProperty(MF_H) == null ? pr.getProperty(HOST) : pr.getProperty(MF_H));
		ds.setDatabaseName(pr.getProperty(MF_N) == null ? pr.getProperty(NAME) : pr.getProperty(MF_N));
		ds.setPortNumber(pr.getProperty(PORT) == null ? 9876 : Integer.parseInt(pr.getProperty(PORT)));
		return ds;
	}

	public static ClassLoader getContextClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (SecurityException ex) {}
		if (cl == null) cl = ClassLoader.getSystemClassLoader();
		return cl;
	}
}
