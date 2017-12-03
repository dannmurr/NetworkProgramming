package kth.daniel.homework3.server.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import kth.daniel.homework3.server.model.Holder;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T16:56:34")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Long> accountId;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, Integer> versionNum;
    public static volatile SingularAttribute<Account, Holder> holder;

}