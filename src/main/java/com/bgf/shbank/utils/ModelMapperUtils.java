package com.bgf.shbank.utils;

import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.DefaultFieldMapper;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.util.HashMap;

@Slf4j
public class ModelMapperUtils {

    private static HashMap<String, Integer> hashMap = new HashMap();

    public static <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        log.debug("[SEND_MSG][{}] {}", source.getClass().getSimpleName().replace("Recv", "Send"), source);
        return getModelMapper().map(source, destinationClass);
    }

    public static ModelMapper getModelMapper() {
        return AppContextManager.getBean(ModelMapper.class);
    }

    public static BoundMapperFacade getMapper(String beanName, String packageName) {
        MapperFactory mapperFactory = (MapperFactory) AppContextManager.getBean(MapperFactory.class);

        Class src;
        Class dest;
        try {
            src = Class.forName(packageName + "." + beanName);
            dest = Class.forName(packageName + "." + beanName + "VO");
        } catch (ClassNotFoundException var6) {
            return null;
        }


        String modelMapperName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1) + "ModelMapper";

        if (!hashMap.containsKey(beanName)) {
            hashMap.put(beanName, 1);
            mapperFactory.classMap(src, dest).byDefault(new DefaultFieldMapper[0]).customize((Mapper) AppContextManager.getBean(modelMapperName, Mapper.class)).register();
        }

        return mapperFactory.getMapperFacade(src, dest);
    }

}

