package com.example.demo.service;

import com.example.demo.dto.auth.request.RegistRequest;
import com.example.demo.dto.client.request.ClientEdditInfoRequest;
import com.example.demo.dto.client.response.ClientInfoResponse;
import com.example.demo.entity.Client;
import com.example.demo.entity.User;
import com.example.demo.exception.Exception;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerClient(RegistRequest registRequest) {
        if(userRepository.findByUsername(registRequest.getUsername()).isPresent()){
            throw new Exception("User đã tồn tại");
        }
        User userEntity = User.builder()
                .username(registRequest.getUsername())
                .password(passwordEncoder.encode(registRequest.getPassword()))
                .role("CLIENT")
                .build();
        User user = userRepository.save(userEntity);

        Client client = Client.builder()
                .userId(user.getId())
                .fullName(registRequest.getFullName())
                .address(registRequest.getAddress())
                .build();
        clientRepository.save(client);
    }
    public void editInfo(ClientEdditInfoRequest request){
        Client client = clientRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Client not found by id: " + request.getUserId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("User not found by id: " + request.getUserId()));

        user.setUsername(request.getEmail());
        client.setFullName(request.getFullName());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(request.getAddress());
        client.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);
        clientRepository.save(client);
    }

}
