package com.visualvm.plugin;

import com.sun.tools.visualvm.core.datasource.DataSource;
import com.sun.tools.visualvm.core.ui.DataSourceViewPlugin;
import com.sun.tools.visualvm.core.ui.components.DataViewComponent;
import com.sun.tools.visualvm.core.ui.components.ScrollableContainer;

class JvmCapabilitiesViewPlugin extends DataSourceViewPlugin {
    
    private InfluxModel model2;

    public DataViewComponent.DetailsView createView(int location) {
        switch (location) {
            case DataViewComponent.TOP_RIGHT:
                return new DataViewComponent.DetailsView("Influx DB Configuration", null, 30,
                        new ScrollableContainer(new JvmCapabilitiesViewComponent(model2)), null);
            default: return null;
        }
    }
    
    
    protected void willBeAdded() {
         model2.initialize();
    }
    InfluxModel getModel2() {
        return model2;
    }
    
    
    JvmCapabilitiesViewPlugin(DataSource dataSource,InfluxModel model2) {
        super(dataSource);
        this.model2 = model2;
    }

}
