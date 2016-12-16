package kyun.iot.framework.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kyun.iot.framework.exception.ServiceException;
import kyun.iot.framework.message.Param;
import kyun.iot.framework.service.ServiceInvoker;

@RestController
@RequestMapping("/service")
public class FrontController {

    private static Logger logger = LoggerFactory.getLogger(FrontController.class);
    
    @Resource
    private ServiceInvoker serviceInvoker;

    @RequestMapping(method = RequestMethod.POST)
    public Object service(@RequestBody Param param) {
        
        String serviceId = param.getString("serviceId");
        
        if (serviceId == null) {
            throw new ServiceException("serviceId is null");
        }
        
        logger.debug("serviceId:{}", serviceId);
        logger.debug("Request Param:{}", param);

        Object result = serviceInvoker.invoke(serviceId, param);

        return result;
    }
}
