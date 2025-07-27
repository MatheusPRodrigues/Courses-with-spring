package com.br.courses.services.external.viacep;

import com.br.courses.dto.address.AddressResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {


    public AddressResponseDto getAddress(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://viacep.com.br/ws/"+cep+"/json";

        var address = restTemplate.getForObject(url, AddressResponseDto.class);

        return address;
    }

}
