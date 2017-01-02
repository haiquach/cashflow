package com.hquach.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * File Bucket contains File for upload process.
 */
public class FileBucket {
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
