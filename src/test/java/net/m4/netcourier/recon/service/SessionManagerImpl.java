package net.m4.netcourier.recon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metafour.netcourier.model.NCSession;
import com.metafour.netcourier.service.AbstractSessionManager;
import com.metafour.netcourier.service.AppConfig;
import com.metafour.netcourier.service.ClientService;
import com.metafour.netcourier.service.ContactService;
import com.metafour.netcourier.service.SessionManager;
import com.metafour.orm.SessionFactory;

@Component
public class SessionManagerImpl extends AbstractSessionManager implements SessionManager{
	private static final Logger logger = LoggerFactory.getLogger(SessionManagerImpl.class);

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ContactService contactService;


	public SessionManagerImpl() {
		super();
	}

	/**
	 * Returns session information from single sign on cookie
	 * 
	 * @return {@link NCSession}
	 */
	public NCSession getSession() {
		return super.getSession();
	}	
}