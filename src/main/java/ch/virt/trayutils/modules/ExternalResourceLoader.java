package ch.virt.trayutils.modules;

import java.io.InputStream;

/**
 * This class is a ResourceLoader for loading resources of a module
 */
public interface ExternalResourceLoader {
    InputStream load(String path);
}
