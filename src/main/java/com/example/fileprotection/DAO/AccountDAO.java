package com.example.fileprotection.DAO;



import com.example.fileprotection.Entities.Account;
import com.example.fileprotection.Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class AccountDAO {
    public void insertAccount(Account data){
        Session session= HibernateUtil.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(data);
        transaction.commit();
        session.close();
        return;
    }
    public void updateExpireTime(Account oldAccount, LocalDateTime time){
        Session session=HibernateUtil.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Account account = session.get(Account.class, oldAccount.getId());
        account.setExpireAt(time);

        session.saveOrUpdate(account);
        transaction.commit();
        session.close();
        return ;
    }
    public void updatePassword(Account oldAccount,String newPass){
        Session session=HibernateUtil.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Account account = session.get(Account.class, oldAccount.getId());
        account.setPassword(newPass);

        session.saveOrUpdate(account);
        transaction.commit();
        session.close();
        return ;
    }

    public int delData(Account data) {
        Session session=HibernateUtil.getFACTORY().openSession();
        Transaction transaction=session.beginTransaction();
        Account account = session.get(Account.class, data.getId());
        if (account != null) {
            session.delete(account);
        }
        transaction.commit();
        session.close();

        return 0;
    }
    public Account getAccountByFilePathAndFileName(String filePath,String fileName){
        Session session= HibernateUtil.getFACTORY().openSession();
        CriteriaBuilder cb=session.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);

        Predicate p1=cb.equal(root.get("pathName").as(String.class),filePath);
        Predicate p2=cb.equal(root.get("fileName").as(String.class),fileName);
        query.where(cb.and(p1,p2));
        Account student=(Account) session.createQuery(query.select(root)).getSingleResult();
        session.close();
        return student;
    }
}
