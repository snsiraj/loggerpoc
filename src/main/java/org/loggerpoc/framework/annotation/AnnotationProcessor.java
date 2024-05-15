package org.loggerpoc.framework.annotation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AnnotationProcessor {

    protected static String getJsonString(Object obj) {
      try {
        return new ObjectMapper().writeValueAsString(obj);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    public static String processAnnotations(Object obj)  {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        String result = null;
        if(!obj.getClass().isAnnotationPresent(MaskLogger.class)){
            return getJsonString(obj);
        }
        try {
           // for (Object o : obj) {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(MaskFeild.class)) {
                        MaskFeild maskFeild = field.getAnnotation(MaskFeild.class);
                        String maskedValue = maskFeild.mask();
                        map.put(field.getName(), maskedValue);
                    }
                    else {
                        map.put(field.getName(),field.get(obj) );
                    }
                }
                map2.put(obj.getClass().getSimpleName(), map);
                result = new ObjectMapper().writeValueAsString(map2);
            //}
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      return result;
    }

}
