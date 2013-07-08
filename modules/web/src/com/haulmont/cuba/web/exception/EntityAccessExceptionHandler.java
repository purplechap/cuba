/*
 * Copyright (c) 2011 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.cuba.web.exception;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.EntityAccessException;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.IFrame;
import com.haulmont.cuba.web.App;

import javax.annotation.Nullable;

/**
 * Handles {@link com.haulmont.cuba.core.global.EntityAccessException}.
 *
 * @author pavlov
 * @version $Id$
 */
public class EntityAccessExceptionHandler extends AbstractExceptionHandler {

    public EntityAccessExceptionHandler() {
        super(EntityAccessException.class.getName());
    }

    @Override
    protected void doHandle(App app, String className, String message, @Nullable Throwable throwable) {
        String msg = AppBeans.get(Messages.class).formatMessage(getClass(), "entityAccessException.message");
        app.getWindowManager().showNotification(msg, IFrame.NotificationType.WARNING);
    }

    public void handle(EntityAccessException e, App app) {
        doHandle(app, EntityAccessException.class.getName(), e.getMessage(), e);
    }
}