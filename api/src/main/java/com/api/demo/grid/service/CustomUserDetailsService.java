package com.api.demo.grid.service;


import com.api.demo.grid.models.User;
import com.api.demo.grid.repository.UserRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Could not find user : " + username);

        return new CustomUser(user);
    }

    @SuppressFBWarnings("SE_BAD_FIELD")
    private static final class CustomUser extends User implements UserDetails {

        private static final long serialVersionUID = 4L;

        private User user;

        public CustomUser(User user) {

            super();
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new HashSet<>();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CustomUser)) return false;
            if (!super.equals(o)) return false;
            CustomUser that = (CustomUser) o;
            return Objects.equals(user, that.user);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), user);
        }
    }
}
