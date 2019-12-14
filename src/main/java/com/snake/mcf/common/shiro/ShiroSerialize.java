package com.snake.mcf.common.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.shiro.io.SerializationException;
import org.springframework.data.redis.serializer.RedisSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiroSerialize implements RedisSerializer {
    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
    /**
     * 序列化
     * @param object
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object object) throws SerializationException {
        byte[] result = null;
        if (object == null) {
            return new byte[0];
        }
        try (
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)
        ){
            if (!(object instanceof Serializable)) {
                throw new IllegalArgumentException(ShiroSerialize.class.getSimpleName() + " requires a Serializable payload " +
                        "but received an object of type [" + object.getClass().getName() + "]");
            }
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result =  byteStream.toByteArray();
        } catch (Exception ex) {
            log.error("Failed to serialize",ex);
        }
        return result;
    }
    /**
     * 反序列化
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object result = null;
        if (isEmpty(bytes)) {
            return null;
        }
        try (
                ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)
        ){
            result = objectInputStream.readObject();
        } catch (Exception e) {
            log.error("Failed to deserialize",e);
        }
        return result;
    }
}
