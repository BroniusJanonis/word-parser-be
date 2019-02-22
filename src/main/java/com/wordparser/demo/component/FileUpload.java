package com.wordparser.demo.component;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FILE_UPLOAD")
public class FileUpload {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Id
    @Column(name = "id")
    private String id;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "file_content")
    private byte[] fileContent;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "uploaded_on")
    private LocalDateTime uploadedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private FileContentType contentType;

    public FileUpload() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(LocalDateTime uploadedOn) {
        this.uploadedOn = uploadedOn;
    }

    public FileContentType getContentType() {
        return contentType;
    }

    public void setContentType(FileContentType contentType) {
        this.contentType = contentType;
    }


    public static class FileUploadBuilder {
        private String id;
        private byte[] fileContent;
        private String fileName;
        private FileContentType contentType;
        private LocalDateTime uploadedOn;

        private FileUploadBuilder() {
        }

        public static FileUploadBuilder aFileUpload() {
            return new FileUploadBuilder();
        }

        public FileUploadBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public FileUploadBuilder withFileContent(byte[] fileContent) {
            this.fileContent = fileContent;
            return this;
        }

        public FileUploadBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FileUploadBuilder withContentType(FileContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public FileUploadBuilder withUploadedOn(LocalDateTime uploadedOn) {
            this.uploadedOn = uploadedOn;
            return this;
        }

        public FileUpload build() {
            FileUpload fileUpload = new FileUpload();
            fileUpload.setId(id);
            fileUpload.setFileContent(fileContent);
            fileUpload.setFileName(fileName);
            fileUpload.setContentType(contentType);
            fileUpload.setUploadedOn(uploadedOn);
            return fileUpload;
        }
    }
}
