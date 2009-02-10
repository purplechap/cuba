/*
 * Copyright (c) 2008 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.

 * Author: Dmitry Abramov
 * Created: 22.12.2008 18:12:13
 * $Id$
 */
package com.haulmont.cuba.web.components;

import com.haulmont.cuba.gui.components.Component;

public class DateField
    extends
        AbstractField<com.itmill.toolkit.ui.DateField>
    implements
        com.haulmont.cuba.gui.components.DateField, Component.Wrapper {

    private Resolution resolution = Resolution.MIN;

    public DateField() {
        this.component = new com.itmill.toolkit.ui.DateField();
        component.setImmediate(true);
        __setResolution(Resolution.MIN);
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
        __setResolution(resolution);
    }

    protected void __setResolution(Resolution resolution) {
        switch (resolution) {
            case MSEC: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_MSEC); break;}
            case SEC: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_SEC); break;}
            case MIN: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_MIN); break;}
            case HOUR: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_HOUR); break;}
            case DAY: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_DAY); break;}
            case MONTH: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_MONTH); break;}
            case YEAR: {component.setResolution(com.itmill.toolkit.ui.DateField.RESOLUTION_YEAR); break;}
        }
    }
}