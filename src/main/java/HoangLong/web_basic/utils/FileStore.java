package HoangLong.web_basic.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileStore {
    public static String UPLOAD_FOLDER = System.getProperty("user.dir"); // lay thu muc hien tai cua project
    
    public static String getFilePath(MultipartFile multipartFile,String prefix ) {
    	if(multipartFile != null && !multipartFile.isEmpty()) {
    	  try {	
    		int index = multipartFile.getOriginalFilename().lastIndexOf(".");
    		String ext = multipartFile.getOriginalFilename().substring(index);// dinh dang file
    		String image = prefix + System.currentTimeMillis() + ext;
    		
    		// Đường dẫn của hình ảnh (File.separator = "\" ở win còn *nix thì trả về "/" )
    		Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
    		
    		multipartFile.transferTo(pathImage);
    		return image;
    	  }catch(IOException e) {
    	  }
    	}
    	return null;
    }
    
    public static List<String> getFilePaths(List<MultipartFile> multipartFiles, String prefix){
    	List<String> images = new ArrayList<String>();
    	if(multipartFiles != null) {
    		for(int i = 0; i < multipartFiles.size(); i++ ) {
    			MultipartFile multipartFile = multipartFiles.get(i);
    			try {
	    			if(multipartFile != null && !multipartFile.isEmpty()) {
	    				int index = multipartFile.getOriginalFilename().lastIndexOf(".");
	    	    		String ext = multipartFile.getOriginalFilename().substring(index);// dinh dang file
	    	    		String image = prefix + System.currentTimeMillis() + ext;
	    	    		
	    	    		// Đường dẫn của hình ảnh (File.separator = "\" ở win còn *nix thì trả về "/" )
	    	    		Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
	    	    		
	    	    		multipartFile.transferTo(pathImage);
	    	    		
	    	    		images.add(image);
	    			}
    			}catch(IOException e) {
    				
    			}
    		}
    	}
    	return images;
    }
    
    public static void deleteFile(String filePath) {
    	if(filePath != null) {
    		File file = new File(UPLOAD_FOLDER + File.separator +filePath);
    		if(file.exists()) {
    			file.delete();
    		}
    	}
    }
    
    public static void deleteFiles(List<String> filePaths) {
    	if(filePaths != null) {
    		for (String filePath : filePaths) {
				File file = new File(UPLOAD_FOLDER + File.separator +filePath);
				if(file.exists()) {
					file.delete();
				}
			}
    	}
    }
    
    
}
