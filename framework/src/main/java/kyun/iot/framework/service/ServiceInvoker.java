package kyun.iot.framework.service;

import kyun.iot.framework.message.Param;

public interface ServiceInvoker {

    /**
     * 서비스 인스턴스를 호출하고 결과를 리턴한다.
     * 서비스ID에 매핑된 서비스 클래스 정보를 조회해서 리플렉션으로 수행한다.
     * @param serviceId
     * @param data
     * @return
     */
    Object invoke(String serviceId, Param param);
}
