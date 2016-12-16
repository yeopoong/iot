package kyun.iot.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import kyun.iot.framework.exception.ServiceException;
import kyun.iot.framework.message.Param;

@Component
public class ServiceInvokerImpl implements ServiceInvoker {

    private static Logger logger = LoggerFactory.getLogger(ServiceInvoker.class);

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ServiceMapper serviceMapper;

    @Override
    public Object invoke(String serviceId, Param param) {

        Service service = serviceMapper.getService(serviceId);

        if (service == null) {
            logger.info("non-existing serviceId=[{}]", serviceId);
            service = getService(serviceId);
        }

        String serviceName = service.getSvcClassNm();
        String methodName = service.getSvcMethodNm();

        Object _service = applicationContext.getBean(serviceName);
        Method method = getMethod(_service, methodName);

        Object returnObject;
        try {
            returnObject = method.invoke(_service, param);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ServiceException(e);
        }

        return returnObject;
    }

    /**
     * @param serviceId
     * @return
     */
    private Service getService(String serviceId) {
        
        if (serviceId.length() < 3 || serviceId.indexOf('#') < 1) {
            throw new ServiceException("serviceId format error!");
        }
        
        Service service = new Service();

        String[] split = serviceId.split("#");

        service.setSvcClassNm(split[0]);
        service.setSvcMethodNm(split[1]);

        return service;
    }

    /**
     * BEAN METHOD 생성
     * 
     * @param bean
     * @param methodName
     * @return
     */
    private Method getMethod(Object bean, String methodName) {
        Method method = null;

        try {
            method = bean.getClass().getMethod(methodName, new Class[] { Param.class });
        } catch (NoSuchMethodException e) {
            throw new ServiceException(e);
        }

        return method;
    }
}
