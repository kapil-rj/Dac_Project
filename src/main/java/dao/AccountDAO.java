package dao;

import entity.*;
import form.*;
import model.*;
import org.springframework.transaction.annotation.Propagation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagination.PaginationResult;

@Transactional
@Repository
public class AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Account findAccount(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, userName);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteUser(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        Account user = null;
        if (userName != null) {
            user = this.findAccount(userName);
            session.delete(user);
        }
        session.flush();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(UserForm userForm) {

        Session session = this.sessionFactory.getCurrentSession();
        String userName = userForm.getUserName();

        Account user = null;
        boolean isNewUser = false;
        if (userName != null) {
            user = this.findAccount(userName);
        }
        if (user == null) {
            isNewUser = true;
            user = new Account();
        }
        user.setUserName(userForm.getUserName());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setUserEmail(userForm.getUserEmail());
        user.setUserRole(userForm.getUserRole());
        user.setActive(true);

        if (isNewUser) {
            session.persist(user);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }

    // @page = 1, 2, ...
    public PaginationResult<UserInfo> listUserInfo(int page, int maxResult, int maxNavigationPage) {
        String sql = "Select new " + UserInfo.class.getName()//
                + "(ord.userName, ord.firstName, ord.lastName, ord.userEmail, ord.userRole) " + " from "
                + Account.class.getName() + " ord ";

        Session session = this.sessionFactory.getCurrentSession();
        Query<UserInfo> query = session.createQuery(sql, UserInfo.class);
        return new PaginationResult<UserInfo>(query, page, maxResult, maxNavigationPage);
    }

}
