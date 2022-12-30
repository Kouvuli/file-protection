package com.example.fileprotection.Entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String pathName;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "file_name")
    private String fileName;


    @Column(name="expire_at")
    private LocalDateTime expireAt;


    private String password;

    public Account() {
    }

    public Account(String pathName, String fileName, Timestamp createAt,LocalDateTime expireAt, String password) {
        this.pathName = pathName;
        this.createAt = createAt;
        this.fileName = fileName;
        this.expireAt = expireAt;
        this.password = password;
    }

//    public Account(String pathName, String fileName, Timestamp createAt, String password) {
//        this.pathName = pathName;
//        this.createAt = createAt;
//        this.fileName = fileName;
//        this.password = password;
//    }


    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
