
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.daniel.homework3.server.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@NamedQueries({
    @NamedQuery(
            name = "deleteFileDataByName",
            query = "DELETE FROM FileData fdata WHERE fdata.filename LIKE :name"
    )
    ,
        @NamedQuery(
            name = "findFileDataByName",
            query = "SELECT fdata FROM FileData fdata WHERE fdata.filename LIKE :name",
            lockMode = LockModeType.OPTIMISTIC
    )
    ,
        @NamedQuery(
            name = "findAllFiles",
            query = "SELECT fdata FROM FileData fdata",
            lockMode = LockModeType.OPTIMISTIC
    )
})

@Entity(name = "FileData")
public class FileData implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileid;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "fileprivate", nullable = false)
    private boolean fileprivate;

    @Column(name = "publicwrite", nullable = false)
    private boolean publicwrite;

    @Version
    @Column(name = "OPTLOCK")
    private int versionNum;

    public FileData() {
    }

    public FileData(String user, String fileName, long size, boolean fileprivate, boolean publicwrite) {
        this.owner = user;
        this.filename = fileName;
        this.size = size;
        this.fileprivate = fileprivate;
        this.publicwrite = publicwrite;

    }

    public String getName() {
        return filename;
    }

    public long getSize() {
        return size;
    }

    public String getOwner() {
        return owner;
    }

    public boolean getPrivate() {
        return fileprivate;
    }

    public boolean getReadWrite() {
        return publicwrite;
    }

    @Override
    public String toString() {
        return "FileData{" + "fileid=" + fileid + ", filename=" + filename + ", size=" + size + ", owner=" + owner + ", fileprivate=" + fileprivate + ", publicwrite=" + publicwrite + '}';
    }

    public void setFilePrivate(boolean fileprivate) {
        this.fileprivate = fileprivate;
    }

    public void setPublicWrite(boolean publicwrite) {
        this.publicwrite = publicwrite;
    }

}
