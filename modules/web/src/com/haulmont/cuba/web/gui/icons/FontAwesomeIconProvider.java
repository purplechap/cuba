/*
 * Copyright (c) 2008-2017 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.cuba.web.gui.icons;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.web.WebConfig;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FontAwesomeIconProvider extends AbstractIconProvider {
    private final Logger log = LoggerFactory.getLogger(FontAwesomeIconProvider.class);

    protected static final String[] FONT_AWESOME_PREFIXES = {"font-icon", "font-awesome-icon"};

    @Override
    public Resource getIconResource(String iconPath) {
        if (StringUtils.isEmpty(iconPath)) {
            throw new IllegalArgumentException("Icon path should not be empty");
        }

        String iconName = iconPath.contains(":") ? iconPath.split(":")[1] : iconPath;

        Resource themeIcon = getIconFromTheme(iconName);
        if (themeIcon != null)
            return themeIcon;

        try {
            return ((Resource) FontAwesome.class
                    .getDeclaredField(iconName)
                    .get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.warn("Unable to find icon {} in the FontAwesome icon set.", iconName);
        }

        return null;
    }

    @Override
    public boolean canProvide(String iconPath) {
        boolean useFontIcons = AppBeans.get(Configuration.class)
                .getConfig(WebConfig.class).getUseFontIcons();
        if (!useFontIcons)
            return false;

        for (String prefix : FONT_AWESOME_PREFIXES) {
            if (iconPath.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }
}
