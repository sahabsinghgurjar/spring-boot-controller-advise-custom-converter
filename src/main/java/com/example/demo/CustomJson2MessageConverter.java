package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

@Component("mappingJackson2HttpMessageConverter")
public class CustomJson2MessageConverter extends MappingJackson2HttpMessageConverter {
    @Override
    public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JavaType javaType = getJavaType(type, contextClass);
        return customReadJavaType(javaType, inputMessage);
    }

    private Object customReadJavaType(JavaType javaType, HttpInputMessage inputMessage) throws IOException {
        Map<?,?> jsonMap=null;
        try {
            if (inputMessage instanceof MappingJacksonInputMessage) {
                Class<?> deserializationView = ((MappingJacksonInputMessage) inputMessage).getDeserializationView();
                if (deserializationView != null) {
                    return this.objectMapper.readerWithView(deserializationView).forType(javaType).
                            readValue(inputMessage.getBody());
                }
            }

            InputStream inputStream= null;

                inputStream = inputMessage.getBody();
                ObjectMapper objectMapper=new ObjectMapper();
                 jsonMap = objectMapper.readValue(inputStream,Map.class);
                 System.out.println("UserName :" +jsonMap.get("userName"));

            return this.objectMapper.convertValue(jsonMap, javaType);
        }
        catch (InvalidDefinitionException ex) {
            throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
            //return "Type definition error";
        }
        catch (JsonProcessingException ex) {
            throw new CustomHttpMessageNotReadableException(jsonMap,"JSON parse error: " + ex.getOriginalMessage(), ex, inputMessage);
            //return "JSON parse error";
        }catch (Exception ex){
            throw new CustomHttpMessageNotReadableException(jsonMap,"JSON parse error: " + ex.getMessage(), ex, inputMessage);
        }
    }


}
