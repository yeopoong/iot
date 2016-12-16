package kyun.iot.framework.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceMapper {

    public List<Service> getServices();

    @Cacheable(value = "service")
    public Service getService(String serviceId);
}
