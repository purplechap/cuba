/*
 * Copyright (c) 2008 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.

 * Author: Dmitry Abramov
 * Created: 10.02.2009 10:40:28
 * $Id$
 */
package com.haulmont.cuba.gui.xml.layout.loaders;

import com.haulmont.cuba.gui.xml.layout.*;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.DateField;
import org.dom4j.Element;
import org.apache.commons.lang.StringUtils;

public class DateFieldLoader extends AbstractFieldLoader {
    public DateFieldLoader(Context context, LayoutLoaderConfig config, ComponentsFactory factory) {
        super(context, config, factory);
    }

    @Override
    public Component loadComponent(ComponentsFactory factory, Element element) throws InstantiationException, IllegalAccessException {
        final DateField component = (DateField) super.loadComponent(factory, element);

        final String resolution = element.attributeValue("resolution");
        if (!StringUtils.isEmpty(resolution)) {
            component.setResolution(DateField.Resolution.valueOf(resolution));
        }
        
        return component;
    }
}
