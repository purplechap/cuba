/*
 * Copyright (c) 2008 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.

 * Author: Konstantin Krivopustov
 * Created: 20.05.2009 18:19:11
 *
 * $Id$
 */
package com.haulmont.cuba.web.exception;

import com.haulmont.bali.util.ReflectionHelper;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.web.App;
import com.vaadin.server.ErrorEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.Map;

/**
 * Class that holds the collection of exception handlers and delegates unhandled exception processing to them. Handlers
 * form the chain of responsibility.
 *
 * <p>A set of exception handlers is configured by defining <code>ExceptionHandlersConfiguration</code> beans
 * in spring.xml. If a project needs specific handlers, it should define a bean of such type with its own
 * <strong>id</strong>, e.g. <code>refapp_ExceptionHandlersConfiguration</code></p>
 *
 * <p>An instance of this class is bound to {@link App}.</p>
 *
 * @author krivopustov
 * @version $Id$
 */
public class ExceptionHandlers {

    protected App app;

    protected LinkedList<ExceptionHandler> handlers = new LinkedList<>();

    protected ExceptionHandler defaultHandler;

    private Log log = LogFactory.getLog(getClass());

    public ExceptionHandlers(App app) {
        this.app = app;
        this.defaultHandler = new DefaultExceptionHandler();
    }

    /**
     * Adds new handler if it is not yet registered.
     * @param handler   handler instance
     */
    public void addHandler(ExceptionHandler handler) {
        if (!handlers.contains(handler))
            handlers.add(handler);
    }

    /**
     * Return all registered handlers.
     * @return  modifiable handlers list
     */
    public LinkedList<ExceptionHandler> getHandlers() {
        return handlers;
    }

    /**
     * Delegates exception handling to registered handlers.
     * @param event error event generated by Vaadin
     */
    public void handle(ErrorEvent event) {
        for (ExceptionHandler handler : handlers) {
            if (handler.handle(event, app))
                return;
        }
        defaultHandler.handle(event, app);
    }

    /**
     * Create all handlers defined by <code>ExceptionHandlersConfiguration</code> beans in spring.xml.
     */
    public void createByConfiguration() {
        Map<String, ExceptionHandlersConfiguration> map = AppBeans.getAll(ExceptionHandlersConfiguration.class);
        for (ExceptionHandlersConfiguration conf : map.values()) {
            for (Class aClass : conf.getHandlerClasses()) {
                try {
                    addHandler(ReflectionHelper.<ExceptionHandler>newInstance(aClass));
                } catch (NoSuchMethodException e) {
                    log.error("Unable to instantiate " + aClass, e);
                }
            }
        }
    }

    /**
     * Remove all handlers.
     */
    public void removeAll() {
        handlers.clear();
    }
}