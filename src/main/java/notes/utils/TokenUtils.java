package notes.utils;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import notes.dtos.UserDTO;
import notes.exceptions.ApiException;
import notes.exceptions.NotAuthorizedException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenUtils {
    public static UserDTO getUserWithRolesFromToken(String token) throws ParseException {
        // Return a user with Set of roles as strings
        SignedJWT jwt = SignedJWT.parse(token);
        String roles = jwt.getJWTClaimsSet().getClaim("roles").toString();
        String email = jwt.getJWTClaimsSet().getClaim("email").toString();

        Set<String> rolesSet = Arrays
                .stream(roles.split(","))
                .collect(Collectors.toSet());
        return new UserDTO(email, rolesSet);
    }

    public static String getUserIdFromToken(String token) throws ParseException {
        SignedJWT jwt = SignedJWT.parse(token);
        return jwt.getJWTClaimsSet().getClaim("email").toString();
    }

    public static boolean tokenIsValid(String token, String secret) throws ParseException, JOSEException, NotAuthorizedException {
        SignedJWT jwt = SignedJWT.parse(token);
        if (jwt.verify(new MACVerifier(secret)))
            return true;
        else
            throw new NotAuthorizedException(403, "Token is not valid");
    }

    public static boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        if (timeToExpire(token) > 0)
            return true;
        else
            throw new NotAuthorizedException(403, "Token has expired");
    }
    public static int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        SignedJWT jwt = SignedJWT.parse(token);
        return (int) (jwt.getJWTClaimsSet().getExpirationTime().getTime() - new Date().getTime());
    }

    public static String createToken(UserDTO user, String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY){
        // https://codecurated.com/blog/introduction-to-jwt-jws-jwe-jwa-jwk/
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer(ISSUER)
                    .claim("email", user.getEmail())
                    .claim("roles", user.getRoles().stream().reduce("",(s1, s2) -> s1 + "," + s2))
                    .expirationTime(new Date(new Date().getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                    .build();
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(SECRET_KEY);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            jwsObject.sign(signer);
            return jwsObject.serialize();

        } catch (JOSEException e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }
}
