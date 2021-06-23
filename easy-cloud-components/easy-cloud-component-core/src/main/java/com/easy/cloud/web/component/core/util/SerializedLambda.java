package com.easy.cloud.web.component.core.util;

import com.easy.cloud.web.component.core.enums.HttpResultEnum;
import com.easy.cloud.web.component.core.exception.BusinessException;
import com.easy.cloud.web.component.core.funinterface.SFunction;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;

/**
 * @author GR
 * @date 2020-11-3 17:39
 */
public final class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private String implMethodName;

    /**
     * 通过反序列化转换 lambda 表达式，该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    public static SerializedLambda resolve(SFunction lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new BusinessException(HttpResultEnum.FAIL.getCode(), "该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtils.serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new BusinessException(HttpResultEnum.FAIL.getCode(), "This is impossible to happen");
        }
    }

    /**
     * 获取实现者的方法名称
     *
     * @return 方法名称
     */
    public String getImplMethodName() {
        return implMethodName;
    }
}
