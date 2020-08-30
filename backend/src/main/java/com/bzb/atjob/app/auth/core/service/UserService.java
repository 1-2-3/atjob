// package com.bzb.atjob.app.auth.core.service;

// import com.bzb.atjob.app.auth.core.entity.User;
// import com.bzb.atjob.app.auth.core.repository.UserRepository;
// import java.util.List;
// import java.util.stream.Collectors;
// import lombok.RequiredArgsConstructor;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// /** 系统用户领域服务. */
// @Service
// @RequiredArgsConstructor
// public class UserService implements UserDetailsService {

//   private final UserRepository userRepository;

//   @Override
//   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//     User user = userRepository.getByLoginName(username);
//     if (user == null) {
//       throw new UsernameNotFoundException("User not found!");
//     }

//     List<SimpleGrantedAuthority> roles =
//         user.getRolesOwned().stream()
//             .map(role -> new SimpleGrantedAuthority(role.getRoleId()))
//             .collect(Collectors.toList());
//     // 在这里手动构建 UserDetails 的默认实现
//     return new org.springframework.security.core.userdetails.User(
//         user.getName(), user.getPwd(), roles);
//   }
// }
