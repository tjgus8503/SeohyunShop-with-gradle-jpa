package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.ManagerRequests;
import seohyun.app.mall.repository.ManagerReqRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerReqService {
    private final ManagerReqRepository managerReqRepository;

    @Transactional
    public void createManagerReq(ManagerRequests managerRequests) throws Exception {
        try{
            managerReqRepository.save(managerRequests);
        } catch (Exception e){
            throw new Exception();
        }
    }
}
