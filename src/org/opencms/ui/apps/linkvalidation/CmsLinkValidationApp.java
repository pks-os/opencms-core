/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH & Co. KG (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.ui.apps.linkvalidation;

import org.opencms.ui.CmsVaadinUtils;
import org.opencms.ui.apps.A_CmsWorkplaceApp;
import org.opencms.ui.apps.CmsFileExplorer;
import org.opencms.ui.apps.Messages;
import org.opencms.util.CmsStringUtil;

import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * Class for the Link validation app.<p>
 */
public class CmsLinkValidationApp extends A_CmsWorkplaceApp {

    /**
     * @see org.opencms.ui.apps.A_CmsWorkplaceApp#getBreadCrumbForState(java.lang.String)
     */
    @Override
    protected LinkedHashMap<String, String> getBreadCrumbForState(String state) {

        LinkedHashMap<String, String> crumbs = new LinkedHashMap<String, String>();

        //Main page.
        if (CmsStringUtil.isEmptyOrWhitespaceOnly(state)) {
            crumbs.put("", CmsVaadinUtils.getMessageText(Messages.GUI_LINKVALIDATION_ADMIN_TOOL_NAME_SHORT_0));
            return crumbs;
        }
        return new LinkedHashMap<String, String>(); //size==1 & state was not empty -> state doesn't match to known path
    }

    /**
     * @see org.opencms.ui.apps.A_CmsWorkplaceApp#getComponentForState(java.lang.String)
     */
    @Override
    protected Component getComponentForState(String state) {

        if (state.isEmpty()) {
            m_rootLayout.setMainHeightFull(true);
            return getInternalComponent();
        }
        return null;
    }

    /**
     * @see org.opencms.ui.apps.A_CmsWorkplaceApp#getSubNavEntries(java.lang.String)
     */
    @Override
    protected List<NavEntry> getSubNavEntries(String state) {

        return null;
    }

    /**
     * Creates a layout with info panel.<p>
     *
     * @param messageString Message to be displayed
     * @return layout
     */
    private VerticalLayout getInfoLayout(String messageString) {

        VerticalLayout ret = new VerticalLayout();
        ret.setMargin(true);
        ret.addStyleName("o-center");
        ret.setWidth("100%");
        VerticalLayout inner = new VerticalLayout();
        inner.addStyleName("o-workplace-maxwidth");
        Panel panel = new Panel();
        panel.setWidth("100%");

        Label label = new Label(CmsVaadinUtils.getMessageText(messageString));
        label.addStyleName("o-report");
        panel.setContent(label);

        inner.addComponent(panel);
        ret.addComponent(inner);
        return ret;
    }

    /**
     * Returns the component for the internal link validation.<p>
     *
     * @return vaadin component
     */
    private HorizontalSplitPanel getInternalComponent() {

        m_rootLayout.setMainHeightFull(true);
        HorizontalSplitPanel sp = new HorizontalSplitPanel();
        sp.setSizeFull();
        VerticalLayout result = new VerticalLayout();
        result.setSizeFull();
        VerticalLayout intro = getInfoLayout(Messages.GUI_LINKVALIDATION_INTRODUCTION_0);
        VerticalLayout nullResult = getInfoLayout(Messages.GUI_LINKVALIDATION_NO_BROKEN_LINKS_0);

        nullResult.setVisible(false);
        CmsLinkValidationInternalTable table = new CmsLinkValidationInternalTable(intro, nullResult);
        table.setVisible(false);
        table.setSizeFull();
        table.setWidth("100%");

        result.addComponent(table);
        result.addComponent(intro);
        result.addComponent(nullResult);

        VerticalLayout leftCol = new VerticalLayout();
        leftCol.setSizeFull();
        CmsInternalResources resources = new CmsInternalResources(table);
        leftCol.addComponent(resources);

        leftCol.setExpandRatio(resources, 1);
        sp.setFirstComponent(leftCol);
        sp.setSecondComponent(result);
        sp.setSplitPosition(CmsFileExplorer.LAYOUT_SPLIT_POSITION, Unit.PIXELS);
        return sp;
    }
}
