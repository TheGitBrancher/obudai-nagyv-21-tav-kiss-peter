package cookbook;

import cookbook.persistence.entity.Cook;
import cookbook.persistence.repository.CookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private CookRepository cookRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Cook> cookInDb = cookRepository.findByUsername(username);
        if (cookInDb.isPresent()) {
            return new MyUserDetails(cookInDb.get());
        }
        throw new UsernameNotFoundException(username);
    }
}

class MyUserDetails implements UserDetails {

    private final Cook cook;

    public MyUserDetails(Cook cook) {
        this.cook = cook;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return cook.getPassword();
    }

    @Override
    public String getUsername() {
        return cook.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
