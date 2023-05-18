package by.tms.eshop.repository.impl;

import by.tms.eshop.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static by.tms.eshop.utils.Constants.QueryParameter.EMAIL;
import static by.tms.eshop.utils.Constants.QueryParameter.LOGIN;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements by.tms.eshop.repository.UserRepository {

    private final SessionFactory sessionFactory;

    private static final String GET_USER_BY_LOGIN = "FROM User WHERE login = :login";
    private static final String GET_USER_BY_LOGIN_OR_EMAIL = "FROM User WHERE login = :login OR email = :email";

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_USER_BY_LOGIN, User.class)
                .setParameter(LOGIN, login)
                .uniqueResultOptional();
    }

    @Override
    public Optional<User> getVerifyUser(String login, String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(GET_USER_BY_LOGIN_OR_EMAIL, User.class)
                .setParameter(LOGIN, login)
                .setParameter(EMAIL, email)
                .uniqueResultOptional();
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }
}