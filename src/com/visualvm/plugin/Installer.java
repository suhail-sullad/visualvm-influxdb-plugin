
package com.visualvm.plugin;

import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
final class Installer extends ModuleInstall {
    public void restored() {
        ApplicationViewPluginProvider.initialize();
    }
}
