package com.buskarro.Buskarro.Utility;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import java.net.URL;


@Component
public class GoogleJWTUtility {

    private static final String JWKS_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private static final String ISSUER = "https://accounts.google.com";
    private static String CLIENT_ID;

    @Autowired
    public GoogleJWTUtility(Environment env) {
        CLIENT_ID = env.getProperty("google.client.id");
    }

    public static JWTClaimsSet verify(String token) throws Exception {
        URL jwksURL = new URL(JWKS_URL);
        JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(jwksURL);

        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSVerificationKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, keySource);

        jwtProcessor.setJWSKeySelector(keySelector);

        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claims = jwtProcessor.process(signedJWT, null);

        if (!ISSUER.equals(claims.getIssuer())) {
            throw new IllegalArgumentException("Invalid issuer");
        }

        if (!claims.getAudience().contains(CLIENT_ID)) {
            throw new IllegalArgumentException("Invalid audience");
        }

        return claims;
    }
}
