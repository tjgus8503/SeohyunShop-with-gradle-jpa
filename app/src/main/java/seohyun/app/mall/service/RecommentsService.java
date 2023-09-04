package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.ReComments;
import seohyun.app.mall.repository.ReCommentsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommentsService {
    private final ReCommentsRepository reCommentsRepository;

    @Transactional
    public void createReComment(ReComments reComments) throws Exception {
        try{
            reCommentsRepository.save(reComments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public ReComments getByIdAndUserId(String id, String userId) throws Exception {
        try{
            return reCommentsRepository.findOneByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception();
        }
    }

    @Transactional
    public void updateReComment(ReComments reComments) throws Exception {
        try{
            reCommentsRepository.save(reComments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteReComment(String id) throws Exception {
        try{
            reCommentsRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception();
        }
    }

    public ReComments getReComment(String commentsId) throws Exception {
        try{
            return reCommentsRepository.findOneByCommentsId(commentsId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public ReComments getByCommentsId(String commentsId) throws Exception{
        try{
            return reCommentsRepository.findOneByCommentsId(commentsId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteReCommentByProductId(String id) throws Exception {
        try{
            reCommentsRepository.deleteAllByProductId(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
