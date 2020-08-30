package ch.virt.trayutils.modules;

import java.awt.image.DirectColorModel;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class loads modules in from disk
 * @author VirtCode
 * @version 1.0
 */
public class JarModuleLoader {

    public static final String DIR = System.getenv("APPDATA") + "/TrayUtils/modules/";
    private static final String TAG = "[JarModuleLoader] "; 
    private ArrayList<Class<?>> moduleClasses;
    private ArrayList<ClassLoader> classLoaders;

    /**
     * Loads all jar modules from the jar module directory
     */
    public void load(){
        System.out.println(TAG + "Going to load Jar Modules");

        moduleClasses = new ArrayList<>();
        classLoaders = new ArrayList<>();

        File dir = new File(DIR);
        dir.mkdir();
        File[] files = dir.listFiles((dir1, name) -> (name.endsWith(".jar")));
        if (files == null) return;

        for (File file : files) {
            try {
                loadJar(file);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(TAG + "Failed to load Jar: " + file.getName());
                e.printStackTrace();
            }
        }

        System.out.println(TAG + "Finished loading Jar Modules");
    }

    /**
     * Loads the given file as a jar
     * @param file jar to load
     * @throws IOException exception thrown by the file loading
     * @throws ClassNotFoundException exception thrown by the class loading
     */
    public void loadJar(File file) throws IOException, ClassNotFoundException {
        System.out.println(TAG + "Loading Jar: " + file);
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + file.getName() +"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}, JarModuleLoader.class.getClassLoader());

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            if (className.endsWith("module-info")) continue;
            className = className.replace('/', '.');
            addIfModule(cl.loadClass(className), cl);
        }
    }

    /**
     * Adds the class to discovered modules if it is one
     * @param c class to check
     */
    public void addIfModule(Class<?> c, ClassLoader loader){
        if (c.getSuperclass() != null && isAssociatedWithModule(c) && !Modifier.isAbstract(c.getModifiers())){
            System.out.println(TAG + "Found Module class: " + c.getName());
            moduleClasses.add(c);
            classLoaders.add(loader);
        }
    }

    /**
     * Returns whether the given class does extend the module class
     * @param c class to check
     * @return does extend
     */
    private boolean isAssociatedWithModule(Class<?> c){
        while (c.getSuperclass() != null){
            c = c.getSuperclass();

            if (c.getName().equals(Object.class.getName())) return false;
            else if (c.getName().equals(Module.class.getName())) return true;
        }
        return false;
    }

    /**
     * Registers all module the loader has found when loading all the jars
     * @param loader ModuleLoader to register the modules in
     */
    public void registerFoundModules(ModuleLoader loader){
        if (moduleClasses == null) return;
        for (Class<?> moduleClass : moduleClasses) {
            try {
                Module module = (Module) moduleClass.getConstructor().newInstance();

                final ClassLoader cl = classLoaders.get(moduleClasses.indexOf(moduleClass));
                module.setResourceLoader(cl::getResourceAsStream);
                
                loader.registerModule(module);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.err.println(TAG + "Failed to load Module: " + moduleClass.getName());
                e.printStackTrace();
            }
        }
    }

}
