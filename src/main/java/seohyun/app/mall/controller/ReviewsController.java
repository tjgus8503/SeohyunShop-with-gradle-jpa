package seohyun.app.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seohyun.app.mall.service.ReviewsService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/reviews")
public class ReviewsController {
    private ReviewsService reviewsService;

    // TODO 0811. 구매 후기 등록
    // 구매자만 등록 가능
}
