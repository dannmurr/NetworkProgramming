
package se.kth.daniel.homework3.server.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@NamedQueries({
    @NamedQuery(
            name = "deleteAccountByName",
            query = "DELETE FROM Account acct WHERE acct.username LIKE :holderName"
    )
    ,
        @NamedQuery(
            name = "findAccountByName",
            query = "SELECT acct FROM Account acct WHERE acct.username LIKE :holderName",
            lockMode = LockModeType.OPTIMISTIC
    )
    ,
        @NamedQuery(
            name = "findAllAccounts",
            query = "SELECT acct FROM Account acct",
            lockMode = LockModeType.OPTIMISTIC
    )
})


@Entity(name = "Account")
public class Account implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;

    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "loggin", nullable = false)
    private boolean loggedin;

   // @OneToOne(cascade = CascadeType.ALL)
  //  @JoinColumn(name = "holder", nullable = false)
   // private Holder holder;

    @Version
    @Column(name = "OPTLOCK")
    private int versionNum;
    
    public Account(){
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedin = false;
    }
    
    public void logout(){
        loggedin = false;
    }
    public void login(){
        loggedin = true;
    }
    public boolean checklog(){
        return loggedin;
    }
    public String getPassword(){
        return password;
    }

    /**
     * @return A string representation of all fields in this object.
     */
        private String accountInfo() {
        return " " + this;
    }
        public String getUsername() {
        return username;
    }
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Account: [");
        stringRepresentation.append("holder: ");
        stringRepresentation.append(username);
        stringRepresentation.append(", password: ");
        stringRepresentation.append(password);
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
