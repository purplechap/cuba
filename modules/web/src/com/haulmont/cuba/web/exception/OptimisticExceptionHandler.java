/*
 * Copyright (c) 2011 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */
package com.haulmont.cuba.web.exception;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.IFrame;
import com.haulmont.cuba.web.App;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles a JPA optimistic lock exception.
 *
 * @author krivopustov
 * @version $Id$
 */
public class OptimisticExceptionHandler extends AbstractExceptionHandler {

    public OptimisticExceptionHandler() {
        super("org.springframework.orm.jpa.JpaOptimisticLockingFailureException");
    }

    @Override
    protected void doHandle(App app, String className, String message, @Nullable Throwable throwable) {
        Pattern pattern = Pattern.compile("\\[([^-]*)-");
        Matcher matcher = pattern.matcher(message);
        String entityClassName = "";
        if (matcher.find()) {
            entityClassName = matcher.group(1);
        }

        Messages messages = AppBeans.get(Messages.class);

        String localizedEntityName = "";
        String entityName = entityClassName.substring(entityClassName.lastIndexOf(".") + 1);
        String packageName = entityClassName.substring(0, entityClassName.lastIndexOf("."));
        localizedEntityName = messages.getMessage(packageName, entityName);

        String msg = messages.formatMessage(messages.getMainMessagePack(), "optimisticException.message", "\"" + localizedEntityName + "\"");
        app.getWindowManager().showNotification(msg, IFrame.NotificationType.ERROR);
    }
}