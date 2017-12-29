package es.caser.spring.springbootdemo.security;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import es.caser.spring.springbootdemo.model.User;
import es.caser.spring.springbootdemo.repository.IUserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private IUserRepository uRepo;
	public CustomAuthenticationProvider (IUserRepository uRepo){
		this.uRepo=uRepo;
	}
    @Override
    public Authentication authenticate(Authentication auth) 
      throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials()
            .toString();
        User user=uRepo.findByUsername(username);
        if (user!=null){
	        if (user.getPassword().equals(password)){
	        	return new UsernamePasswordAuthenticationToken
	                    (username, password, Arrays.asList(new SimpleGrantedAuthority[]{new SimpleGrantedAuthority("ACTRADMIN")}));
	        }
	        else {
	            throw new
	              BadCredentialsException("External system authentication failed");
	        }
        }
        else{
        	throw new UsernameNotFoundException("User not found");
        }
    }
 
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}