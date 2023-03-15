package com.htdong.client.conf;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigRegister implements BeanPostProcessor, ApplicationContextAware {

    private File file;
    private Map<String, String> nodeConfigMap;
    private Long lastUpdateTime;
    private Map<String, List<ObjectField>> fieldMap = new ConcurrentHashMap<>();
    private ScheduledExecutorService pool;
    // private ApplicationContext applicationContext;

    public void init() {
        pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> execute(), 0, 5, TimeUnit.SECONDS);
    }

    public ConfigRegister(String file) {
        this.file = new File(file);
        this.lastUpdateTime = System.currentTimeMillis();
        this.nodeConfigMap = new HashMap<>(getConfigFile());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        do {
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                final ConfigValue configValue = field.getAnnotation(ConfigValue.class);
                if (configValue != null) {
                    String key = configValue.key();
                    if (!fieldMap.containsKey(key)) {
                        fieldMap.put(key, new LinkedList<>());
                    }
                    fieldMap.get(key)
                            .add(new ObjectField(new WeakReference<Object>(bean), field, configValue.defaultValue()));
                    try {
                        putConfigValue(bean, field, getValue(key, configValue.defaultValue()));
                    } catch (Exception e) {
                        throw new BeanInitializationException("putConfigValue error.", e);
                    }
                }
            }
        } while ((beanClass = beanClass.getSuperclass()) != null);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void execute() {
        try {
            long updateTime = this.file.lastModified();
            if (lastUpdateTime >= updateTime) {
                return;
            }
            lastUpdateTime = updateTime;
            nodeConfigMap.clear();
            nodeConfigMap.putAll(getConfigFile());
            for (String iter : fieldMap.keySet()) {
                update(iter);
            }
        } catch (Exception e) {
            log.error("[execute] config update error.", e);
        }
    }

    /**
     * 
     * @param configKey
     * @return
     */
    private String getValue(String configKey, String defaultValue) {
        if (ConfigValue.DEFAULT_NULL.equals(defaultValue)) {
            defaultValue = null;
        }
        return nodeConfigMap.getOrDefault(configKey, defaultValue);
    }

    private void update(String configKey) throws IllegalArgumentException, IllegalAccessException {
        if (!fieldMap.containsKey(configKey)) {
            return;
        }
        List<ObjectField> list = fieldMap.get(configKey);
        for (Iterator<ObjectField> iter = list.iterator(); iter.hasNext();) {
            ObjectField pair = iter.next();
            if (pair.getObj().get() == null) {
                iter.remove();
                continue;
            }
            putConfigValue(pair.getObj().get(), pair.getField(), getValue(configKey, pair.getDefaultValue()));
        }
    }

    private void putConfigValue(Object bean, Field field, String value)
            throws IllegalArgumentException, IllegalAccessException {
        if (bean == null) {
            return;
        }
        field.set(bean, value);
    }

    private Map<String, String> getConfigFile() {
        Map<String, String> kv = new HashMap<>();
        try (Scanner in = new Scanner(this.file)) {
            while (in.hasNextLine()) {
                String s = in.nextLine();
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                int eqInx = s.indexOf("=");
                if (eqInx == -1 || eqInx + 1 >= s.length() || StringUtils.isBlank(s.substring(eqInx + 1))) {
                    continue;
                }
                kv.put(s.substring(0, eqInx).trim(), s.substring(eqInx + 1).trim());
            }
        } catch (Exception e) {
            log.error("[getConfigFile]", e);
        }
        return kv;
    }

    private static class ObjectField {
        private WeakReference<Object> obj;
        private Field field;
        private String defaultValue;

        public ObjectField(WeakReference<Object> obj, Field field, String defaultValue) {
            this.obj = obj;
            this.field = field;
            this.defaultValue = defaultValue;
        }

        public WeakReference<Object> getObj() {
            return obj;
        }

        public Field getField() {
            return field;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}