package com.visualvm.plugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.application.snapshot.ApplicationSnapshot;
import com.sun.tools.visualvm.application.views.ApplicationViewsSupport;
import com.sun.tools.visualvm.core.snapshot.Snapshot;
import com.sun.tools.visualvm.core.ui.DataSourceViewPlugin;
import com.sun.tools.visualvm.core.ui.DataSourceViewPluginProvider;

/**
 *
 * @author Suhail Sullad
 */
class ApplicationViewPluginProvider extends DataSourceViewPluginProvider<Application> {

    protected DataSourceViewPlugin createPlugin(Application application) {
        return new JvmCapabilitiesViewPlugin(application, InfluxModel.create(application));
    }

    protected boolean supportsPluginFor(Application t) {
        return true;
    }

    protected boolean supportsSavePluginFor(Application application, Class<? extends Snapshot> snapshotClass) {
        return ApplicationSnapshot.class.isAssignableFrom(snapshotClass);
    }

    private ApplicationViewPluginProvider() {
    }

    static void initialize() {
        ApplicationViewsSupport.sharedInstance().getOverviewView().
                registerPluginProvider(new ApplicationViewPluginProvider());
    }

}
