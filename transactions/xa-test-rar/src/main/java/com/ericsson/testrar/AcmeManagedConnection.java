/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.ericsson.testrar;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;

import javax.security.auth.Subject;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * AcmeManagedConnection
 * 
 * @version $Revision: $
 */
public class AcmeManagedConnection implements ManagedConnection, XAResource,
		LocalTransaction {

	/** The logger */
	private static Logger log = Logger.getLogger("AcmeManagedConnection");

	/** The logwriter */
	private PrintWriter logwriter;

	/** ManagedConnectionFactory */
	private AcmeManagedConnectionFactory mcf;

	/** Listeners */
	private List<ConnectionEventListener> listeners;

	/** Connection */
	private AcmeConnectionImpl connection;

	private int timeout;

	/**
	 * Default constructor
	 * 
	 * @param mcf
	 *            mcf
	 */
	public AcmeManagedConnection(AcmeManagedConnectionFactory mcf) {
		this.mcf = mcf;
		this.logwriter = null;
		this.listeners = Collections
				.synchronizedList(new ArrayList<ConnectionEventListener>(1));
		this.connection = null;
	}

	/**
	 * Creates a new connection handle for the underlying physical connection
	 * represented by the ManagedConnection instance.
	 * 
	 * @param subject
	 *            Security context as JAAS subject
	 * @param cxRequestInfo
	 *            ConnectionRequestInfo instance
	 * @return generic Object instance representing the connection handle.
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public Object getConnection(Subject subject,
			ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		log.finest("getConnection()");
		connection = new AcmeConnectionImpl(this, mcf);
		return connection;
	}

	/**
	 * Used by the container to change the association of an application-level
	 * connection handle with a ManagedConneciton instance.
	 * 
	 * @param connection
	 *            Application-level connection handle
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public void associateConnection(Object connection) throws ResourceException {
		log.finest("associateConnection()");

		if (connection == null)
			throw new ResourceException("Null connection handle");

		if (!(connection instanceof AcmeConnectionImpl))
			throw new ResourceException("Wrong connection handle");

		this.connection = (AcmeConnectionImpl) connection;
	}

	/**
	 * Application server calls this method to force any cleanup on the
	 * ManagedConnection instance.
	 * 
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public void cleanup() throws ResourceException {
		log.finest("cleanup()");
	}

	/**
	 * Destroys the physical connection to the underlying resource manager.
	 * 
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public void destroy() throws ResourceException {
		log.finest("destroy()");
	}

	/**
	 * Adds a connection event listener to the ManagedConnection instance.
	 * 
	 * @param listener
	 *            A new ConnectionEventListener to be registered
	 */
	public void addConnectionEventListener(ConnectionEventListener listener) {
		log.finest("addConnectionEventListener()");
		if (listener == null)
			throw new IllegalArgumentException("Listener is null");
		listeners.add(listener);
	}

	/**
	 * Removes an already registered connection event listener from the
	 * ManagedConnection instance.
	 * 
	 * @param listener
	 *            already registered connection event listener to be removed
	 */
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		log.finest("removeConnectionEventListener()");
		if (listener == null)
			throw new IllegalArgumentException("Listener is null");
		listeners.remove(listener);
	}

	/**
	 * Close handle
	 * 
	 * @param handle
	 *            The handle
	 */
	void closeHandle(AcmeConnection handle) {
		ConnectionEvent event = new ConnectionEvent(this,
				ConnectionEvent.CONNECTION_CLOSED);
		event.setConnectionHandle(handle);
		for (ConnectionEventListener cel : listeners) {
			cel.connectionClosed(event);
		}

	}

	/**
	 * Gets the log writer for this ManagedConnection instance.
	 * 
	 * @return Character ourput stream associated with this Managed-Connection
	 *         instance
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public PrintWriter getLogWriter() throws ResourceException {
		log.finest("getLogWriter()");
		return logwriter;
	}

	/**
	 * Sets the log writer for this ManagedConnection instance.
	 * 
	 * @param out
	 *            Character Output stream to be associated
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public void setLogWriter(PrintWriter out) throws ResourceException {
		log.finest("setLogWriter()");
		logwriter = out;
	}

	/**
	 * Returns an <code>javax.resource.spi.LocalTransaction</code> instance.
	 * 
	 * @return LocalTransaction instance
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public LocalTransaction getLocalTransaction() throws ResourceException {
		log.finest("getLocalTransaction() returning this");
		return this;
	}

	/**
	 * Returns an <code>javax.transaction.xa.XAresource</code> instance.
	 * 
	 * @return XAResource instance
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public XAResource getXAResource() throws ResourceException {
		log.finest("getXAResource()");
		return this;
	}

	/**
	 * Gets the metadata information for this connection's underlying EIS
	 * resource manager instance.
	 * 
	 * @return ManagedConnectionMetaData instance
	 * @throws ResourceException
	 *             generic exception if operation fails
	 */
	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		log.finest("getMetaData()");
		return new AcmeManagedConnectionMetaData();
	}

	/**
	 * Call me
	 */
	void callMe() {
		log.info("callMe()");
//		Random rand = new Random();
//		int min = 1;
//		int max = 5;

		//int randomNum = rand.nextInt((max - min) + 1) + min;

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.severe("Error in work manager while trying to sleep for "
					/*+ randomNum*/);
		}
		log.info("Work manager is executing some work after sleeping for "
				/*+ randomNum*/);
	}

	@Override
	public void begin() throws ResourceException {
		log.info("begin method called...");

	}

	@Override
	public void commit() throws ResourceException {
		log.info("commit method called...");
	}

	@Override
	public void rollback() throws ResourceException {
		log.info("rollback method called...");
	}

	@Override
	public void commit(Xid xid, boolean onePhase) throws XAException {
		log.info("commit called with xid="+xid+" and onePhase="+onePhase);

	}

	@Override
	public void end(Xid xid, int flags) throws XAException {
		log.info("End called with xid=["+xid+"] and flag "+flags);

	}

	@Override
	public void forget(Xid xid) throws XAException {
		log.info("Forget called with xid="+ xid);

	}

	@Override
	public int getTransactionTimeout() throws XAException {
		return timeout;
	}

	@Override
	public boolean isSameRM(XAResource xares) throws XAException {
		final boolean result = this.equals(xares);
		log.info("isSameRM called with xares="+xares+" and is returning "+result);
		return result;
	}

	@Override
	public int prepare(Xid xid) throws XAException {
		log.info("prepare called with xid=" + xid);
		return XA_OK;
	}

	@Override
	public Xid[] recover(int flag) throws XAException {
		log.info("Recover called " + flag);
		return null;
	}

	@Override
	public void rollback(Xid xid) throws XAException {
		log.info("rollback called for " + xid);

	}

	@Override
	public boolean setTransactionTimeout(int seconds) throws XAException {
		this.timeout = seconds;
		return true;
	}

	@Override
	public void start(Xid xid, int flags) throws XAException {
		log.info("start called for xid="+xid+" and flags="+flags);

	}

}
