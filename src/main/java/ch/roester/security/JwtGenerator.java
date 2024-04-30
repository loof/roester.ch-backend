package ch.roester.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

public final class JwtGenerator {

    /**
     * Generates a JWT token for the given user name.
     *
     * @param userName the user name for which to generate the JWT token
     * @return the generated JWT token
     */
    public static String generateJwtToken(String userName) {
        try {
            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), buildJWTClaimsSet(userName));
            jwt.sign(new MACSigner(SecurityConstants.SECRET_KEY_SPEC));
            return jwt.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a JWTClaimsSet object for the given user name.
     *
     * @param userName the user name for which to build the JWTClaimsSet
     * @return the constructed JWTClaimsSet object
     */
    private static JWTClaimsSet buildJWTClaimsSet(String userName) {
        return new JWTClaimsSet.Builder()
                .subject(userName)
                .expirationTime(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .build();
    }

}
