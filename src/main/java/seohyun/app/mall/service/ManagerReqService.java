package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.ManagerRequests;
import seohyun.app.mall.repository.ManagerReqRepository;

import java.util.List;

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
            throw new Exception(e);
        }
    }

    @Transactional
    public void acceptManagerReq(ManagerRequests managerRequests) throws Exception {
        try{
            managerReqRepository.save(managerRequests);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteManagerReq(String id, String userId) throws Exception {
        try{
            managerReqRepository.deleteByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<ManagerRequests> getAllManagerReq(Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return managerReqRepository.findAll(pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

}
