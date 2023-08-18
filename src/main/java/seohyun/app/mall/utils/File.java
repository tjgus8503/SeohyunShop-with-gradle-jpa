package seohyun.app.mall.utils;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class File {

    public void DeleteFile(String priorImage) throws Exception {
        try{
            if (priorImage != null) {
                List<String> test = List.of(priorImage.split(","));
                for (String image : test) {
                    Files.delete(Path.of(image));
                }
            }
        } catch (Exception e){
            throw new Exception(e);
        }
    }
}
