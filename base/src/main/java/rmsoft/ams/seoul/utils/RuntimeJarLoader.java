package rmsoft.ams.seoul.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Slf4j
public class RuntimeJarLoader {
    public static void loadJarIndDir(String dir) {
        try {
            final URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            final Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);

            new File(dir).listFiles(new FileFilter() {
                public boolean accept(File jar) {
                    // jar 파일인 경우만 로딩
                    if (jar.toString().toLowerCase().contains(".jar")) {
                        try {
                            // URLClassLoader.addURL(URL url) 메소드 호출
                            method.invoke(loader, new Object[]{jar.toURI().toURL()});
                            log.info(jar.getName() + " is loaded.");
                        } catch (Exception e) {
                            log.info(jar.getName() + " is loaded.");
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeJarIndDir(String path) {
        try {
            URL url = new File(path).toURI().toURL();

            URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

            //urlClassLoader.

            Class<?> urlClass = URLClassLoader.class;

            Field ucpField = urlClass.getDeclaredField("ucp");
            ucpField.setAccessible(true);

            URL[] old = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
            List<URL> urlLists = new ArrayList<>();

            //URL[] newurls = new URL[old.length - 1];
            for (int i = 0; i < old.length; ++i) {
                if (!old[i].equals(url)) {
                    urlLists.add(old[i]);
                }
            }

            URL[] newArray = urlLists.toArray(new URL[urlLists.size()]);

            ucpField.set(ClassLoader.getSystemClassLoader(), new sun.misc.URLClassPath(newArray));


            //URLClassPath ucp = (URLClassPath) ucpField.get(urlClassLoader);
            //ucp.getLoader(url).close();

//            Class<?> ucpClass = URLClassPath.class;
//
//            URL[] tempUrls = ucp.getURLs();
//
//
//            Field urlsField = ucpClass.getDeclaredField("urls");
//            urlsField.setAccessible(true);
//
//            Stack urls = (Stack) urlsField.get(ucp);
//            urls.remove(url);

            //file:/Users/jspark226/IdeaProjects/seoul-ams/service-modules/long-term-preservation/build/libs/long-term-preservation-0.0.1.jar
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // 테스트
    public static void main(String[] args) {
        RuntimeJarLoader.loadJarIndDir("c:/libs");
        RuntimeJarLoader.loadJarIndDir("./lib");
    }
}
