package com.hquach.repository;

import com.hquach.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Token repository is used for handling remember login security
 *
 * @author Hai Quach
 */
@Repository("tokenRepository")
public class TokenRepositoryImpl implements PersistentTokenRepository {
    static final Logger logger = LoggerFactory.getLogger(TokenRepositoryImpl.class);
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.info("Creating Token for user : {}", token.getUsername());
        Token persistentLogin = new Token();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persist(persistentLogin);
    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUse) {
        logger.info("Updating Token for seriesId : {}", seriesId);
        Query query = Query.query(Criteria.where("series").is(seriesId));
        Update update = new Update();
        update.set("token", tokenValue);
        update.set("lastUsed", lastUse);
        mongoTemplate.updateFirst(query, update, Token.class);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        logger.info("Fetch Token if any for seriesId : {}", seriesId);
        try {
            Query query = Query.query(Criteria.where("series").is(seriesId));
            Token token = mongoTemplate.findOne(query, Token.class);

            return new PersistentRememberMeToken(token.getUsername(), token.getSeries(),
                    token.getToken(), token.getLastUsed());
        } catch (Exception e) {
            logger.info("Token not found...");
            return null;
        }
    }

    @Override
    public void removeUserTokens(String userName) {
        mongoTemplate.remove(Query.query(Criteria.where("username").is(userName)), Token.class);
    }

    private void persist(Token token) {
        mongoTemplate.save(token);
    }
}
