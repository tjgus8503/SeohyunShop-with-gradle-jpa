package seohyun.app.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohyun.app.mall.models.Comments;
import seohyun.app.mall.repository.CommentsRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {
    private final CommentsRepository commentsRepository;

    @Transactional
    public void createComment(Comments comments) throws Exception {
        try{
            commentsRepository.save(comments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Comments getByIdAndUserId(String id, String userId) throws Exception {
        try{
            return commentsRepository.findOneByIdAndUserId(id, userId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void updateComment(Comments comments) throws Exception {
        try{
            commentsRepository.save(comments);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void deleteComment(String id) throws Exception {
        try{
            commentsRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public Comments getByProductQId(String productQId) throws Exception {
        try{
            return commentsRepository.findOneByProductInquiriesId(productQId);
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
