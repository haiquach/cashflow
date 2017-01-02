package com.hquach.repository;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import com.hquach.form.CashFlowForm;
import com.hquach.model.Dropbox;
import com.hquach.model.HouseHold;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Dropbox repository for dropbox access
 */
@Repository
public class DropboxRepository {

    private final static DbxRequestConfig CONFIG = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    private final static String FOLDER_DILIMITER = "/";
    private final static String DOT = ".";

    @Autowired
    private HouseHoldRepository houseHoldRepository;

    public String upload(MultipartFile file) throws DbxException, IOException {
        String path = buildPath(file);
        if (path == null) {
            return null;
        }
        FileMetadata metadata = getDropboxClient().files().uploadBuilder(path)
                .uploadAndFinish(file.getInputStream());
        return path;
    }

    public FileMetadata download(String path, OutputStream outputStream) throws IOException, DbxException {
        return getDropboxClient().files().downloadBuilder(path).download(outputStream);
    }

    private String buildPath(MultipartFile file) {
        if (file != null && file.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(FOLDER_DILIMITER);
        builder.append(SecurityContextHolder.getContext().getAuthentication().getName());
        builder.append(FOLDER_DILIMITER);
        Calendar calendar = Calendar.getInstance();
        builder.append(calendar.get(Calendar.YEAR));
        builder.append(FOLDER_DILIMITER);
        builder.append(RandomStringUtils.randomAlphanumeric(15));
        builder.append(DOT);
        builder.append(FilenameUtils.getExtension(file.getOriginalFilename()));
        return builder.toString();
    }

    public Dropbox getDropbox(String token) {
        try {
            DbxClientV2 client = new DbxClientV2(CONFIG, token);
            FullAccount account = client.users().getCurrentAccount();
            return new Dropbox(token, account.getEmail(), account.getName().getDisplayName());
        } catch (Exception ex) {
            return null;
        }
    }

    private DbxClientV2 getDropboxClient() {
        HouseHold houseHold = houseHoldRepository.getHouseHold();
        if (houseHold == null || houseHold.getDropbox() == null) {
            return null;
        }
        return new DbxClientV2(CONFIG, houseHold.getDropbox().getToken());
    }
}
