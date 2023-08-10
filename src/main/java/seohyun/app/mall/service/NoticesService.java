package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Notices;
import seohyun.app.mall.repository.NoticesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticesService {
    private final NoticesRepository noticesRepository;

    @Transactional
    public void createNotice(Notices notices) throws Exception {
        try{
            noticesRepository.save(notices);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<Notices> getAllNotices(Integer pageNumber, Integer pageSize) throws Exception {
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return noticesRepository.findAll(pageable);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Notices getNotice(Long id) throws Exception {
        try{
            return noticesRepository.findOneById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
