package test

import grails.util.Holders
import org.springframework.web.multipart.MultipartFile

class FileUtil {
    public static String getRootPath(){
        return Holders.servletContext?.getRealPath("")
    }

    public static File makeDirectory(String path){
        File file = new File(path)
        if(!file.exists()){
            file.mkdirs()
        }
        return file
    }

    public static String uploadBannerImage(MultipartFile multipartFile){
        if(multipartFile){
            String bannerImagePath = "${getRootPath()}banner-image/"
            makeDirectory(bannerImagePath)
            multipartFile.transferTo(new File(bannerImagePath, multipartFile.originalFilename))
            return multipartFile.originalFilename
        }
        return ""
    }
}
